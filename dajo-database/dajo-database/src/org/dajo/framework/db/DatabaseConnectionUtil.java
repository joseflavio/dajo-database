package org.dajo.framework.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.dajo.loggers.db.DatabaseLogger;

final class DatabaseConnectionUtil {

    static private final Logger LOGGER = LoggerFactory.getLogger(DatabaseConnectionUtil.class);

    static private final DatabaseConnectionUtil INSTANCE = new DatabaseConnectionUtil();
   
    static public DatabaseConnectionUtil getInstance() {
        return INSTANCE;
    }

    static private String getJdbcConnectionUrl(final DatabaseConfig dbConfig) {    	    	
    	if( SqlDriver.MSSQL.equals(dbConfig.getDbDriver()) ) {
    		return "jdbc:mssql://"+dbConfig.getDbHost()+":"+dbConfig.getDbPort()+";databaseName="+dbConfig.getDbName();
    			
    	}
    	if( SqlDriver.MYSQL.equals(dbConfig.getDbDriver()) ) {
    		return "jdbc:mysql://"+dbConfig.getDbHost()+":"+dbConfig.getDbPort()+"/"+dbConfig.getDbName();
    	}
    	throw new RuntimeException("Unsupported driver: " + dbConfig.getDbDriver()); 
    }
    
    private final AtomicInteger openConnectionsCount = new AtomicInteger(0);

    private final Set<SqlDriver> databaseDriversNames = new HashSet<SqlDriver>();

    private DatabaseConnectionUtil() {}

    private void loadDatabaseDriver(final SqlDriver dbDriver) {
    	LOGGER.info("databaseDriversNames="+databaseDriversNames);
        if( databaseDriversNames.contains(dbDriver) ) {
            return;
        }
        final String dbDriverClassName = dbDriver.getDriverClassName();
        try {
            Class.forName(dbDriverClassName);
            databaseDriversNames.add(dbDriver);
            LOGGER.info("Success loading jdbc driver class. dbDriver={}, driverClassName={}", dbDriver, dbDriverClassName);
        }
        catch (final ClassNotFoundException e) {
            throw new RuntimeException("Could not load jdbc driver class. dbDriver="+dbDriver+", driverClassName="+dbDriverClassName, e);
        }
    }
    
    synchronized Connection getConnection(final DatabaseConfig dbConfig) {

        loadDatabaseDriver(dbConfig.getDbDriver());
        String dbConnectionUrl = getJdbcConnectionUrl( dbConfig);
        LOGGER.info("dbConnectionUrl={}", dbConnectionUrl);
        try {        	
            
        	final Connection connection = DriverManager.getConnection( dbConnectionUrl, dbConfig.getDbUser(), dbConfig.getDbPassword() );
            connection.setAutoCommit(true);
            connection.setReadOnly( dbConfig.isDbReadOnly() );        
            if( dbConfig.getDbTransactionIsolation() != null ) {
            	//NONE is not Supported by MySQL
            	connection.setTransactionIsolation( dbConfig.getDbTransactionIsolation().intValue() ); 
            }
            
            openConnectionsCount.incrementAndGet();
            DatabaseLogger.logConnectionOpen(openConnectionsCount.intValue());
            LOGGER.debug("Getting a new db connection. openConnectionsCount=" + openConnectionsCount);
            return connection;
        }
        catch (final SQLException e) {
            LOGGER.error("Could not open a new connection. openConnectionsCount=" + openConnectionsCount, e);
            return null;
        }
        catch (Throwable t) {
            LOGGER.error("Unexpected. Could not open a new connection. openConnectionsCount=" + openConnectionsCount, t);
            return null;
        }

    }

    synchronized void closeConnection(final Connection connection) {

        try {
            if (connection == null) {
                DatabaseConnectionUtil.LOGGER.error("Null connection. Could not close it. openConnectionsCount=" + openConnectionsCount);
                return;
            }
            connection.close();
            openConnectionsCount.decrementAndGet();
            DatabaseLogger.logConnectionClosed(openConnectionsCount.intValue());
            LOGGER.debug("Closing a db connection. openConnectionsCount=" + openConnectionsCount);

        }
        catch (final SQLException e) {
            DatabaseConnectionUtil.LOGGER.error("Could not close the jdbc connection. openConnectionsCount=" + openConnectionsCount, e);
        }
        catch (Throwable t) {
            LOGGER.error("Unexpected. Could not open a new connection. openConnectionsCount=" + openConnectionsCount, t);
        }

    }

}// class

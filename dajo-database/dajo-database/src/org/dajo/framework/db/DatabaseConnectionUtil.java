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

    private final AtomicInteger openConnectionsCount = new AtomicInteger(0);

    private final Set<String> databaseDriversNames = new HashSet<String>();

    private DatabaseConnectionUtil() {}

    private void loadDatabaseDriver(final String dbDriverClassName) {
        if( databaseDriversNames.contains(dbDriverClassName) ) {
            return;
        }
        try {
            Class.forName(dbDriverClassName);
            databaseDriversNames.add(dbDriverClassName);
            DatabaseConnectionUtil.LOGGER.debug("Success loading jdbc driver class. driverClassName={}", dbDriverClassName);
        }
        catch (final ClassNotFoundException e) {
            throw new RuntimeException("Could not load jdbc driver class. driverClassName="+dbDriverClassName, e);
        }
    }

    synchronized Connection getConnection(final DatabaseConfig dbConfig) {

        loadDatabaseDriver(dbConfig.getDbDriver());

        try {
            final Connection connection = DriverManager.getConnection( dbConfig.getDbUrl(), dbConfig.getDbUser(), dbConfig.getDbPassword() );
            connection.setAutoCommit(true);
            connection.setReadOnly( dbConfig.isDbReadOnly() );
            connection.setTransactionIsolation(Connection.TRANSACTION_NONE);
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

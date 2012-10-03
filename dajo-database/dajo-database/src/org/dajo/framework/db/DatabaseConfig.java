package org.dajo.framework.db;

import org.dajo.configuration.ConfigAccessor;
import org.dajo.types.IntegerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

final public class DatabaseConfig {

    static private final Logger LOGGER = LoggerFactory.getLogger(DatabaseConfig.class);

    static public final String DEFAULT_DB_HOST_PROPERTY = "database.default.host";
    static public final String DEFAULT_DB_PORT_PROPERTY = "database.default.port";
    static public final String DEFAULT_DB_NAME_PROPERTY = "database.default.name";
    static public final String DEFAULT_DB_USER_PROPERTY = "database.default.user";
    static public final String DEFAULT_DB_PASSWORD_PROPERTY = "database.default.password";

    static public DatabaseConfig getInstance(final ConfigAccessor accessor, final boolean readOnly) {
        final DatabaseConfig dbConfig = new DatabaseConfig(accessor, "default", readOnly);
        LOGGER.trace("Configuration loaded. dbConfig="+dbConfig);
        return dbConfig;
    }

    static public DatabaseConfig getInstance(final ConfigAccessor accessor, final String dbAlias, final boolean readOnly) {
        final DatabaseConfig dbConfig = new DatabaseConfig(accessor, dbAlias, readOnly);
        LOGGER.trace("Configuration loaded. dbConfig="+dbConfig);
        return dbConfig;
    }

    private final String dbAlias;
    private final String dbHost;
    private final int dbPort;
    private final String dbName;
    private final String dbUser;
    private final String dbPassword;
    private final SqlDriver dbDriver;
    private final Integer dbTransactionIsolation;
    private final boolean dbReadOnly;

    static private final IntegerAdapter INTEGER_ADAPTER = new IntegerAdapter();
    static private final SqlDriverTypeAdapter SQLDRIVER_ADAPTER = new SqlDriverTypeAdapter();
    static private final TransactionIsolationTypeAdapter TRANSACTION_ISOLATION_ADAPTER = new TransactionIsolationTypeAdapter();
    
    private DatabaseConfig(final ConfigAccessor accessor, final String alias, final boolean readOnly) {
        this.dbAlias = alias;        
        final String prefix = "database."+dbAlias; 
        this.dbHost = accessor.getMandatoryProperty(prefix+".host");
        this.dbPort = accessor.getMandatoryProperty(prefix+".port", INTEGER_ADAPTER).intValue();
        this.dbName = accessor.getMandatoryProperty(prefix+".databasename");
        this.dbUser = accessor.getMandatoryProperty(prefix+".user");
        this.dbPassword = accessor.getMandatoryProperty(prefix+".password");
        this.dbDriver = accessor.getMandatoryProperty(prefix+".driver", SQLDRIVER_ADAPTER);                
        this.dbTransactionIsolation = accessor.getOptionalProperty(prefix+".transactionIsolation", TRANSACTION_ISOLATION_ADAPTER, null);
        this.dbReadOnly = readOnly;
    }

	public String getDbHost() {
    	return dbHost;
    }

	public int getDbPort() {
    	return dbPort;
    }

	public String getDbName() {
    	return dbName;
    }

	public String getDbUser() {
    	return dbUser;
    }

	public String getDbPassword() {
    	return dbPassword;
    }

	public SqlDriver getDbDriver() {
    	return dbDriver;
    }

	public Integer getDbTransactionIsolation() {
    	return dbTransactionIsolation;
    }

	public boolean isDbReadOnly() {
    	return dbReadOnly;
    }    

}// class

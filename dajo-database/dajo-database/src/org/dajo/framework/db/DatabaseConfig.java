package org.dajo.framework.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.dajo.framework.adapters.IntegerAdapter;
import org.dajo.framework.configuration.ConfigAccessor;

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
    private final String dbDriver;
    private final boolean dbReadOnly;

    private DatabaseConfig(final ConfigAccessor accessor, final String alias, final boolean readOnly) {
        this.dbAlias = alias;
        this.dbHost = accessor.getMandatoryProperty("database."+dbAlias+".host");
        this.dbPort = accessor.getMandatoryProperty("database."+dbAlias+".port", new IntegerAdapter()).intValue();
        this.dbName = accessor.getMandatoryProperty("database."+dbAlias+".name");
        this.dbUser = accessor.getMandatoryProperty("database."+dbAlias+".user");
        this.dbPassword = accessor.getMandatoryProperty("database."+dbAlias+".password");
        this.dbDriver = accessor.getMandatoryProperty("database."+dbAlias+".driver");
        this.dbReadOnly = readOnly;
    }

    public String getDbUrl() {
        final String dbJdbcConnectionUrl = "jdbc:sqlserver://" + dbHost + ":" + dbPort + ";databaseName=" + dbName;
        return dbJdbcConnectionUrl;
    }

    public String getDbName() {
        final String dbJdbcConnectionUrl = dbHost + ":" + dbPort + "/" + dbName;
        return dbJdbcConnectionUrl;
    }

    public String getDbUser() {
        return dbUser;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    public String getDbDriver() {
        return dbDriver;
    }

    public boolean isDbReadOnly() {
        return dbReadOnly;
    }

    @Override
    public String toString() {
        return "DatabaseConfig [dbAlias=" + dbAlias + ", dbHost=" + dbHost + ", dbPort=" + dbPort + ", dbName=" + dbName + ", dbUser=" + dbUser
                + ", dbPassword=(not visible)]";
    }

}// class

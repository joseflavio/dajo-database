package org.dajo.framework.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;

import org.dajo.configuration.ConfigAccessor;
import org.dajo.types.Function;
import org.dajo.types.Optional;
import org.dajo.types.adapter.IntegerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class DatabaseConfigFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseConfigFactory.class);

    static final IntegerAdapter INTEGER_ADAPTER = new IntegerAdapter();
    static final TransactionIsolationTypeAdapter TRANSACTION_ISOLATION_ADAPTER = new TransactionIsolationTypeAdapter();

    public static DatabaseConfig getInstance(final ConfigAccessor accessor, final boolean readOnly) {
        return getInstance(accessor, "default", readOnly);
    }

    public static DatabaseConfig getInstance(final ConfigAccessor accessor, final String dbAlias, final boolean readOnly) {
        final CommonDatabaseConfig commonConfig = new CommonDatabaseConfig(accessor, dbAlias, readOnly);
        final DatabaseConfig config;
        if (DatabaseTypes.SQLITE_MEMORY.equals(commonConfig.dbType)) {
            config = new SqliteMemoryDatabaseConfig(commonConfig);
        } else if (DatabaseTypes.SQLITE_FILE.equals(commonConfig.dbType)) {
            config = new SqliteFileDatabaseConfig(accessor, commonConfig);
        } else if (DatabaseTypes.MICROSOFT_SQL.equals(commonConfig.dbType)) {
            config = new ExternalDatabaseConfig(accessor, commonConfig);
        } else if (DatabaseTypes.MY_SQL.equals(commonConfig.dbType)) {
            config = new ExternalDatabaseConfig(accessor, commonConfig);
        } else if (DatabaseTypes.ORACLE.equals(commonConfig.dbType)) {
            config = new ExternalDatabaseConfig(accessor, commonConfig);
        } else {
            throw new RuntimeException("Unsuported database type. dbType=" + commonConfig.dbType);
        }
        LOGGER.trace("Configuration loaded. dbConfig=" + config);
        return config;
    }

    private static final class DatabaseTypeAdapter implements Function<String, Optional<DatabaseTypes>> {
        DatabaseTypeAdapter() {
        }

        @Override
        public Optional<DatabaseTypes> apply(final String value) {
            try {
                DatabaseTypes sqlDriver = DatabaseTypes.valueOf(value.toUpperCase());
                return Optional.of(sqlDriver);
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Invalid value: " + value + ", Expected values: " + Arrays.toString(DatabaseTypes.values()), e);
            }
        }
    }// class

    private static class CommonDatabaseConfig {

        private static final DatabaseTypeAdapter DB_TYPE_ADAPTER = new DatabaseTypeAdapter();

        private final String dbAlias;

        final DatabaseTypes dbType;
        final String dbConfigPrefix;
        final boolean dbReadOnly;

        CommonDatabaseConfig(final ConfigAccessor accessor, final String alias, final boolean readOnly) {
            this.dbAlias = alias;
            this.dbConfigPrefix = "database." + dbAlias;
            this.dbType = accessor.getMandatoryProperty(dbConfigPrefix + ".type", DB_TYPE_ADAPTER);
            this.dbReadOnly = readOnly;
        }

        @Override
        public String toString() {
            return "CommonDatabaseConfig [dbType=" + dbType + ", dbAlias=" + dbAlias + ", dbConfigPrefix=" + dbConfigPrefix + ", dbReadOnly=" + dbReadOnly
                    + "]";
        }

    }// class

    private static class SqliteMemoryDatabaseConfig implements DatabaseConfig {
        private final CommonDatabaseConfig commonCfg;

        public SqliteMemoryDatabaseConfig(final CommonDatabaseConfig commonCfg) {
            this.commonCfg = commonCfg;
        }

        @Override
        public Connection getConnection() throws SQLException {
            return DriverManager.getConnection("jdbc:sqlite::memory:");
        }

        @Override
        public String toString() {
            return "SqliteMemoryDatabaseConfig [commonCfg=" + commonCfg + "]";
        }
    }// class

    private static class SqliteFileDatabaseConfig implements DatabaseConfig {
        private final CommonDatabaseConfig commonCfg;
        private final String dbFile;
        private final String dbUrl;

        public SqliteFileDatabaseConfig(final ConfigAccessor accessor, final CommonDatabaseConfig commonCfg) {
            this.commonCfg = commonCfg;
            final String prefix = commonCfg.dbConfigPrefix;
            this.dbFile = accessor.getMandatoryProperty(prefix + ".file");
            this.dbUrl = "jdbc:sqlite:" + this.dbFile;
        }

        @Override
        public Connection getConnection() throws SQLException {
            return DriverManager.getConnection("jdbc:sqlite::memory:");
        }

        @Override
        public String toString() {
            return "SqliteFileDatabaseConfig [commonCfg=" + commonCfg + ", dbFile=" + dbFile + ", dbUrl=" + dbUrl + "]";
        }

    }// class

    private static class ExternalDatabaseConfig implements DatabaseConfig {

        private static final Logger INNER_LOGGER = LoggerFactory.getLogger(DatabaseConfigFactory.class);

        private final CommonDatabaseConfig commonCfg;
        private final String dbHost;
        private final int dbPort;
        private final String dbName;
        private final String dbUser;
        private final String dbPassword;
        private final Integer dbTransactionIsolation;
        private final String dbUrl;

        public ExternalDatabaseConfig(final ConfigAccessor accessor, final CommonDatabaseConfig commonCfg) {

            this.commonCfg = commonCfg;
            final String prefix = commonCfg.dbConfigPrefix;
            this.dbHost = accessor.getMandatoryProperty(prefix + ".host");
            this.dbPort = accessor.getMandatoryProperty(prefix + ".port", INTEGER_ADAPTER).intValue();
            this.dbName = accessor.getMandatoryProperty(prefix + ".databasename");
            this.dbUser = accessor.getMandatoryProperty(prefix + ".user");
            this.dbPassword = accessor.getMandatoryProperty(prefix + ".password");
            this.dbTransactionIsolation = accessor.getOptionalProperty(prefix + ".transactionIsolation", TRANSACTION_ISOLATION_ADAPTER, null);

            if (DatabaseTypes.MICROSOFT_SQL.equals(commonCfg.dbType)) {
                dbUrl = "jdbc:sqlserver://" + this.dbHost + ":" + this.dbPort + ";databaseName=" + this.dbName;
            } else if (DatabaseTypes.MY_SQL.equals(commonCfg.dbType)) {
                dbUrl = "jdbc:mysql://" + this.dbHost + ":" + this.dbPort + "/" + this.dbName;
            } else if (DatabaseTypes.ORACLE.equals(commonCfg.dbType)) {
                dbUrl = "jdbc:oracle:thin:@//" + this.dbHost + ":" + this.dbPort + "/" + this.dbName;
            } else {
                throw new RuntimeException("Unsupported database type. dbType=" + commonCfg.dbType);
            }

            loadDatabaseDriver();
        }

        @Override
        public Connection getConnection() throws SQLException {
            try (final Connection c = DriverManager.getConnection(dbUrl, dbUser, dbPassword)) {
                c.setAutoCommit(true);
                c.setReadOnly(commonCfg.dbReadOnly);
                if (dbTransactionIsolation != null) {
                    // NONE is not Supported by MySQL
                    c.setTransactionIsolation(dbTransactionIsolation.intValue());
                }
                return c;
            }
        }

        private void loadDatabaseDriver() {
            final String dbDriverClassName = commonCfg.dbType.getDriverClassName();
            try {
                Class.forName(dbDriverClassName);
                INNER_LOGGER.info("Success loading jdbc driver class. dbType={}, driverClassName={}", commonCfg.dbType, dbDriverClassName);
            } catch (final ClassNotFoundException e) {
                throw new RuntimeException("Could not load jdbc driver class. dbType=" + commonCfg.dbType + ", driverClassName=" + dbDriverClassName, e);
            }
        }

    }

}// class

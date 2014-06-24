package org.dajo.framework.db;

public enum DatabaseTypes {

    SQLITE_MEMORY("org.sqlite.JDBC"), SQLITE_FILE("org.sqlite.JDBC"), MICROSOFT_SQL("com.microsoft.sqlserver.jdbc.SQLServerDriver"), MY_SQL(
            "com.mysql.jdbc.Driver"), ORACLE("oracle.jdbc.driver.OracleDriver");

    private final String driverClass;

    private DatabaseTypes(final String driverClass) {
        this.driverClass = driverClass;
    }

    public String getDriverClassName() {
        return driverClass;
    }

}// enum

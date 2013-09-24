package org.dajo.framework.db;

public enum SqlDriversNames {

    MICROSOFT_SQL("com.microsoft.sqlserver.jdbc.SQLServerDriver"), MY_SQL("com.mysql.jdbc.Driver"), ORACLE("oracle.jdbc.driver.OracleDriver");

    private final String driverClass;

    private SqlDriversNames(final String driverClass) {
        this.driverClass = driverClass;
    }

    public String getDriverClassName() {
        return driverClass;
    }

}// enum

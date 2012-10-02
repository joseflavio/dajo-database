package org.dajo.framework.db;

enum SqlDriver {

	MSSQL("com.microsoft.sqlserver.jdbc.SQLServerDriver"),
	MYSQL("com.mysql.jdbc.Driver");

	private final String driverClass;

	private SqlDriver(final String driverClass) {
		this.driverClass = driverClass;
	}

	public String getDriverClassName() {
    	return driverClass;
    }

}// enum

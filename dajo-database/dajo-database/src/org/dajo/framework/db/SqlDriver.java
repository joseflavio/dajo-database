package org.dajo.framework.db;

enum SqlDriver {
	
	MSSQL("com.mysql.jdbc.Driver"),
	MYSQL("com.mysql.jdbc.Driver");
	
	private final String driverClass;

	private SqlDriver(String driverClass) {
		this.driverClass = driverClass;
	}

	public String getDriverClassName() {
    	return driverClass;
    }
	
}// enum

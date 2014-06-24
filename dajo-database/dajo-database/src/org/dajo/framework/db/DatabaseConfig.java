package org.dajo.framework.db;

import java.sql.Connection;
import java.sql.SQLException;

public interface DatabaseConfig {

    Connection getConnection() throws SQLException;

}

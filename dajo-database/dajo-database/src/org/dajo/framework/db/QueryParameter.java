package org.dajo.framework.db;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class QueryParameter {

    public abstract Object getValueToPrint();

    public abstract void setPreparedStatementParameter(final PreparedStatement st, final int parameterIndex) throws SQLException;

}// class

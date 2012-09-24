package org.dajo.framework.db;

import java.sql.PreparedStatement;
import java.sql.SQLException;



public final class QueryBooleanParameter extends QueryParameter {

    private final Boolean parameterValue;

    public QueryBooleanParameter(final Boolean value) {
        this. parameterValue = value;
    }

    @Override
    public void setPreparedStatementParameter(final PreparedStatement st, final int parameterIndex) throws SQLException {
        st.setObject(parameterIndex, parameterValue);
    }

    @Override
    public Object getValueToPrint() {
        return parameterValue;
    }

}// class

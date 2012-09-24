package org.dajo.framework.db;

import java.sql.PreparedStatement;
import java.sql.SQLException;


public final class QueryIntParameter extends QueryParameter {

    private final int parameterValue;

    public QueryIntParameter(final int intValue) {
        this.parameterValue = intValue;
    }

    @Override
    public void setPreparedStatementParameter(final PreparedStatement st, final int parameterIndex) throws SQLException {
        st.setInt(parameterIndex, parameterValue);
    }

    @Override
    public Object getValueToPrint() {
        return Integer.valueOf(parameterValue);
    }

}// class

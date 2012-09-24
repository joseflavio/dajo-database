package org.dajo.framework.db;

import java.sql.PreparedStatement;
import java.sql.SQLException;


public final class QueryIntegerParameter extends QueryParameter {

    private final Integer parameterValue;

    public QueryIntegerParameter(final Integer intValue) {
        this.parameterValue = intValue;
    }

    @Override
    public void setPreparedStatementParameter(final PreparedStatement st, final int parameterIndex) throws SQLException {
        if( parameterValue != null ) {
            st.setInt(parameterIndex, parameterValue.intValue());
        }
        else {
            st.setNull(parameterIndex, java.sql.Types.INTEGER);
        }
    }

    @Override
    public Object getValueToPrint() {
        return parameterValue;
    }

}// class

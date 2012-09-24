package org.dajo.framework.db;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public final class QueryUniqueIdParameter extends QueryParameter {

    private final String parameterValue;

    public QueryUniqueIdParameter(final String stringValue) {
        this.parameterValue = stringValue;
    }

    @Override
    public void setPreparedStatementParameter(final PreparedStatement st, final int parameterIndex) throws SQLException {
        st.setString(parameterIndex, parameterValue);
    }

    @Override
    public Object getValueToPrint() {
        return parameterValue;
    }

}// class

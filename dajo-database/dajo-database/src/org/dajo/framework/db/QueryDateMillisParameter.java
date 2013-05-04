package org.dajo.framework.db;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public final class QueryDateMillisParameter extends QueryParameter {

    private final QueryTypeGmtTimeInMillis parameterValue;

    public QueryDateMillisParameter(final QueryTypeGmtTimeInMillis value) {
        this.parameterValue = value;
    }

    @Override
    public void setPreparedStatementParameter(final PreparedStatement st, final int parameterIndex) throws SQLException {
        final java.sql.Timestamp parameterSqlDate = new java.sql.Timestamp(parameterValue.getMillis());
        // final Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        st.setTimestamp(parameterIndex, parameterSqlDate);
    }

    @Override
    public Object getValueToPrint() {
        return parameterValue;
    }

}// class

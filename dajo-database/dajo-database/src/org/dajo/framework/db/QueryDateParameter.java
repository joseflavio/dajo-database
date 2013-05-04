package org.dajo.framework.db;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public final class QueryDateParameter extends QueryParameter {

    private final Date parameterValue;

    public QueryDateParameter(final Date value) {
        this.parameterValue = new Date(value.getTime());
    }

    @Override
    public void setPreparedStatementParameter(final PreparedStatement st, final int parameterIndex) throws SQLException {
        final  java.sql.Timestamp parameterSqlDate = new  java.sql.Timestamp(parameterValue.getTime());
        final Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        st.setTimestamp(parameterIndex, parameterSqlDate, calendar);
    }

    @Override
    public Object getValueToPrint() {
        return parameterValue;
    }

}// class

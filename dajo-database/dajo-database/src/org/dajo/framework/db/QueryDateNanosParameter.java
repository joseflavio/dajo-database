package org.dajo.framework.db;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class QueryDateNanosParameter extends QueryParameter {

    private final QueryTypeGmtTimeInNanos gmtTimeInNanos;

    public QueryDateNanosParameter(final QueryTypeGmtTimeInNanos gmtTimeInNanos) {
        this.gmtTimeInNanos = gmtTimeInNanos;
    }

    @Override
    public void setPreparedStatementParameter(final PreparedStatement st, final int parameterIndex) throws SQLException {
        final long gmtTimeInMillis = TimeUnit.NANOSECONDS.convert(gmtTimeInNanos.getNanos(), TimeUnit.MILLISECONDS);
        final java.sql.Timestamp parameterSqlDate = new java.sql.Timestamp(gmtTimeInMillis);
        final long onlyNanos = (gmtTimeInNanos.getNanos() - TimeUnit.NANOSECONDS.convert(gmtTimeInMillis, TimeUnit.MILLISECONDS));
        parameterSqlDate.setNanos((int) (onlyNanos));
        final Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        st.setTimestamp(parameterIndex, parameterSqlDate, calendar);
    }

    @Override
    public Object getValueToPrint() {
        return gmtTimeInNanos;
    }

}

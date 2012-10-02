package org.dajo.framework.db.resultadapters;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.dajo.framework.db.SelectQueryResultAdapter;

public final class SelectQueryResultAdapterDatetime implements SelectQueryResultAdapter<SelectQuerySingleResult<Date>> {

    @Override
    public SelectQuerySingleResult<Date> adaptResultSet(final ResultSet rs) throws SQLException {
        if( rs.next() ) {
            java.sql.Timestamp sqlResult = rs.getTimestamp(1);
            Date result = new Date(sqlResult.getTime());
            return new SelectQuerySingleResult<Date>(result);
        }
        return new SelectQuerySingleResult<Date>();
    }

}// class

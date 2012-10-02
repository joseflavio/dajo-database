package org.dajo.framework.db.resultadapters;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.dajo.framework.db.SelectQueryResultAdapter;

public final class SelectQueryResultAdapterString implements SelectQueryResultAdapter<SelectQuerySingleResult<String>> {

    @Override
    public SelectQuerySingleResult<String> adaptResultSet(final ResultSet rs) throws SQLException {
        if( rs.next() ) {
            final String result = rs.getString(1);
            return new SelectQuerySingleResult<String>(result);
        }
        return new SelectQuerySingleResult<String>();
    }

}// class

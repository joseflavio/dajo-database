package org.dajo.framework.db.resultadapters;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.dajo.framework.db.SelectQueryResultAdapter;
import org.dajo.types.BooleanConverter;

public class SelectQuerySimpleResultAdapters {

	static public class ResultAdapterBool implements SelectQueryResultAdapter<SelectQuerySingleResult<Boolean>> {
	    @Override
	    public SelectQuerySingleResult<Boolean> adaptResultSet(final ResultSet rs) throws SQLException {
	        if( rs.next() ) {
	            boolean result = BooleanConverter.checkedConvert(rs.getInt(1));
	            return new SelectQuerySingleResult<Boolean>(Boolean.valueOf(result));
	        }
	        return new SelectQuerySingleResult<Boolean>();
	    }
	}
	
	static public class ResultAdapterBoolean implements SelectQueryResultAdapter<Boolean> {
	    @Override
	    public Boolean adaptResultSet(final ResultSet rs) throws SQLException {
	        if( rs.next() ) {
	            Boolean result = (Boolean) rs.getObject(1);
	            return result;
	        }
	        return null;
	    }
	}
	
	static public class ResultAdapterInteger implements SelectQueryResultAdapter<Integer> {
	    @Override
	    public Integer adaptResultSet(final ResultSet rs) throws SQLException {
	        if( rs.next() ) {
	            Integer result = (Integer)rs.getObject(1);
	            return result;
	        }
	        return null;
	    }
	}
	
	static public class ResultAdapterLong implements SelectQueryResultAdapter<Long> {
	    @Override
	    public Long adaptResultSet(final ResultSet rs) throws SQLException {
	        if( rs.next() ) {
	        	Long result = (Long)rs.getObject(1);
	            return result;
	        }
	        return null;
	    }
	}
	
}

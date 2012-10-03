package org.dajo.framework.db;

import java.sql.Connection;

import org.dajo.types.TypeAdapter;
import org.dajo.types.TypeAdapterResult;

final class TransactionIsolationTypeAdapter implements TypeAdapter<Integer, String> {

	@Override
    public TypeAdapterResult<Integer> adapt(final String value) {
	    final Integer result;
		if( value == null) {
			result = null;
		}
		else if ( value.equals("TRANSACTION_NONE") == true ) {
			result = Integer.valueOf(Connection.TRANSACTION_NONE);
	    }
		else {
			result = null;
		}				
		if( result != null ) {
			return new TypeAdapterResult<Integer>(result);
		}
		else {
			return new TypeAdapterResult<Integer>();
		}		
    }

}

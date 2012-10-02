package org.dajo.framework.db;

import org.dajo.framework.adapters.TypeAdapter;
import org.dajo.framework.adapters.TypeAdapterResult;


final class SqlDriverTypeAdapter implements TypeAdapter<SqlDriver, String> {

	@Override
    public TypeAdapterResult<SqlDriver> adapt(String value) {
		SqlDriver sqlDriver = SqlDriver.valueOf(value.toUpperCase());		
	    return new TypeAdapterResult<SqlDriver>(sqlDriver);
    }

}

package org.dajo.framework.db;

import java.util.Arrays;

import org.dajo.types.TypeAdapter;
import org.dajo.types.TypeAdapterResult;

final class SqlDriverTypeAdapter implements TypeAdapter<SqlDriversNames, String> {

    @Override
    public TypeAdapterResult<SqlDriversNames> adapt(final String value) {
        try {
            SqlDriversNames sqlDriver = SqlDriversNames.valueOf(value.toUpperCase());
            return new TypeAdapterResult<SqlDriversNames>(sqlDriver);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid value: " + value + ", Expected values: " + Arrays.toString(SqlDriversNames.values()), e);
        }
    }

}

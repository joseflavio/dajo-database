package org.dajo.framework.db;

import java.util.Arrays;

import org.dajo.types.Function;
import org.dajo.types.Optional;

final class SqlDriverTypeAdapter implements Function<String, Optional<SqlDriversNames>> {

    @Override
    public Optional<SqlDriversNames> apply(final String value) {
        try {
            SqlDriversNames sqlDriver = SqlDriversNames.valueOf(value.toUpperCase());
            return Optional.of(sqlDriver);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid value: " + value + ", Expected values: " + Arrays.toString(SqlDriversNames.values()), e);
        }
    }

}// class

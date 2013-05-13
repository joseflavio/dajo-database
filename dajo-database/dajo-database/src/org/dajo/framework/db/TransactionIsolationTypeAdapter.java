package org.dajo.framework.db;

import java.sql.Connection;

import org.dajo.types.Function;
import org.dajo.types.Optional;

final class TransactionIsolationTypeAdapter implements Function<String, Optional<Integer>> {

    @Override
    public Optional<Integer> apply(final String value) {
        if (value == null) {
            return Optional.absent();
        }
        if (value.equals("TRANSACTION_NONE") == true) {
            return Optional.of(Integer.valueOf(Connection.TRANSACTION_NONE));
        }
        if (value.equals("TRANSACTION_READ_COMMITTED") == true) {
            return Optional.of(Integer.valueOf(Connection.TRANSACTION_READ_COMMITTED));
        }
        throw new IllegalArgumentException("Invalid value for transaction isolation type. value=" + value);
    }

}// class

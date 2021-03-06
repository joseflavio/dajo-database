package org.dajo.framework.db.resultadapters;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.dajo.framework.db.SelectQueryResultAdapter;
import org.dajo.types.adapter.BooleanConverter;

public final class SelectQuerySimpleResultAdapters {

    static public class ResultAdapterBool implements SelectQueryResultAdapter<SelectQuerySingleResult<Boolean>> {
        @Override
        public SelectQuerySingleResult<Boolean> adaptResultSet(final ResultSet rs) throws SQLException {
            if (rs.next()) {
                boolean result = BooleanConverter.checkedIntToBool(rs.getInt(1));
                return new SelectQuerySingleResult<Boolean>(Boolean.valueOf(result));
            }
            return new SelectQuerySingleResult<Boolean>();
        }
    }

    static public class ResultAdapterBoolean implements SelectQueryResultAdapter<Boolean> {
        @Override
        public Boolean adaptResultSet(final ResultSet rs) throws SQLException {
            if (rs.next()) {
                final Boolean result = (Boolean) rs.getObject(1);
                return result;
            }
            return null;
        }
    }

    static public class ResultAdapterInteger implements SelectQueryResultAdapter<Integer> {
        @Override
        public Integer adaptResultSet(final ResultSet rs) throws SQLException {
            if (rs.next()) {
                final Integer result = (Integer) rs.getObject(1);
                return result;
            }
            return null;
        }
    }

    static public class ResultAdapterIntegerList implements SelectQueryResultAdapter<List<Integer>> {
        @Override
        public List<Integer> adaptResultSet(final ResultSet rs) throws SQLException {
            List<Integer> result = new LinkedList<Integer>();
            while (rs.next()) {
                final Integer current = (Integer) rs.getObject(1);
                result.add(current);
            }
            return result;
        }
    }

    static public class ResultAdapterLong implements SelectQueryResultAdapter<Long> {
        @Override
        public Long adaptResultSet(final ResultSet rs) throws SQLException {
            if (rs.next()) {
                final Long result = (Long) rs.getObject(1);
                return result;
            }
            return null;
        }
    }

    static public class ResultAdapterBigDecimal implements SelectQueryResultAdapter<BigDecimal> {
        @Override
        public BigDecimal adaptResultSet(final ResultSet rs) throws SQLException {
            if (rs.next()) {
                final BigDecimal result = (BigDecimal) rs.getObject(1);
                return result;
            }
            return null;
        }
    }

    static public class ResultAdapterString implements SelectQueryResultAdapter<String> {
        @Override
        public String adaptResultSet(final ResultSet rs) throws SQLException {
            if (rs.next()) {
                final String result = rs.getString(1);
                return result;
            }
            return null;
        }
    }

    static public class ResultAdapterStringList implements SelectQueryResultAdapter<List<String>> {
        @Override
        public List<String> adaptResultSet(final ResultSet rs) throws SQLException {
            List<String> result = new LinkedList<String>();
            while (rs.next()) {
                final String current = rs.getString(1);
                result.add(current);
            }
            return result;
        }
    }

    static public class ResultAdapterStringTable implements SelectQueryResultAdapter<List<String[]>> {
        private final int columns;

        public ResultAdapterStringTable(final int columns) {
            this.columns = columns;
        }

        @Override
        public List<String[]> adaptResultSet(final ResultSet rs) throws SQLException {
            List<String[]> result = new LinkedList<String[]>();
            while (rs.next()) {
                String[] row = new String[columns];
                for (int i = 0; i < columns; ++i) {
                    final String current = rs.getString(i + 1);
                    row[i] = current;
                }
                result.add(row);
            }
            return result;
        }
    }

}// class

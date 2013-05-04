package org.dajo.framework.db;

import java.util.List;

final class QueryPrinterInternal {

    static private final QueryPrinterParameterConverter.Default defaultConverter = new QueryPrinterParameterConverter.Default();

    private QueryPrinterInternal() {
    }

    static String printUpdateQuery(final UpdateQueryInterface updateQuery) {

        final String queryString = updateQuery.getPreparedUpdateQueryString();
        final List<QueryParameter> queryParameters = updateQuery.getUpdateQueryParameters();

        String replacedQueryString = queryString;
        if (queryParameters != null) {
            for (final QueryParameter queryParam : queryParameters) {
                final Object paramValue = queryParam.getValueToPrint();
                final String printedParam = defaultConverter.convertParameterToPrint(paramValue);
                replacedQueryString = replacedQueryString.replaceFirst("\\?", printedParam);
            }
        }

        return replacedQueryString;

    }

    static String printSelectQuery(final SelectQueryInterface selectQuery) {
        return printSelectQuery(selectQuery, defaultConverter);
    }

    static String printSelectQuery(final SelectQueryInterface selectQuery, final QueryPrinterParameterConverter converter) {
        final String queryString = selectQuery.getPreparedSelectQueryString();
        final List<QueryParameter> queryParameters = selectQuery.getSelectQueryParameters();
        String replacedQueryString = queryString;
        if (queryParameters != null) {
            for (final QueryParameter queryParam : queryParameters) {
                final Object paramValue = queryParam.getValueToPrint();
                final String printedParam = converter.convertParameterToPrint(paramValue);
                replacedQueryString = replacedQueryString.replaceFirst("\\?", printedParam);
            }
        }
        return replacedQueryString;
    }

    static String printInsertQuery(final String queryString, final List<QueryParameter> queryParameters) {
        String replacedQueryString = queryString;
        if (queryParameters != null) {
            for (final QueryParameter queryParam : queryParameters) {
                final Object paramValue = queryParam.getValueToPrint();
                final String printedParam = defaultConverter.convertParameterToPrint(paramValue);
                replacedQueryString = replacedQueryString.replaceFirst("\\?", printedParam);
            }
        }
        return replacedQueryString;
    }

}// class

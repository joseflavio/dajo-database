package org.dajo.framework.db;


public final class SelectQueryPrinter {

    private final SelectQueryInterface selectQuery;

    public SelectQueryPrinter(final SelectQueryInterface selectQuery) {
        this.selectQuery = selectQuery;
    }

    @Override
    public String toString() {
        final String queryName = selectQuery.getClass().getCanonicalName();
        final String printedSelectQuery = QueryPrinterInternal.printSelectQuery(selectQuery);
        final String toString = "[queryName="+ queryName + ", query=" + printedSelectQuery + "]";
        return toString;
    }

    public String toShortString() {
        final String printedSelectQuery = QueryPrinterInternal.printSelectQuery(selectQuery);
        return printedSelectQuery;
    }

    public String toShortString(final QueryPrinterParameterConverter parameterPrinter) {
        final String printedSelectQuery = QueryPrinterInternal.printSelectQuery(selectQuery, parameterPrinter);
        return printedSelectQuery;
    }

}// class

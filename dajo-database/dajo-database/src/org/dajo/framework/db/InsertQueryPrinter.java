package org.dajo.framework.db;


public final class InsertQueryPrinter {

    private final InsertQueryInterface query;

    public InsertQueryPrinter(final InsertQueryInterface query) {
        this.query = query;
    }

    @Override
    public String toString() {
        final String queryName = query.getClass().getCanonicalName();
        final String printedSelectQuery = QueryPrinterInternal.printInsertQuery(query.getPreparedInsertQueryString(), query.getInsertQueryParameters());
        final String toString = "[queryName="+ queryName + ", query=" + printedSelectQuery + "]";
        return toString;
    }

}// class

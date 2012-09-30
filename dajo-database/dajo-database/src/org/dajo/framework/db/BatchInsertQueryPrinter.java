package org.dajo.framework.db;



public final class BatchInsertQueryPrinter {

    private final BatchInsertQueryInterface query;

    public BatchInsertQueryPrinter(final BatchInsertQueryInterface query) {
        this.query = query;
    }

    @Override
    public String toString() {
        final String queryName = query.getClass().getCanonicalName();
        String toString = "BatchInsertQuery[queryName="+ queryName + " \n";
        int i = 0;
        for( BatchInsertQueryParameters currentParams : query.getInsertQueryParametersList() ) {
            String currentPrintedQuery = InternalQueryPrinter.printInsertQuery(query.getPreparedInsertQueryString(), currentParams.getInsertQueryParameters());
            toString +=" query " + i +": " + currentPrintedQuery + " \n";
            ++i;
        }
        toString += " ] ";
        return toString;
    }

}// class

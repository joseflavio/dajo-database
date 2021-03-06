package org.dajo.framework.db;

public final class InsertQueryResult {

    private final boolean querySuccessfullyExecuted;

    private final int rowsInserted;

    public InsertQueryResult() {
        this.querySuccessfullyExecuted = false;
        this.rowsInserted = 0;
    }

    public InsertQueryResult(final int rowsInserted) {
        this.querySuccessfullyExecuted = true;
        this.rowsInserted = rowsInserted;
    }

    public int getRowsInserted() {
        return rowsInserted;
    }

    public boolean isInsertQuerySuccessfull() {
        return querySuccessfullyExecuted;
    }

    @Override
    public String toString() {
        return "InsertQueryResult [querySuccessfullyExecuted=" + querySuccessfullyExecuted + ", rowsInserted=" + rowsInserted + "]";
    }


}// class

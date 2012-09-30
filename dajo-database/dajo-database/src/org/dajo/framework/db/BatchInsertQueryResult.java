package org.dajo.framework.db;

import java.util.Arrays;

public final class BatchInsertQueryResult {

    private final boolean querySuccessfullyExecuted;

    private final int[] rowsInsertedArray;

    public BatchInsertQueryResult() {
        this.querySuccessfullyExecuted = false;
        this.rowsInsertedArray = null;
    }

    public BatchInsertQueryResult(final int[] rowsInsertedArray) {
        this.querySuccessfullyExecuted = true;
        this.rowsInsertedArray = rowsInsertedArray;
    }

    public int[] getRowsInsertedArray() {
        return rowsInsertedArray;
    }

    public boolean isInsertQuerySuccessfull() {
        return querySuccessfullyExecuted;
    }

    @Override
    public String toString() {
        return "InsertQueryResult [querySuccessfullyExecuted=" + querySuccessfullyExecuted + ", rowsInsertedArray=" + Arrays.toString(rowsInsertedArray) + "]";
    }

}// class

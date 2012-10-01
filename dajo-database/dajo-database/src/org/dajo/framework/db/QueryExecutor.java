package org.dajo.framework.db;


public interface QueryExecutor {

    InsertQueryResult executeInsertQuery(InsertQueryInterface insertQuery);

    BatchInsertQueryResult executeBatchInsertQuery(final BatchInsertQueryInterface batchInsertQuery);

    UpdateQueryResult executeUpdateQuery(UpdateQueryInterface updateQuery);

    <T> SelectQueryResult<T> executeSelectQuery(SelectQueryInterface selectQuery, SelectQueryResultAdapter<T> queryResultAdapter);

}//class

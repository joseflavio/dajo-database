package org.dajo.framework.db;

import java.sql.Connection;

import org.dajo.chronometer.Chronometer;
import org.dajo.loggers.db.DatabaseLogger;

public class SingleConnectionQueryExecutor implements QueryExecutor {

    private Connection connection;

    public SingleConnectionQueryExecutor(final DatabaseConfig dbConfig) {
        this.connection = DatabaseConnectionUtil.getInstance().getConnection(dbConfig);
    }

    @Override
    protected void finalize() {
        if( connection != null ) {
            DatabaseConnectionUtil.getInstance().closeConnection(connection);
            connection = null;
        }
    }

    public void close() {
        if( connection != null ) {
            DatabaseConnectionUtil.getInstance().closeConnection(connection);
            connection = null;
        }
    }

    @Override
    public InsertQueryResult executeInsertQuery(final InsertQueryInterface insertQuery) {

        if( connection == null ) {
            return new InsertQueryResult();
        }
        final InsertQueryResult result = InsertQueryExecuter.executeInsertQuery(connection, insertQuery);
        return result;
    }

    @Override
    public BatchInsertQueryResult executeBatchInsertQuery(final BatchInsertQueryInterface batchInsertQuery) {
        DatabaseLogger.logInsertQuery(batchInsertQuery);
        final Chronometer chronometer = new Chronometer(this, "executeInsertQuery", batchInsertQuery);
        chronometer.start();
        final BatchInsertQueryResult result;
        if( connection != null ) {
            result = InsertQueryExecuter.executeBatchInsertQuery(connection, batchInsertQuery);
        }
        else {
            result = new BatchInsertQueryResult();
        }
        chronometer.close();
        DatabaseLogger.logBatchInsertResult(chronometer, result);
        return result;
    }

    @Override
    public UpdateQueryResult executeUpdateQuery(final UpdateQueryInterface updateQuery) {
        if( connection == null ) {
            return new UpdateQueryResult();
        }
        final UpdateQueryResult result = UpdateQueryExecuter.executeUpdateQuery(connection, updateQuery);
        return result;
    }

    @Override
    public <T> SelectQueryResult<T> executeSelectQuery(final SelectQueryInterface selectQuery, final SelectQueryResultAdapter<T> queryResultAdapter) {
        DatabaseLogger.logSelectQuery(selectQuery);
        final Chronometer chronometer = new Chronometer(this, "executeSelectQuery", selectQuery);
        chronometer.start();
        final SelectQueryResult<T> result;
        if( connection != null ) {
            result = SelectQueryExecuter.executeSelectQuery(connection, selectQuery, queryResultAdapter);
        }
        else {
            result = new SelectQueryResult<T>();
        }
        chronometer.close();
        DatabaseLogger.logSelectResult(chronometer, result);
        return result;
    }

}// class

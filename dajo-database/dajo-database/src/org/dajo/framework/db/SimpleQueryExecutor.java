package org.dajo.framework.db;

import java.sql.Connection;

import org.dajo.chronometer.Chronometer;
import org.dajo.loggers.db.DatabaseLogger;

public class SimpleQueryExecutor implements QueryExecutor {

    private final DatabaseConfig dbConfig;

    public SimpleQueryExecutor(final DatabaseConfig dbConfig) {
        this.dbConfig = dbConfig;
    }

    @Override
    public InsertQueryResult executeInsertQuery(final InsertQueryInterface insertQuery) {
        DatabaseLogger.logInsertQuery(insertQuery);
        final Chronometer chronometer = new Chronometer(this, "executeInsertQuery", insertQuery);
        chronometer.start();
        final Connection connection = DatabaseConnectionUtil.getInstance().getConnection(dbConfig);
        final InsertQueryResult result;
        if (connection != null) {
            result = InsertQueryExecuter.executeInsertQuery(connection, insertQuery);
            DatabaseConnectionUtil.getInstance().closeConnection(connection);
        }
        else {
            result = new InsertQueryResult();
        }
        chronometer.close();
        DatabaseLogger.logInsertResult(chronometer, result);
        return result;

    }

    @Override
    public BatchInsertQueryResult executeBatchInsertQuery(final BatchInsertQueryInterface batchInsertQuery) {
        DatabaseLogger.logInsertQuery(batchInsertQuery);
        final Chronometer chronometer = new Chronometer(this, "executeInsertQuery", batchInsertQuery);
        chronometer.start();
        final Connection connection = DatabaseConnectionUtil.getInstance().getConnection(dbConfig);
        final BatchInsertQueryResult result;
        if (connection != null) {
            result = InsertQueryExecuter.executeBatchInsertQuery(connection, batchInsertQuery);
            DatabaseConnectionUtil.getInstance().closeConnection(connection);
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
        DatabaseLogger.logUpdateQuery(updateQuery);
        final Chronometer chronometer = new Chronometer(this, "executeUpdateQuery", updateQuery);
        chronometer.start();
        final Connection connection = DatabaseConnectionUtil.getInstance().getConnection(dbConfig);
        final UpdateQueryResult result;
        if( connection != null ) {
            result = UpdateQueryExecuter.executeUpdateQuery(connection, updateQuery);
            DatabaseConnectionUtil.getInstance().closeConnection(connection);
        }
        else {
            result = new UpdateQueryResult();
        }
        chronometer.close();
        DatabaseLogger.logUpdateResult(chronometer, result);
        return result;

    }

    @Override
    public <T> SelectQueryResult<T> executeSelectQuery(final SelectQueryInterface selectQuery, final SelectQueryResultAdapter<T> queryResultAdapter) {
        DatabaseLogger.logSelectQuery(selectQuery);
        final Chronometer chronometer = new Chronometer(this, "executeSelectQuery", selectQuery);
        chronometer.start();
        final Connection connection = DatabaseConnectionUtil.getInstance().getConnection(dbConfig);
        final SelectQueryResult<T> result;
        if( connection != null ) {
            result = SelectQueryExecuter.executeSelectQuery(connection, selectQuery, queryResultAdapter);
            DatabaseConnectionUtil.getInstance().closeConnection(connection);
        }
        else {
            result = new SelectQueryResult<T>();
        }
        chronometer.close();
        DatabaseLogger.logSelectResult(chronometer, result);
        return result;
    }

}// class

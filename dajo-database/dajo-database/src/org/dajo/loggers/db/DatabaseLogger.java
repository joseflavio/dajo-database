package org.dajo.loggers.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.dajo.chronometer.Chronometer;
import org.dajo.framework.db.BatchInsertQueryInterface;
import org.dajo.framework.db.BatchInsertQueryPrinter;
import org.dajo.framework.db.BatchInsertQueryResult;
import org.dajo.framework.db.InsertQueryInterface;
import org.dajo.framework.db.InsertQueryPrinter;
import org.dajo.framework.db.InsertQueryResult;
import org.dajo.framework.db.SelectQueryInterface;
import org.dajo.framework.db.SelectQueryPrinter;
import org.dajo.framework.db.SelectQueryResult;
import org.dajo.framework.db.UpdateQueryInterface;
import org.dajo.framework.db.UpdateQueryPrinter;
import org.dajo.framework.db.UpdateQueryResult;

public final class DatabaseLogger {

    static private final Logger LOGGER = LoggerFactory.getLogger(DatabaseLogger.class);

    static public void logConnectionOpen(final int openConnectionsCount) {
        LOGGER.trace("[DB CONNECTION OPENED ] [openConnectionsCount={}]", Integer.valueOf(openConnectionsCount));
    }

    static public void logConnectionClosed(final int openConnectionsCount) {
        LOGGER.trace("[DB CONNECTION CLOSED ] [openConnectionsCount={}]", Integer.valueOf(openConnectionsCount));
    }

    static public void logSelectQuery(final SelectQueryInterface query) {
        LOGGER.trace("[DB SELECT QUERY      ] selectQuery={}", new SelectQueryPrinter(query));
    }

    static public void logUpdateQuery(final UpdateQueryInterface query) {
        LOGGER.trace("[DB UPDATE QUERY      ] updateQuery={}", new UpdateQueryPrinter(query));
    }

    static public void logInsertQuery(final InsertQueryInterface query) {
        LOGGER.trace("[DB INSERT QUERY      ] insertQuery={}", new InsertQueryPrinter(query));
    }

    public static void logInsertQuery(final BatchInsertQueryInterface batchInsertQuery) {
        LOGGER.trace("[DB BAT INSERT QUERY  ] batchInsertQuery={}", new BatchInsertQueryPrinter(batchInsertQuery));
    }

    static public void logSelectResult(final Chronometer chronometer, final SelectQueryResult<?> result) {
        LOGGER.trace("[DB SELECT RESULT     ] chronometer={}, result={}", chronometer, result);
    }

    static public void logUpdateResult(final Chronometer chronometer, final UpdateQueryResult result) {
        LOGGER.trace("[DB UPDATE RESULT     ] chronometer={}, result={}", chronometer, result);
    }

    static public void logInsertResult(final Chronometer chronometer, final InsertQueryResult result) {
        LOGGER.trace("[DB INSERT RESULT     ] chronometer={}, result={}", chronometer, result);
    }

    public static void logBatchInsertResult(final Chronometer chronometer, final BatchInsertQueryResult result) {
        int numberOfRows = (result.getRowsInsertedArray() != null) ? result.getRowsInsertedArray().length : 0 ;
        LOGGER.trace("[DB BAT INSERT RESULT ] chronometer={}, result={}", chronometer.toString(numberOfRows), result);
    }

}// class

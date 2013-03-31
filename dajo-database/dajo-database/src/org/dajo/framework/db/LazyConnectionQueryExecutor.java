package org.dajo.framework.db;

import java.sql.Connection;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.dajo.chronometer.Chronometer;
import org.dajo.loggers.db.DatabaseLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LazyConnectionQueryExecutor implements QueryExecutor {

    static private final Logger LOGGER = LoggerFactory.getLogger(LazyConnectionQueryExecutor.class);

    private final Lock lock = new ReentrantLock();

    private Connection connection = null;

    private Date lastTime = new Date();

    private final DatabaseConfig dbConfig;

    public LazyConnectionQueryExecutor(final DatabaseConfig dbConfig) {
        this.dbConfig = dbConfig;
    }

    @Override
    protected void finalize() {
        if (connection != null) {
            DatabaseConnectionUtil.getInstance().closeConnection(connection);
            connection = null;
        }
    }

    public void close() {
        lock.lock();
        try {
            if (connection != null) {
                DatabaseConnectionUtil.getInstance().closeConnection(connection);
                connection = null;
            } else {
                LOGGER.info("no connection to release");
            }
        } finally {
            lock.unlock();
        }
    }

    public long getIdleTime() {
        lock.lock();
        try {
            final Date now = new Date();
            return now.getTime() - lastTime.getTime();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public InsertQueryResult executeInsertQuery(final InsertQueryInterface insertQuery) {
        throw new UnsupportedOperationException();
    }

    @Override
    public BatchInsertQueryResult executeBatchInsertQuery(final BatchInsertQueryInterface batchInsertQuery) {
        throw new UnsupportedOperationException();
    }

    @Override
    public UpdateQueryResult executeUpdateQuery(final UpdateQueryInterface updateQuery) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> SelectQueryResult<T> executeSelectQuery(final SelectQueryInterface selectQuery, final SelectQueryResultAdapter<T> queryResultAdapter) {
        DatabaseLogger.logSelectQuery(selectQuery);
        final Chronometer chronometer = new Chronometer(this, "executeSelectQuery", selectQuery);
        chronometer.start();

        lock.lock();
        try {

            if (connection == null) {
                connection = DatabaseConnectionUtil.getInstance().getConnection(dbConfig);
            }

            final SelectQueryResult<T> result;
            if (connection != null) {
                result = SelectQueryExecuter.executeSelectQuery(connection, selectQuery, queryResultAdapter);
            } else {
                result = new SelectQueryResult<T>();
            }

            chronometer.close();
            DatabaseLogger.logSelectResult(chronometer, result);

            return result;
        } finally {
            lastTime = new Date();
            lock.unlock();
        }

    }

    static public void launchDatabaseReleaser(final LazyConnectionQueryExecutor queryExecutor, final int maxIdleTime, final long delay,
            final long peridToCheckAlive) {
        DatabaseReleaser releaserTask = new LazyConnectionQueryExecutor.DatabaseReleaser(queryExecutor, maxIdleTime);
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(releaserTask, delay, peridToCheckAlive);
    }

    private static final class DatabaseReleaser extends TimerTask {
        static private final Logger INNERLOGGER = LoggerFactory.getLogger(DatabaseReleaser.class);
        private final LazyConnectionQueryExecutor queryExecutor;
        private final int maxIdleTime;

        protected DatabaseReleaser(final LazyConnectionQueryExecutor queryExecutor, final int maxIdleTime) {
            this.queryExecutor = queryExecutor;
            this.maxIdleTime = maxIdleTime;
        }

        @Override
        public void run() {
            long idleTime = queryExecutor.getIdleTime();
            if (idleTime > maxIdleTime) {
                INNERLOGGER.info("Max idle time reached, releasing the connection. idleTime=" + idleTime);
                queryExecutor.close();
            }
        }
    }// class

}// class

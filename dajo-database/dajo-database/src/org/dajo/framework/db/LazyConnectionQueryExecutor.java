package org.dajo.framework.db;

import java.sql.Connection;
import java.util.Date;
import java.util.TimerTask;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class LazyConnectionQueryExecutor implements QueryExecutor {

    private final Lock lock = new ReentrantLock();

    private Connection connection = null;

    private Date lastTime = new Date();

    private final DatabaseConfig dbConfig;

    public LazyConnectionQueryExecutor(final DatabaseConfig dbConfig) {
        this.dbConfig = dbConfig;
    }

    @Override
    protected void finalize() {
        if( connection != null ) {
            DatabaseConnectionUtil.getInstance().closeConnection(connection);
        }
    }

    public boolean release() {
        lock.lock();
        try {
            if( connection != null ) {
                DatabaseConnectionUtil.getInstance().closeConnection(connection);
                connection = null;
                return true;
            }
            else {
                return false;
            }
        } finally {
            lock.unlock();
        }
    }


    public long getIdleTime() {
        lock.lock();
        try {
            final Date now = new Date();
            return now.getTime()-lastTime.getTime();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public InsertQueryResult executeInsertQuery(final InsertQueryInterface insertQuery) {
        throw new UnsupportedOperationException();
    }

    @Override
    public UpdateQueryResult executeUpdateQuery(final UpdateQueryInterface updateQuery) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> SelectQueryResult<T> executeSelectQuery(final SelectQueryInterface selectQuery, final SelectQueryResultAdapter<T> queryResultAdapter) {
        lock.lock();
        try {
            lastTime = new Date();
            if (connection == null) {
                this.connection = DatabaseConnectionUtil.getInstance().getConnection(dbConfig);
            }
            final SelectQueryResult<T> result = SelectQueryExecuter.executeSelectQuery(connection, selectQuery, queryResultAdapter);
            return result;
        }
        finally {
            lock.unlock();
        }
    }

    public final static class DatabaseReleaser extends TimerTask {
        static private final Logger LOGGER = LoggerFactory.getLogger(DatabaseReleaser.class);
        private final LazyConnectionQueryExecutor queryExecutor;
        private final int maxIdleTime;
        public DatabaseReleaser(final LazyConnectionQueryExecutor queryExecutor, final int maxIdleTime) {
            this.queryExecutor = queryExecutor;
            this.maxIdleTime = maxIdleTime;
        }
        @Override
        public void run() {
            long idleTime = queryExecutor.getIdleTime();
            if( idleTime > maxIdleTime ) {
                LOGGER.info("Max idle time reached, releasing the connection. idleTime="+idleTime);
                boolean connectionReleased = queryExecutor.release();
                if( connectionReleased == false ) {
                    LOGGER.info("no connection to release");
                }
            }
        }
    }

}// class

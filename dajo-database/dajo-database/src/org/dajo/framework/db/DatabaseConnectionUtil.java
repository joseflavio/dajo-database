package org.dajo.framework.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicInteger;

import org.dajo.loggers.db.DatabaseLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

final class DatabaseConnectionUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseConnectionUtil.class);

    private static final DatabaseConnectionUtil INSTANCE = new DatabaseConnectionUtil();

    public static DatabaseConnectionUtil getInstance() {
        return INSTANCE;
    }

    private final AtomicInteger openConnectionsCount = new AtomicInteger(0);

    private DatabaseConnectionUtil() {
    }

    synchronized Connection getConnection(final DatabaseConfig dbConfig) {
        try {
            final Connection connection = dbConfig.getConnection();
            openConnectionsCount.incrementAndGet();
            DatabaseLogger.logConnectionOpen(openConnectionsCount.intValue());
            LOGGER.debug("Getting a new db connection. openConnectionsCount=" + openConnectionsCount);
            return connection;
        } catch (final SQLException e) {
            LOGGER.error("Could not open a new connection. openConnectionsCount=" + openConnectionsCount, e);
            return null;
        } catch (Throwable t) {
            LOGGER.error("Unexpected. Could not open a new connection. openConnectionsCount=" + openConnectionsCount, t);
            return null;
        }

    }

    synchronized boolean closeConnection(final Connection connection) {

        try {
            if (connection == null) {
                DatabaseConnectionUtil.LOGGER.error("Null connection. Could not close it. openConnectionsCount=" + openConnectionsCount);
                return false;
            }
            connection.close();
            openConnectionsCount.decrementAndGet();
            DatabaseLogger.logConnectionClosed(openConnectionsCount.intValue());
            LOGGER.debug("Closing a db connection. openConnectionsCount=" + openConnectionsCount);
            return true;
        } catch (final SQLException e) {
            DatabaseConnectionUtil.LOGGER.error("Could not close the jdbc connection. openConnectionsCount=" + openConnectionsCount, e);
            return false;
        } catch (Throwable t) {
            LOGGER.error("Unexpected. Could not open a new connection. openConnectionsCount=" + openConnectionsCount, t);
            return false;
        }

    }

}// class

package org.dajo.framework.db.tests;


import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

import org.dajo.configuration.ConfigurationReader;
import org.dajo.files.Files2;
import org.dajo.framework.db.DatabaseConfig;
import org.dajo.framework.db.DatabaseConfigFactory;
import org.dajo.framework.db.QueryParameter;
import org.dajo.framework.db.SimpleQueryExecutor;
import org.dajo.framework.db.SqliteMemoryConfig;
import org.dajo.framework.db.UpdateQueryInterface;

public final class MyTest {

    public static void main(final String[] args) {

        DatabaseConfig config1 = DatabaseConfigFactory.getInstance(SqliteMemoryConfig.getInstance(), false);


        DatabaseConfig config2 = DatabaseConfigFactory.getInstance(
                ConfigurationReader.loadInternalProperties("org/dajo/framework/db/tests/sqllite-test.properties"), false);

        System.out.println("config1=" + config1);
        System.out.println("config2=" + config2);

        Path path = Paths.get("temp").toAbsolutePath();
        boolean s = Files2.createDirectoryIfDoesntExist(path);
        System.out.println("s=" + s + ", path=" + path);

        SimpleQueryExecutor sqe = new SimpleQueryExecutor(config2);
        sqe.executeUpdateQuery(new CreateTableQuery());

        // try (final Connection c = DriverManager.getConnection("jdbc:sqlite:./temp/local.db")) {
        // try (final Statement st = c.createStatement()) {
        // final String sql = "CREATE TABLE HOTELAVAIL1 ( " + "ID INT PRIMARY KEY       NOT NULL, " + "NAME                     CHAR(50)" + ")";
        // st.executeUpdate(sql);
        // }
        // } catch (SQLException se) {
        // for (Throwable e : se) {
        // System.err.println("Error encountered: " + e);
        // }
        // }
        //
        // try (final Connection c = DriverManager.getConnection("jdbc:sqlite::memory:")) {
        // try (final Statement st = c.createStatement()) {
        // final String sql = "CREATE TABLE HOTELAVAIL1 ( " + "ID INT PRIMARY KEY       NOT NULL, " + "NAME                     CHAR(50)" + ")";
        // st.executeUpdate(sql);
        // }
        // } catch (SQLException se) {
        // for (Throwable e : se) {
        // System.err.println("Error encountered: " + e);
        // }
        // }

    }

    static final class CreateTableQuery implements UpdateQueryInterface {

        private final String sql;

        public CreateTableQuery() {
            this.sql = "CREATE TABLE HOTELAVAIL1 ( " + "ID INT PRIMARY KEY       NOT NULL, " + "NAME                     CHAR(50)" + ")";
        }

        @Override
        public String getPreparedUpdateQueryString() {
            return sql;
        }

        @Override
        public List<QueryParameter> getUpdateQueryParameters() {
            return Collections.emptyList();
        }

    }// class

}// class

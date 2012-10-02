package org.dajo.framework.db.util;

import java.util.Date;
import java.util.HashMap;

import org.dajo.framework.configuration.SimpleConfigAccessor;
import org.dajo.framework.db.DatabaseConfig;
import org.dajo.framework.db.SelectQueryResult;
import org.dajo.framework.db.SimpleQueryExecutor;
import org.dajo.framework.db.resultadapters.SelectQueryResultAdapterDatetime;
import org.dajo.framework.db.resultadapters.SelectQuerySingleResult;

public final class TestSelectCurrentDate {

    public static void main(final String[] args) {

        HashMap<String, String> map = new HashMap<String, String>();
        map.put(DatabaseConfig.DEFAULT_DB_HOST_PROPERTY, "107.22.162.94");
        map.put(DatabaseConfig.DEFAULT_DB_PORT_PROPERTY, "1433");
        map.put(DatabaseConfig.DEFAULT_DB_NAME_PROPERTY, "KEEPIN_V01_DEMO01");
        map.put(DatabaseConfig.DEFAULT_DB_USER_PROPERTY, "sa");
        map.put(DatabaseConfig.DEFAULT_DB_PASSWORD_PROPERTY, "chicabom2012!");

        SimpleConfigAccessor configAccessor = SimpleConfigAccessor.getInstance(map);

        DatabaseConfig databaseConfig = DatabaseConfig.getInstance(configAccessor, true);
        SimpleQueryExecutor simpleQueryExecutor = new SimpleQueryExecutor(databaseConfig);

        SelectMsSQLGetSysUTCDatetime selectQuery = new SelectMsSQLGetSysUTCDatetime();
        SelectQueryResult<SelectQuerySingleResult<Date>> queryResult = simpleQueryExecutor.executeSelectQuery(selectQuery, new SelectQueryResultAdapterDatetime());

        System.out.println("queryResult="+queryResult);

    }


}// class

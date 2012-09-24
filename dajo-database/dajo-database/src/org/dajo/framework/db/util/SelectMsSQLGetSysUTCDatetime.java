package org.dajo.framework.db.util;

import java.util.LinkedList;
import java.util.List;

import org.dajo.framework.db.QueryParameter;
import org.dajo.framework.db.SelectQueryInterface;

public class SelectMsSQLGetSysUTCDatetime implements SelectQueryInterface {

    private final List<QueryParameter> queryParameters = new LinkedList<QueryParameter>();

    @Override
    public String getPreparedSelectQueryString() {
        String query = "SELECT SYSUTCDATETIME()";
        return query;

    }

    @Override
    public List<QueryParameter> getSelectQueryParameters() {
        return queryParameters;
    }

}// class

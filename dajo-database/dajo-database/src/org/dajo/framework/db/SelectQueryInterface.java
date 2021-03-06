package org.dajo.framework.db;

import java.util.List;

public interface SelectQueryInterface {

    String getPreparedSelectQueryString();

    List<QueryParameter> getSelectQueryParameters();

}// interface

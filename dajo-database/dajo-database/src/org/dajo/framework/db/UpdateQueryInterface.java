package org.dajo.framework.db;

import java.util.List;


public interface UpdateQueryInterface {

    String getPreparedUpdateQueryString();

    List<QueryParameter> getUpdateQueryParameters();

}// class

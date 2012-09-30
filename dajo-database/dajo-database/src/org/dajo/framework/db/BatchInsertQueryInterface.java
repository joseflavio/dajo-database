package org.dajo.framework.db;

import java.util.List;

public interface BatchInsertQueryInterface {

    String getPreparedInsertQueryString();

    List<BatchInsertQueryParameters> getInsertQueryParametersList();

}

package org.dajo.framework.db;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

final class PreparedParametersUtil {

    static void fillParameters(final PreparedStatement st, final List<QueryParameter> queryParameterList) throws SQLException {
        int parameterIndex = 1;
        for (final QueryParameter queryParameter : queryParameterList) {
            queryParameter.setPreparedStatementParameter(st, parameterIndex);
            ++parameterIndex;
        }
    }

}// class

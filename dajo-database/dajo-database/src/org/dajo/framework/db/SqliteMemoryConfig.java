package org.dajo.framework.db;

import java.util.HashMap;
import java.util.Map;

import org.dajo.configuration.ConfigAccessor;
import org.dajo.configuration.SimpleConfigAccessor;

public class SqliteMemoryConfig {

    public static ConfigAccessor getInstance() {
        final Map<String, String> map = new HashMap<String, String>();
        map.put("database.default.type", "SQLITE_MEMORY");
        return SimpleConfigAccessor.getInstance(map);
    }

}// class

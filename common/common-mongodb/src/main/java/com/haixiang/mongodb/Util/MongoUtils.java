package com.haixiang.mongodb.Util;

import com.ai.paas.utils.StringUtil;
import com.mongodb.DBObject;

import java.util.*;

public class MongoUtils {
    public MongoUtils() {
    }

    public static List<Map<String, Object>> dbObjectListToMapList(List<DBObject> dbObjectList) {
        List<Map<String, Object>> list = new ArrayList();
        if (dbObjectList != null) {
            new HashMap();
            Iterator i$ = dbObjectList.iterator();
            while(i$.hasNext()) {
                DBObject dbObject = (DBObject)i$.next();
                Map<String, Object> map = new HashMap();
                Iterator it= dbObject.keySet().iterator();
                while(it.hasNext()) {
                    String key = StringUtil.toString(it.next());
                    map.put(key, dbObject.get(key));
                }
                list.add(map);
            }
        }

        return list;
    }
}


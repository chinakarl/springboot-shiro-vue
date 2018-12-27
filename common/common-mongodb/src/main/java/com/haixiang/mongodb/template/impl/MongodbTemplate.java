package com.haixiang.mongodb.template.impl;

import com.ai.market.common.mongo.Util.MongoUtils;
import com.ai.market.common.mongo.conf.MongoDBConfig;
import com.ai.market.common.mongo.page.MongoPageResult;
import com.ai.market.common.mongo.template.IMongodbTemplate;
import com.mongodb.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.*;

@Service
public class MongodbTemplate implements IMongodbTemplate {
    private static final Logger logger = LoggerFactory.getLogger(MongodbTemplate.class);
    private MongoClient client;

    private String dbName;

    @Autowired
    private MongoDBConfig mongoDBConfig;

    private Map<String, DB> dbMap = new HashMap();

    /**
     * @Author: zhaihx
     * @Description: 预留，调用端更改信息
     * @Date: 10:31 2018/8/14
     * @param hosts ip端口
     * @param userName 账号
     * @param password 密码
     * @param dataBase database
     **/
    public void setMongoDBConfig(String hosts,String userName,String password,String dataBase) {
        this.mongoDBConfig.setHosts(hosts);
        this.mongoDBConfig.setUserName(userName);
        this.mongoDBConfig.setPassword(password);
        this.mongoDBConfig.setDatabase(dataBase);
    }

    /**
    * @Author: zhaihx
    * @Description: 初始化
    * @Date: 10:31 2018/8/14
    **/
    @PostConstruct
    public void init() {
        boolean isUseMongo =mongoDBConfig.isUseMongo();
        if (!isUseMongo) {
            this.logger.info("mongo is not available now!");
        } else {
            try {
                List<ServerAddress> addresses = new ArrayList();
                String hosts = mongoDBConfig.getHosts();
                String[] arr$ = hosts.split(",");
                int len$ = arr$.length;
                String password;
                String database;
                for(int i$ = 0; i$ < len$; ++i$) {
                    password = arr$[i$];
                    database = password.split(":")[0];
                    int port = Integer.parseInt(password.split(":")[1]);
                    ServerAddress address = new ServerAddress(database, port);
                    addresses.add(address);
                }
                MongoClientOptions.Builder build = new MongoClientOptions.Builder();
                build.connectionsPerHost(mongoDBConfig.getMaxConnectionNum());
                build.socketKeepAlive(mongoDBConfig.isKeepAlive());
                build.threadsAllowedToBlockForConnectionMultiplier(mongoDBConfig.getMaxBlockNum());
                build.maxWaitTime(mongoDBConfig.getMaxWaitTime());
                build.connectTimeout(mongoDBConfig.getConnectTimeOut());
                MongoClientOptions myOptions = build.build();
                if (mongoDBConfig.isAuthorized()) {
                    this.client = new MongoClient(addresses, myOptions);
                } else {
                    String userName =mongoDBConfig.getUserName();
                    password = mongoDBConfig.getPassword();
                    database =mongoDBConfig.getDatabase();
                    dbName=mongoDBConfig.getDatabase();
                    MongoCredential credential = MongoCredential.createCredential(userName, database, password.toCharArray());
                    this.client = new MongoClient(addresses, Arrays.asList(credential), myOptions);
                }
            } catch (Exception var11) {
                var11.printStackTrace();
            }

        }
    }

    public List<String> getDatabaseNames() {
        return this.client.getDatabaseNames();
    }
    public int save(String collection, DBObject doc) {
        WriteResult result = this.getDB().getCollection(collection).save(doc);
        return result.getN();
    }
    public int insert(String collection, DBObject doc) {
        if (doc != null) {
            WriteResult result = this.getDB().getCollection(collection).insert(new DBObject[]{doc});
            return result.getN();
        } else {
            return -1;
        }
    }
    public int batchInsert(String collection, List<DBObject> docList) {
        if (docList != null) {
            WriteResult result = this.getDB().getCollection(collection).insert(docList);
            return result.getN();
        } else {
            return -1;
        }
    }
    public int update(String collection, DBObject queryDoc, DBObject updateDoc) {
        WriteResult result = this.getDB().getCollection(collection).updateMulti(queryDoc, updateDoc);
        return result.getN();
    }
    public int delete(String collection, DBObject doc) {
        WriteResult result = this.getDB().getCollection(collection).remove(doc);
        return result.getN();
    }
    public DBObject findOne(String collection, DBObject query, DBObject fields) {
        return this.getDB().getCollection(collection).findOne(query, fields);
    }

    public DBObject findOne(String collection, DBObject query, DBObject fields,DBObject orderBy) {
        return this.getDB().getCollection(collection).findOne(query, fields,orderBy);
    }
    public List<DBObject> find(String collection, DBObject query, DBObject fields) {
        List<DBObject> list = new ArrayList();
        DBCursor cursor = null;
        try {
            cursor = this.getDB().getCollection(collection).find(query, fields);

            while(cursor.hasNext()) {
                list.add(cursor.next());
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return list;
    }

    public List<DBObject> distinctFind(String collection, DBObject query, String key) {
        List<DBObject> list = this.getDB().getCollection(collection).distinct(key, query);
        return list;
    }

    public MongoPageResult distinctFind(String collection, DBObject query, String key, DBObject fields, DBObject orderBy, int pageNum, int pageSize) {
        long count = this.distinctCount(collection, query, key);
        DBObject groupFields = new BasicDBObject("_id", fields);
        DBObject group = new BasicDBObject("$group", groupFields);
        DBObject match = new BasicDBObject("$match", query);
        DBObject skip = new BasicDBObject("$skip", (pageNum - 1) * pageSize);
        DBObject limit = new BasicDBObject("$limit", pageSize);
        DBObject sort = new BasicDBObject("$sort", orderBy);
        List<DBObject> list = new ArrayList();
        Cursor aggregateOutput = this.getDB().getCollection(collection).aggregate(Arrays.asList(match, group, sort, skip, limit), AggregationOptions.builder().allowDiskUse(true).build());

        while(aggregateOutput.hasNext()) {
            DBObject doc = (DBObject)aggregateOutput.next();
            list.add(doc);
        }

        List<DBObject> pageList = new ArrayList();
        Iterator i$ = list.iterator();

        while(i$.hasNext()) {
            DBObject obj = (DBObject)i$.next();
            BasicDBObject obj2 = (BasicDBObject)obj;
            pageList.add((DBObject)obj2.get("_id"));
        }

        MongoPageResult pageResult = new MongoPageResult();
        pageResult.setCount(count);
        pageResult.setPageList(MongoUtils.dbObjectListToMapList(pageList));
        pageResult.setPage(pageNum);
        pageResult.setPageSize(pageSize);
        return pageResult;
    }

    private long distinctCount(String collection, DBObject query, String key) {
        DBObject groupFields = new BasicDBObject("_id", (Object)null);
        new BasicDBObject("$group", groupFields);
        new BasicDBObject("$match", query);
        return (long)this.getDB().getCollection(collection).distinct(key, query).size();
    }

    public List<DBObject> find(String collection, DBObject query, DBObject fields, DBObject orderBy) {
        List<DBObject> list = new ArrayList();
        DBCursor cursor = null;
        try {
            cursor = this.getDB().getCollection(collection).find(query, fields).sort(orderBy);

            while(cursor.hasNext()) {
                list.add(cursor.next());
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }

        }

        return list;
    }

    public MongoPageResult find(String collection, DBObject query, DBObject fields, DBObject orderBy, int pageNum, int pageSize) {
        List<DBObject> list = new ArrayList();
        DBCursor cursor = null;
        MongoPageResult pageResult = new MongoPageResult();
        long count = 0L;

        try {
            cursor = this.getDB().getCollection(collection).find(query, fields).sort(orderBy);
            count = (long)cursor.count();
            cursor = cursor.skip((pageNum - 1) * pageSize).limit(pageSize);

            while(cursor.hasNext()) {
                list.add(cursor.next());
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }

        }

        pageResult.setCount(count);
        pageResult.setPageList(MongoUtils.dbObjectListToMapList(list));
        pageResult.setPage(pageNum);
        pageResult.setPageSize(pageSize);
        return pageResult;
    }

    public long count(String collection, DBObject query) {
        return this.getDB().getCollection(collection).count(query);
    }

    public void dropDB() {
        this.getDB().dropDatabase();
    }

    public DB getDB() {
        if (dbName != null && !dbName.equals("")) {
            if (this.dbMap.get(dbName) == null) {
                DB db = this.client.getDB(dbName);
                this.dbMap.put(dbName, db);
            }

            return (DB)this.dbMap.get(dbName);
        } else {
            return null;
        }
    }
    @PreDestroy
    public void close() {
        if (this.client != null) {
            this.client.close();
        }

    }
}


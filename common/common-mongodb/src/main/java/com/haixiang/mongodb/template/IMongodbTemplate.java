package com.haixiang.mongodb.template;

import com.haixiang.mongodb.page.MongoPageResult;
import com.mongodb.DB;
import com.mongodb.DBObject;

import java.util.List;

//TODO 加注释
public interface IMongodbTemplate {
    List<String> getDatabaseNames();

    int save(String var2, DBObject var3);

    int insert(String var2, DBObject var3);

    int batchInsert(String var2, List<DBObject> var3);

    int update(String var2, DBObject var3, DBObject var4);

    int delete(String var2, DBObject var3);

    DBObject findOne(String var2, DBObject var3, DBObject var4);


    /**
    * @Author: zhaihx
    * @Description: 获取排序后的第一条数据
    * @Date: 11:10 2018/8/16
     * @param collection
     * @param query
     * @param fields
     * @param orderBy
    **/
    DBObject findOne(String collection, DBObject query, DBObject fields, DBObject orderBy);

    List<DBObject> find(String var2, DBObject var3, DBObject var4);

    List<DBObject> distinctFind(String var2, DBObject var3, String var4);

    MongoPageResult distinctFind(String var2, DBObject var3, String var4, DBObject var5, DBObject var6, int var7, int var8);

    List<DBObject> find(String var2, DBObject var3, DBObject var4, DBObject var5);

    MongoPageResult find(String var2, DBObject var3, DBObject var4, DBObject var5, int var6, int var7);

    long count(String var2, DBObject var3);

    DB getDB();
}

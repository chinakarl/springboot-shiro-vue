package com.haixiang.mongodb.conf;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties( prefix = "mongodbconfig")
public class MongoDBConfig implements InitializingBean {
    private String hosts="10.20.16.74:27017";
    private int maxConnectionNum=100;
    private boolean keepAlive=true;
    private int maxBlockNum=100;
    private int maxWaitTime=120000;
    private int connectTimeOut=60000;
    private boolean useMongo=true;
    private boolean authorized=false;
    private String userName="market";
    private String password="market";
    private String database="market";

    public String getHosts() {
        return hosts;
    }

    public void setHosts(String hosts) {
        this.hosts = hosts;
    }

    public int getMaxConnectionNum() {
        return maxConnectionNum;
    }

    public void setMaxConnectionNum(int maxConnectionNum) {
        this.maxConnectionNum = maxConnectionNum;
    }

    public boolean isKeepAlive() {
        return keepAlive;
    }

    public void setKeepAlive(boolean keepAlive) {
        this.keepAlive = keepAlive;
    }

    public int getMaxBlockNum() {
        return maxBlockNum;
    }

    public void setMaxBlockNum(int maxBlockNum) {
        this.maxBlockNum = maxBlockNum;
    }

    public int getMaxWaitTime() {
        return maxWaitTime;
    }

    public void setMaxWaitTime(int maxWaitTime) {
        this.maxWaitTime = maxWaitTime;
    }

    public int getConnectTimeOut() {
        return connectTimeOut;
    }

    public void setConnectTimeOut(int connectTimeOut) {
        this.connectTimeOut = connectTimeOut;
    }

    public boolean isUseMongo() {
        return useMongo;
    }

    public void setUseMongo(boolean useMongo) {
        this.useMongo = useMongo;
    }

    public boolean isAuthorized() {
        return authorized;
    }

    public void setAuthorized(boolean authorized) {
        this.authorized = authorized;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }
    @Override
    public void afterPropertiesSet()
            throws Exception {

    }
}


package com.haixiang.mongodb.page;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MongoPageResult implements Serializable {
    private int page;
    private int pageSize;
    private long count;
    private List<Map<String, Object>> pageList = new ArrayList();

    public MongoPageResult() {
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPage() {
        return this.page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public long getCount() {
        return this.count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public List<Map<String, Object>> getPageList() {
        return this.pageList;
    }

    public void setPageList(List<Map<String, Object>> pageList) {
        this.pageList = pageList;
    }
}


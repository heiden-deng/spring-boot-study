package com.heiden.neo4jdemo.entity;

import com.alibaba.fastjson.JSONObject;

/**
 * @Author: heiden
 * @Date: 2021/4/16 13:34
 * @Project: spring-boot-study
 */
public abstract class AbstractRelation {
    private String startId;
    private String endId;
    private String name;
    private String type;
    private Long id;

    public AbstractRelation() {
    }

    public AbstractRelation(String type) {
        this.type = type;
    }

    public String getStartId() {
        return startId;
    }

    public void setStartId(String startId) {
        this.startId = startId;
    }

    public String getEndId() {
        return endId;
    }

    public void setEndId(String endId) {
        this.endId = endId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public JSONObject toJSON() {
        JSONObject relationJson = new JSONObject();
        relationJson.put("id", id);
        relationJson.put("startId", startId);
        relationJson.put("endId", endId);
        relationJson.put("type", type);
        return relationJson;
    }
}

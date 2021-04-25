package com.heiden.neo4jdemo.entity;

import com.alibaba.fastjson.JSONObject;

/**
 * @Author: heiden
 * @Date: 2021/4/16 13:33
 * @Project: spring-boot-study
 */
public class AbstractNode {
    private Long id;
    private String name;
    private String taskId;
    private String type;

    public AbstractNode(String type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public JSONObject toJSON() {
        JSONObject nodeJson = new JSONObject();
        nodeJson.put("id", id);
        nodeJson.put("name", name);
        nodeJson.put("taskId", taskId);
        nodeJson.put("type", type);
        return nodeJson;
    }

}

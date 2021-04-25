package com.heiden.javaparserdemo.entity;

/**
 * @Author: heiden
 * @Date: 2021/4/25 16:33
 * @Project: spring-boot-study
 */
public enum  TaskStatus {

    Init(0,"初始化"),
    RUN(1,"运行中"),
    PAUSE(2,"暂停"),
    DISABLE(3,"禁用"),
    FINISH(4,"结束");

    private int status;
    private String name;

    public static String getName(int st){
        for (TaskStatus ts : TaskStatus.values()){
            if (ts.getStatus() == st){
                return ts.getName();
            }
        }
        return null;
    }

    TaskStatus(int st,String name){
        this.name = name;
        this.status = st;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

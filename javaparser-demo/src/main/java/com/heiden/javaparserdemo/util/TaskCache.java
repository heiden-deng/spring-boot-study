package com.heiden.javaparserdemo.util;

/**
 * @Author: heiden
 * @Date: 2021/4/25 16:30
 * @Project: spring-boot-study
 */
public class TaskCache {
    public static String jobName;
    public static String jobGroupName;
    public static int jobStatus = 0;//0:初始化，1:运行，2:暂停，3:终止
    public static String cronExpr;

}

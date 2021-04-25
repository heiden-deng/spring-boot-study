package com.heiden.javaparserdemo.task;

import com.heiden.javaparserdemo.entity.TaskStatus;
import com.heiden.javaparserdemo.util.TaskCache;

/**
 * @Author: heiden
 * @Date: 2021/4/25 16:28
 * @Project: spring-boot-study
 */
public interface TaskIntf extends Runnable{

    void execute();

    @Override
    default void run(){
        if (TaskCache.jobStatus != TaskStatus.RUN.getStatus()){
            return;
        }
        execute();
    }
}

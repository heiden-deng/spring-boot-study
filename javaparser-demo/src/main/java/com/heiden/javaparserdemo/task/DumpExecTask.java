package com.heiden.javaparserdemo.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: heiden
 * @Date: 2021/4/25 16:43
 * @Project: spring-boot-study
 */
public class DumpExecTask implements TaskIntf{
    private static final Logger logger = LoggerFactory.getLogger(DumpExecTask.class);
    private int i = 0;
    @Override
    public void execute() {
        logger.info("thread id:{},DumpExecTask execute times:{}", Thread.currentThread().getId(), ++i);
    }
}

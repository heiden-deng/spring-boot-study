package com.heiden.javaparserdemo.config;

import com.heiden.javaparserdemo.task.DumpExecTask;
import com.heiden.javaparserdemo.util.TaskCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @Author: heiden
 * @Date: 2021/4/25 16:24
 * @Project: spring-boot-study
 */
@Configuration
public class ScheduledConfig implements SchedulingConfigurer {

    @Value("${task.cron}")
    private String cronExpr;

    @Autowired
    private ApplicationContext context;

    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        DumpExecTask dumpExecTask = new DumpExecTask();
        TaskCache.cronExpr = cronExpr;
        scheduledTaskRegistrar.addTriggerTask(dumpExecTask, triggerContext -> {
            return new CronTrigger(TaskCache.cronExpr).nextExecutionTime(triggerContext);
        });
    }

    @Bean
    public Executor taskExecutor(){
        return Executors.newScheduledThreadPool(2);
    }

    public String getCronExpr() {
        return cronExpr;
    }

    public void setCronExpr(String cronExpr) {
        this.cronExpr = cronExpr;
    }

    public ApplicationContext getContext() {
        return context;
    }

    public void setContext(ApplicationContext context) {
        this.context = context;
    }
}

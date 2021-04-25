package com.heiden.javaparserdemo.controller;

import com.heiden.javaparserdemo.entity.TaskStatus;
import com.heiden.javaparserdemo.util.TaskCache;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: heiden
 * @Date: 2021/4/25 16:48
 * @Project: spring-boot-study
 */

@RestController
@RequestMapping("/task")
public class TestController {

    @GetMapping("/list")
    public String taskList(){
        return "task name: " + TaskCache.jobName + ",status: " + TaskStatus.getName(TaskCache.jobStatus);
    }

    @PostMapping("/cron")
    public String taskUpdateCron(@RequestBody String cron){
        TaskCache.cronExpr = cron;
        return "Success";
    }

    @GetMapping("/update")
    public String updateTask(@RequestParam int status){
        TaskCache.jobStatus = status;
        return "Success";
    }
}

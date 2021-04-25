package com.heiden.example.callchainbasedemo.controller.User;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: heiden
 * @Date: 2021/2/25 14:51
 * @Project: spring-boot-study
 */
@RestController
public class UserController {
    @GetMapping("/user")
    public String user(){
        return "success";
    }
}

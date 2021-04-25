package com.heiden.example.callchainbasedemo.controller;


import com.heiden.example.callchainbasedemo.service.PersonActIntf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private PersonActIntf personActIntf;

    @GetMapping("/sayhi")
    public String sayHi(@RequestParam int age){
        personActIntf.sayHi(age);
        return "success";
    }

    @GetMapping("/sayBay")
    public String sayBey(@RequestParam int age){
        personActIntf.sayBey(age);
        return "success";
    }


    @GetMapping("/eat")
    public String eat(@RequestParam int age){
        personActIntf.eat(age);
        return "success";
    }

}

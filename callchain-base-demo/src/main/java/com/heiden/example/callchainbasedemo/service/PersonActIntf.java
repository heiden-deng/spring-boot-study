package com.heiden.example.callchainbasedemo.service;

import com.heiden.example.callchainbasedemo.entity.Person;

public interface PersonActIntf {
    public void sayHi(int age);
    public void sayBey(int age);
    public void eat(int age);
    public void callPerson(Person person);
}

package com.heiden.example.callchainbasedemo.entity;

public class Person {
    private int id;
    private String name;
    private int age;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int compute(){
        if (age > 15 && age < 60){
            return 1;
        }else if (age >= 60){
            return 2;
        }else{
            return 0;
        }
    }
}

package com.heiden.example.callchainbasedemo.service.impl;

import com.heiden.example.callchainbasedemo.entity.Person;
import com.heiden.example.callchainbasedemo.service.PersonActIntf;
import org.springframework.stereotype.Service;

@Service
public class PersonActImpl implements PersonActIntf {
    @Override
    public void sayHi(int age) {
        int localAge = age;
        if (localAge > 60){
            System.out.println("大爷，您好");
        }else if (localAge > 40){
            System.out.println("中年人，你好");
        }else if (localAge > 15){
            System.out.println("青年人，你好");
        }else{
            System.out.println("小朋友,你好");
        }
    }

    @Override
    public void sayBey(int age) {

        Person person = new Person();
        person.setId(0);
        person.setAge(age);
        if (person.compute() == 0){
            System.out.println("青年人");
        }

        System.out.println("准备离开了");
        if (age < 20){
            System.out.println("走之前，记得打声招呼哦");
        }
        System.out.println("收拾东西");

    }

    @Override
    public void eat(int age) {
        System.out.println("开始吃饭了");
        switch (age/20){
            case 5:
            case 4:
            case 3:
            {
                System.out.println("老年人多次蔬菜");
                break;
            }
            case(2):
            {
                System.out.println("成人年多吃肉");
                break;
            }
            case 1:
            {
                System.out.println("小朋友多次有营养的食物");
                break;
            }
            default:
            {
                System.out.println("火星人，快点回火星吧");
            }

        }
    }
}

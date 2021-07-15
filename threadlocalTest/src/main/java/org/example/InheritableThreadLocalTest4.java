package org.example;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @Author: heiden
 * @Date: 2021/7/15 17:15
 * @Project: qrcode-utils
 */

//重写InheriatableThreadlocal对象的childValue函数，完全拷贝对象，
public class InheritableThreadLocalTest4 {
    public static ThreadLocal<Stu> threadLocal = new MyInheritableThreadLocal<>();
    public static ExecutorService executorService = Executors.newFixedThreadPool(1);

    public static void main(String[] args) throws InterruptedException {
        System.out.println("主线程开启");
        threadLocal.set(new Stu("aaa",1));

        executorService.submit(() -> {
            System.out.println("子线程读取本地变量：" + threadLocal.get());
            threadLocal.get().setAge(55);
            System.out.println("子线程读取本地变量：" + threadLocal.get());

        });

        TimeUnit.SECONDS.sleep(1);

        System.out.println("主线程读取本地变量：" + threadLocal.get());
        threadLocal.get().setAge(99);
        System.out.println("主线程读取本地变量：" + threadLocal.get());

        executorService.submit(() -> {
            System.out.println("子线程读取本地变量：" + threadLocal.get());
        });
    }

}

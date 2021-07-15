package org.example;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @Author: heiden
 * @Date: 2021/7/15 17:12
 * @Project: qrcode-utils
 */

//对于对象类型，InheritableThreadLocal在从父线程中拷贝对象时是复制对象引用，此时会有线程安全问题
public class InheritableThreadLocalTest3 {
    public static ThreadLocal<Stu> threadLocal = new InheritableThreadLocal<>();
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

        executorService.submit(() -> {
            System.out.println("子线程读取本地变量：" + threadLocal.get());
        });
    }
}

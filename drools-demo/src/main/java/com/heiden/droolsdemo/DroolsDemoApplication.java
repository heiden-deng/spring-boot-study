package com.heiden.droolsdemo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = {"com.heiden.droolsdemo.mapper"})
public class DroolsDemoApplication {

    public static double sigmod(double x){
        return 1/(1 + Math.exp((-1)*x));
    }
    public static int computeSigmod(int score, double param){
        double sig = sigmod(param);
        return (int)(Math.floor(2 * sig - 1) * score);
    }
    public static void main(String[] args) {

        //Object[] arrobject = new Object[]{4473305039327547984L,"com/xin/test/JacocoTest", 9};
        //boolean[] arrb1  = (boolean[])arrobject[0];

        int b = computeSigmod(80,20/4.0);
        try{
            int a = 2 / 0;
        }catch (Exception e){
            StringBuffer sb = new StringBuffer();
            StackTraceElement[] elements = e.getStackTrace();
            for (int i = 0; i < elements.length; i++){
                sb.append("ClassName:" + elements[i].getClassName())
                        .append(",FileName:" + elements[i].getFileName())
                        .append(",Line:" + elements[i].getLineNumber())
                        .append(",Method:" + elements[i].getMethodName())
                        .append("\n");
            }
            String res = sb.toString();
            System.out.println("Exception Trace: " + res);

        }

        SpringApplication.run(DroolsDemoApplication.class, args);
    }

}

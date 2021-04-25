package com.heiden.asm;

import org.objectweb.asm.*;
import org.objectweb.asm.util.ASMifier;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static org.objectweb.asm.Opcodes.*;

/**
 * Hello world!
 *
 */

class B{

};
public class App 
{
    public static void main( String[] args ) throws IOException {
        /**
         * 不做限制,可随意命名aaa/LeakInfo或bb/aa/cc/LeakInfo
         */
        String fullNameType = "com/heiden/asm/TestA";

        ClassWriter cw = new ClassWriter(0);
        /**
         * version:指定JAVA的版本
         * access:指定类的权限修饰符
         * name:指定类的全路径名,即类的全限定名中的"."替换成"/"
         * signature:签名
         * superName:继承的父类
         * interfaces:实现的接口(数组)
         */
        cw.visit(V1_8, ACC_PUBLIC, fullNameType, null, "java/lang/Object", null);
        /**
         * 创建该类的构造函数
         * visitMethod:
         * access:指定方法的权限修饰符
         * name:指定方法名,构造函数在字节码中的方法名为"<init>"
         * descriptor:方法描述符(public void add(Integer a,Integer b)-->(II)V)
         * signature:签名
         * exceptions:异常(数组)
         */
        MethodVisitor constructor = cw.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
        //将this参数入栈
        constructor.visitVarInsn(ALOAD, 0);
        /**
         * 调用父类无参构造函数
         * visitMethodInsn:
         * opcode:字节码指令(INVOKESPECIAL)
         * owner:被调用方法所属类(指定类的全路径名)
         * name:被调用的方法名
         * descriptor: 被调用方法的方法描述符
         * isInterface:被调用方法的所属类是否是接口
         */
        constructor.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V",false);
        constructor.visitInsn(RETURN);
        //指定局部变量栈的空间大小
        constructor.visitMaxs(1, 1);
        //构造方法的结束
        constructor.visitEnd();
        /**
         * 声明一个成员变量: private String name;
         * visitField:
         * access:成员变量的权限修饰符
         * name:成员变量名
         * descriptor:成员变量的定义描述符
         * signature:签名
         * value:成员变量的初始值
         */
        FieldVisitor fv = cw.visitField(ACC_PRIVATE, "name", "Ljava/lang/String;", null, null);
        fv.visitEnd();
        /**
         * 构造setName方法:public void setName(String name);
         */
        MethodVisitor setName = cw.visitMethod(ACC_PUBLIC, "setName", "(Ljava/lang/String;)V", null, null);
        setName.visitCode();
        //this参数
        setName.visitVarInsn(ALOAD, 0);
        //传入的name参数
        setName.visitVarInsn(ALOAD, 1);
        //fullNameType:类的全路径名,即zzz/ddd/ccc/LeakInfo
        setName.visitFieldInsn(PUTFIELD, fullNameType, "name", "Ljava/lang/String;");
        setName.visitMaxs(2, 2);
        setName.visitInsn(RETURN);
        setName.visitEnd();
        /**
         * 构造getName方法
         */
        MethodVisitor getName = cw.visitMethod(ACC_PUBLIC, "getName", "()Ljava/lang/String;", null, null);
        getName.visitCode();

        generateAccessCall(123,"com.heiden.asm.TestA",20,getName);

        getName.visitVarInsn(ALOAD, 0);
        getName.visitFieldInsn(GETFIELD, fullNameType, "name", "Ljava/lang/String;");
        getName.visitMaxs(1, 1);
        getName.visitInsn(ARETURN);
        getName.visitEnd();
        //完成
        cw.visitEnd();

        byte[] code = cw.toByteArray();
        //可以将其生成class文件保存在磁盘上,或者直接通过classLoad加载
        FileOutputStream fos = new FileOutputStream(new File("TestA.class"));
        fos.write(code);
        fos.close();

        //自定义ClassLoader
 /*       MyClassLoader classLoader = new MyClassLoader();
        *//**
         * name:指定的是加载类的全限制名,即通过"."分隔的
         *//*
        Class<?> cls = classLoader.defineClassPublic(fullNameType.replace("/","."), code, 0, code.length);
        Object o = cls.newInstance();
        //调用setName赋值
        Method setNameMethod = cls.getMethod("setName", String.class);
        setNameMethod.invoke(o, "k0bin");
        //调用getName方法查看值
        Method getNameMethod = cls.getMethod("getName");
        Object name = getNameMethod.invoke(o);
        System.out.println(name);*/

        System.out.println( "Hello World!" );
    }

    public static void generateArgumentArray(final long classid,
                                             final String classname, final int probecount, final MethodVisitor mv) {
        mv.visitInsn(Opcodes.ICONST_3);
        mv.visitTypeInsn(Opcodes.ANEWARRAY, "java/lang/Object");

        // Class Id:
        mv.visitInsn(Opcodes.DUP);
        mv.visitInsn(Opcodes.ICONST_0);
        mv.visitLdcInsn(Long.valueOf(classid));
        mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Long", "valueOf",
                "(J)Ljava/lang/Long;", false);
        mv.visitInsn(Opcodes.AASTORE);

        // Class Name:
        mv.visitInsn(Opcodes.DUP);
        mv.visitInsn(Opcodes.ICONST_1);
        mv.visitLdcInsn(classname);
        mv.visitInsn(Opcodes.AASTORE);

        // Probe Count:
        mv.visitInsn(Opcodes.DUP);
        mv.visitInsn(Opcodes.ICONST_2);
        InstrSupport.push(mv, probecount);
        mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Integer",
                "valueOf", "(I)Ljava/lang/Integer;", false);
        mv.visitInsn(Opcodes.AASTORE);
    }

    /**
     * Generates the code that calls a instance through the
     * JRE API method {@link Object#equals(Object)}. The code pops a
     * {@link Object} instance from the stack and pushes the probe array of type
     * <code>boolean[]</code> on the operand stack. The generated code requires
     * a stack size of 6.
     *
     * @param classid
     *            class identifier
     * @param classname
     *            VM class name
     * @param probecount
     *            probe count for this class
     * @param mv
     *            visitor to emit generated code
     */
    public static void generateAccessCall(final long classid,
                                          final String classname, final int probecount, final MethodVisitor mv) {
        // stack[0]: Ljava/lang/Object;

        generateArgumentArray(classid, classname, probecount, mv);

        // stack[1]: [Ljava/lang/Object;
        // stack[0]: Ljava/lang/Object;

        mv.visitInsn(Opcodes.DUP_X1);

        // stack[2]: [Ljava/lang/Object;
        // stack[1]: Ljava/lang/Object;
        // stack[0]: [Ljava/lang/Object;

        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Object", "equals",
                "(Ljava/lang/Object;)Z", false);
        mv.visitInsn(Opcodes.POP);

        // stack[0]: [Ljava/lang/Object;

        mv.visitInsn(Opcodes.ICONST_0);
        mv.visitInsn(Opcodes.AALOAD);

        // stack[0]: [Z

        mv.visitTypeInsn(Opcodes.CHECKCAST, InstrSupport.DATAFIELD_DESC);
    }
}

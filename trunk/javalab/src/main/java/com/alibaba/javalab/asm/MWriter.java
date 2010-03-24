/**
 * Function: 
 * 
 * File Created at 2010-2-21
 * $Id$
 * 
 * Copyright 2009 Alibaba.com Croporation Limited.
 * All rights reserved.
 */
package com.alibaba.javalab.asm;

import static com.sleepycat.asm.Opcodes.ACC_PUBLIC;
import static com.sleepycat.asm.Opcodes.ACC_STATIC;
import static com.sleepycat.asm.Opcodes.GETSTATIC;
import static com.sleepycat.asm.Opcodes.INVOKEVIRTUAL;
import static com.sleepycat.asm.Opcodes.RETURN;
import static com.sleepycat.asm.Opcodes.V1_6;
import static org.objectweb.asm.Opcodes.ALOAD;
import static org.objectweb.asm.Opcodes.DUP;
import static org.objectweb.asm.Opcodes.INVOKESPECIAL;
import static org.objectweb.asm.Opcodes.NEW;

import java.lang.reflect.Method;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

/**
 * @author li.jinl
 */
public class MWriter {

    private static final String        CLASS_NAME     = "com.alibaba.javalab.asm.Example";
    private static final String        ASM_CLASS_NAME = CLASS_NAME.replace('.', '/');

    private static final MyClassLoader cl             = new MyClassLoader();

    public static void main(String[] args) throws Exception {
        //example1();
        example2();
    }

    public static void example1() throws Exception {
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        cw.visit(V1_6, ACC_PUBLIC, ASM_CLASS_NAME, null, "java/lang/Object", null);

        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "greet", "()V", null, null);
        mv.visitCode();

        mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        mv.visitLdcInsn("hello");
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V");
        mv.visitInsn(RETURN);

        mv.visitMaxs(0, 0);
        mv.visitEnd();
        cw.visitEnd();

        byte[] b = cw.toByteArray();
        Class<?> clazz = cl.defineClass(CLASS_NAME, b);
        Method greet = clazz.getMethod("greet");
        greet.invoke(null);
    }

    public static void example2() throws Exception {
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        cw.visit(V1_6, ACC_PUBLIC, ASM_CLASS_NAME, null, "java/lang/Object", null);

        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "greet", "(Ljava/lang/String;)V", null, null);
        mv.visitCode();

        mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        mv.visitTypeInsn(NEW, "java/lang/StringBuilder");
        mv.visitInsn(DUP);
        mv.visitLdcInsn("hello:");
        mv.visitMethodInsn(INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "(Ljava/lang/String;)V");
        mv.visitVarInsn(ALOAD, 0);
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append",
                "(Ljava/lang/String;)Ljava/lang/StringBuilder;");
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;");
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V");

        mv.visitInsn(RETURN);
        mv.visitMaxs(0, 0);
        mv.visitEnd();
        cw.visitEnd();

        byte[] b = cw.toByteArray();
        Class<?> clazz = cl.defineClass(CLASS_NAME, b);
        Method greet = clazz.getMethod("greet", String.class);
        greet.invoke(null, "stone2083");
    }
}

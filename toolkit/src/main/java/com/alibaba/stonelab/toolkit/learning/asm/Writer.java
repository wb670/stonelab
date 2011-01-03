/**
 * Function: 
 * 
 * File Created at 2010-2-20
 * $Id$
 * 
 * Copyright 2009 Alibaba.com Croporation Limited.
 * All rights reserved.
 */
package com.alibaba.stonelab.toolkit.learning.asm;

import static org.objectweb.asm.Opcodes.ACC_ABSTRACT;
import static org.objectweb.asm.Opcodes.ACC_FINAL;
import static org.objectweb.asm.Opcodes.ACC_INTERFACE;
import static org.objectweb.asm.Opcodes.ACC_PUBLIC;
import static org.objectweb.asm.Opcodes.ACC_STATIC;
import static org.objectweb.asm.Opcodes.V1_6;

import org.objectweb.asm.ClassWriter;

/**
 * @author li.jinl
 */
public class Writer {

    private static final MyClassLoader cl = new MyClassLoader();

    public static void main(String[] args) throws InstantiationException, IllegalAccessException,
            IllegalArgumentException, SecurityException, NoSuchFieldException {
        ClassWriter cw = new ClassWriter(0);
        cw.visit(V1_6, ACC_PUBLIC + ACC_ABSTRACT + ACC_INTERFACE, "com/alibaba/javalab/asm/Demo",
                null, "java/lang/Object", null);
        cw.visitField(ACC_PUBLIC + ACC_FINAL + ACC_STATIC, "id", "I", null, 1).visitEnd();
        cw.visitField(ACC_PUBLIC + ACC_FINAL + ACC_STATIC, "name", "I", null, 10).visitEnd();
        cw.visitMethod(ACC_PUBLIC + ACC_ABSTRACT, "greet", "()Ljava/lang/String;", null, null)
                .visitEnd();
        cw.visitEnd();
        byte[] b = cw.toByteArray();

        Class<?> clazz = cl.defineClass("com.alibaba.javalab.asm.Demo", b);
        System.out.println(clazz);
        System.out.println(clazz.getField("id").getInt(null));
        System.out.println(clazz.getField("name").getInt(null));
    }
}

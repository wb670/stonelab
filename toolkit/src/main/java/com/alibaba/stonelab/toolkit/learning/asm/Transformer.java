/**
 * Function: 
 * 
 * File Created at 2010-2-21
 * $Id$
 * 
 * Copyright 2009 Alibaba.com Croporation Limited.
 * All rights reserved.
 */
package com.alibaba.stonelab.toolkit.learning.asm;

import java.lang.reflect.Method;

import org.objectweb.asm.ClassAdapter;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

/**
 * @author li.jinl
 */
public class Transformer {

    private static final MyClassLoader cl = new MyClassLoader();

    public static void main(String[] args) throws Exception {
        Class<?> clazz = cl.loadClass("com.alibaba.javalab.asm.MyObject");
        Method greet = clazz.getMethod("greet");
        String value = (String) greet.invoke(clazz.newInstance());
        System.out.println(value);

        ClassReader cr = new ClassReader("com.alibaba.javalab.asm.MyObject");
        ClassWriter cw = new ClassWriter(0);
        ClassAdapter ca = new RemoveMethodClassAdapter("greet", cw);
        cr.accept(ca, 0);
        byte[] b = cw.toByteArray();

        Class<?> newclazz = cl.defineClass("com.alibaba.javalab.asm.MyObject", b);
        try {
            newclazz.getMethod("greet");
        } catch (Exception e) {
            System.out.println("No Such Method.");
        }

    }

    public static class RemoveMethodClassAdapter extends ClassAdapter {

        private String mname;

        public RemoveMethodClassAdapter(String mname, ClassVisitor cv) {
            super(cv);
            this.mname = mname;
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String desc, String signature,
                                         String[] exceptions) {
            if (!name.equals(mname)) {
                return cv.visitMethod(access, name, desc, signature, exceptions);
            }
            //remove method
            return null;
        }

    }

}

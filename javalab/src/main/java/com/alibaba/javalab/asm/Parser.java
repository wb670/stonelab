/**
 * Function: 
 * 
 * File Created at 2010-2-20
 * $Id$
 * 
 * Copyright 2009 Alibaba.com Croporation Limited.
 * All rights reserved.
 */
package com.alibaba.javalab.asm;

import java.io.IOException;
import java.io.Serializable;

import org.apache.commons.lang.StringUtils;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;

/**
 * @author li.jinl
 */
public class Parser implements Serializable {

    private static final long serialVersionUID = -3528603639807473856L;

    public static void main(String[] args) throws IOException {
        ClassReader cr = new ClassReader("com.alibaba.javalab.asm.MyObject");
        ClassPrinter cp = new ClassPrinter();
        cr.accept(cp, 0);
    }

    public static class ClassPrinter implements ClassVisitor {

        @Override
        public void visit(int version, int access, String name, String signature, String superName,
                          String[] interfaces) {
            StringBuilder sb = new StringBuilder(256);
            sb.append("[visit]");
            sb.append("version:").append(version).append("==");
            sb.append("access:").append(access).append("==");
            sb.append("name:").append(name).append("==");
            sb.append("signature:").append(signature).append("==");
            sb.append("superName:").append(superName).append("==");
            sb.append("interfaces:").append(StringUtils.join(interfaces, ","));
            System.out.println(sb.toString());
        }

        @Override
        public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
            StringBuilder sb = new StringBuilder(256);
            sb.append("[visitAnnotation]");
            sb.append("desc:").append(desc).append("==");
            sb.append("visible:").append(visible).append("==");
            System.out.println(sb.toString());
            return null;
        }

        @Override
        public void visitAttribute(Attribute attr) {
            StringBuilder sb = new StringBuilder(256);
            sb.append("[visitAnnotation]");
            sb.append("attr:").append(attr);
            System.out.println(sb.toString());
        }

        @Override
        public void visitEnd() {
            StringBuilder sb = new StringBuilder(256);
            sb.append("[visitEnd]");
            System.out.println(sb.toString());
        }

        @Override
        public FieldVisitor visitField(int access, String name, String desc, String signature,
                                       Object value) {
            StringBuilder sb = new StringBuilder(256);
            sb.append("[visitField]");
            sb.append("access:").append(access).append("==");
            sb.append("name:").append(name).append("==");
            sb.append("desc:").append(desc).append("==");
            sb.append("signature:").append(signature).append("==");
            sb.append("value:").append(value);
            System.out.println(sb.toString());
            return null;
        }

        @Override
        public void visitInnerClass(String name, String outerName, String innerName, int access) {
            StringBuilder sb = new StringBuilder(256);
            sb.append("[visitInnerClass]");
            sb.append("name:").append(name).append("==");
            sb.append("outerName:").append(outerName).append("==");
            sb.append("innerName:").append(innerName).append("==");
            sb.append("access:").append(access);
            System.out.println(sb.toString());

        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String desc, String signature,
                                         String[] exceptions) {
            StringBuilder sb = new StringBuilder(256);
            sb.append("[visitMethod]");
            sb.append("access:").append(access).append("==");
            sb.append("name:").append(name).append("==");
            sb.append("desc:").append(desc).append("==");
            sb.append("signature:").append(signature).append("==");
            sb.append("exceptions:").append(StringUtils.join(exceptions, ","));
            System.out.println(sb.toString());
            return null;
        }

        @Override
        public void visitOuterClass(String owner, String name, String desc) {
            StringBuilder sb = new StringBuilder(256);
            sb.append("[visitOuterClass]");
            sb.append("owner:").append(owner).append("==");
            sb.append("name:").append(name).append("==");
            sb.append("desc:").append(desc);
            System.out.println(sb.toString());
        }

        @Override
        public void visitSource(String source, String debug) {
            StringBuilder sb = new StringBuilder(256);
            sb.append("[visitSource]");
            sb.append("source:").append(source).append("==");
            sb.append("debug:").append(debug);
            System.out.println(sb.toString());
        }
    }

}

/*
 * Copyright 1999-2004 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.alibaba.stonelab.toolkit.learning.exception;

/**
 * @author li.jinl 2010-7-9 上午09:53:12
 */
public class Tester {

    public static void main(String[] args) {
        try {
            throw new CustomException("msg");
            // throw new ExtCustomException("msg");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class CustomException extends Exception {

        private static final long serialVersionUID = -6879298763723247455L;

        private String            message;

        public CustomException(String message){
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

    }

    public static class Child extends Parent {

        public Child(String message){
            System.out.println("MC");
        }

        @Override
        public void init() {
            System.out.println("CI");
        }

    }

    public static class Parent {

        public Parent(){
            System.out.println("P");
            init();
        }

        public Parent(String message){
            System.out.println("MP");
            init();
        }

        public void init() {
            System.out.println("PI");
        }

    }

    public static class ExtCustomException extends Exception {

        private static final long serialVersionUID = -6879298763723247455L;

        private String            message;

        public ExtCustomException(String message){
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        @Override
        public Throwable fillInStackTrace() {
            return this;
        }
    }

}

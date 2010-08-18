package com.alibaba.helloworld.webx2.biz.app;

import static org.junit.Assert.*;

import org.junit.Test;

public class AppTest {

    @Test
    public void getPalindrome() {
        App app = new App();
        String res = app.getPalindrome("ab");
        assertEquals("abba", res);

        res = app.getPalindrome(null);
        assertNull(res);
    }
}

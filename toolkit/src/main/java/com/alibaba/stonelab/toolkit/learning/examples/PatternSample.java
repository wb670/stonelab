/*
 * Copyright 1999-2004 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.alibaba.stonelab.toolkit.learning.examples;

import org.apache.oro.text.regex.Pattern;
import org.apache.oro.text.regex.PatternCompiler;
import org.apache.oro.text.regex.PatternMatcher;
import org.apache.oro.text.regex.PatternMatcherInput;
import org.apache.oro.text.regex.Perl5Compiler;
import org.apache.oro.text.regex.Perl5Matcher;

/**
 * @author li.jinl 2010-7-14 下午10:08:23
 */
public class PatternSample {

    private static final String CONTENT = "a${a}b${b}c${c}";

    public static void main(String[] args) throws Exception {
        PatternCompiler pc = new Perl5Compiler();
        Pattern p = pc.compile("\\$\\{.*?\\}");
        PatternMatcher matcher = new Perl5Matcher();
        PatternMatcherInput in = new PatternMatcherInput(CONTENT);
        while (matcher.contains(in, p)) {
            System.out.println(matcher.getMatch().toString());
        }
    }
}

/*
 * Copyright 1999-2004 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.alibaba.stonelab.toolkit.autoconf;

import java.util.HashSet;
import java.util.Set;

import org.apache.oro.text.regex.MalformedPatternException;
import org.apache.oro.text.regex.Pattern;
import org.apache.oro.text.regex.PatternCompiler;
import org.apache.oro.text.regex.PatternMatcher;
import org.apache.oro.text.regex.PatternMatcherInput;
import org.apache.oro.text.regex.Perl5Compiler;
import org.apache.oro.text.regex.Perl5Matcher;

/**
 * @author li.jinl 2010-7-13 下午02:55:13
 */
public class AutoconfUtil {

    private static PatternCompiler compiler = new Perl5Compiler();
    private static Pattern         pattern;

    static {
        try {
            pattern = compiler.compile("\\$\\{(.*?)\\}");
        } catch (MalformedPatternException e) {
            System.out.println(e);
        }
    }

    public static Set<String> parsePlaceholder(String str) {
        Set<String> props = new HashSet<String>();

        PatternMatcher matcher = new Perl5Matcher();
        PatternMatcherInput input = new PatternMatcherInput(str);
        while (matcher.contains(input, pattern)) {
            props.add(matcher.getMatch().group(1));
        }

        return props;
    }
}

/**
 * Function: 
 * 
 * File Created at 2010-3-5
 * $Id$
 * 
 * Copyright 2009 Alibaba.com Croporation Limited.
 * All rights reserved.
 */
package com.alibaba.javalab.misc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;

public class Test {

    public static void main(String[] args) throws Exception {
        File f = new File("d:/tmp/exodus.txt");
        InputStream in = new FileInputStream(f);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in, "GBK"));
        String line = null;

        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        int total = 0;

        int sum = 0;
        int count = 0;
        while ((line = reader.readLine()) != null) {
            if (StringUtils.isNumeric(line.trim())) {
                int num = Integer.valueOf(line) / 1000;
                total++;
                //大于1000ms
                if (num > 1000)
                    setMap(map, 1000);
                else if (num > 800)
                    setMap(map, 800);
                else if (num > 600)
                    setMap(map, 600);
                else if (num > 400)
                    setMap(map, 400);
                else if (num > 300)
                    setMap(map, 300);
                else if (num > 200)
                    setMap(map, 200);
                else if (num > 100)
                    setMap(map, 100);
                else if (num > 50)
                    setMap(map, 50);
                else
                    setMap(map, 0);

                if (num <= 200) {
                    count++;
                    sum += num;
                }
            }

        }
        Set<Entry<Integer, Integer>> set = map.entrySet();
        for (Entry<Integer, Integer> entry : set) {
            System.out.println(entry.getKey() + "\t" + entry.getValue() + "\t" + (entry.getValue() * 100.0 / total));
        }

        System.out.println(sum / count);

        in.close();
    }

    private static void setMap(Map<Integer, Integer> map, Integer key) {
        if (map.containsKey(key)) {
            map.put(key, map.get(key) + 1);
        } else {
            map.put(key, 1);
        }
    }
}

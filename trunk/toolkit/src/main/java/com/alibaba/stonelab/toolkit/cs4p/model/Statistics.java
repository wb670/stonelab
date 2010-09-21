/*
 * Copyright 1999-2004 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.alibaba.stonelab.toolkit.cs4p.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Stone.J 2010-9-19 下午11:01:10
 */
public class Statistics {

    private static final Statistics                  INSTANCE = new Statistics();
    private static final Map<String, StatisticsInfo> STORE    = new HashMap<String, StatisticsInfo>();

    private Statistics(){
    }

    public static final Statistics getInstance() {
        return INSTANCE;
    }

    public void addAnnihilate(String name, int value) {
        if (!STORE.containsKey(name)) {
            StatisticsInfo info = new StatisticsInfo(name, value, 0);
            STORE.put(name, info);
        }
        STORE.get(name).addAnnihilate(value);
    }

    public void addDie(String name, int value) {
        if (!STORE.containsKey(name)) {
            StatisticsInfo info = new StatisticsInfo(name, 0, value);
            STORE.put(name, info);
        }
        STORE.get(name).addDie(value);
    }

    public Collection<StatisticsInfo> getStatistics() {
        return STORE.values();
    }

    public static final class StatisticsInfo {

        private String name;
        private int    annihilate;
        private int    die;

        public StatisticsInfo(String name, int annihilate, int die){
            this.name = name;
            this.annihilate = annihilate;
            this.die = die;
        }

        public void addAnnihilate(int value) {
            this.annihilate += value;
        }

        public void addDie(int value) {
            this.die += value;
        }

        public String getName() {
            return name;
        }

        public int getAnnihilate() {
            return annihilate;
        }

        public int getDie() {
            return die;
        }

    }

}

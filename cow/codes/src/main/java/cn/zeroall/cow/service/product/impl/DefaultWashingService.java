package cn.zeroall.cow.service.product.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;

import cn.zeroall.cow.dal.product.dao.WashingDAO;
import cn.zeroall.cow.dal.product.po.WashingPO;
import cn.zeroall.cow.service.product.WashingService;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Mar 5, 2009
 */
public class DefaultWashingService implements WashingService {

    private WashingDAO washingDAO;

    private WashingCache cache = new WashingCache();

    public void init() {
        load();
    }

    @Override
    public WashingPO getWashingById(Integer id) {
        return cache.getMap().get(id);
    }

    @Override
    public List<WashingPO> getWashingByIds(Integer[] ids) {
        if (ArrayUtils.isEmpty(ids)) {
            return null;
        }
        List<WashingPO> washings = new ArrayList<WashingPO>(ids.length);
        for (Integer id : ids) {
            washings.add(cache.getMap().get(id));
        }
        return washings;
    }

    @Override
    public List<WashingPO> getWashings() {
        if (!cache.isLoaded()) {
            load();
        }
        return cache.getList();
    }

    /**
     * load
     */
    private void load() {
        // read from dao
        List<WashingPO> list = washingDAO.findWashings();
        // set list
        cache.setList(list);
        // set map
        Map<Integer, WashingPO> map = new HashMap<Integer, WashingPO>();
        for (WashingPO washingPO : list) {
            map.put(washingPO.getId(), washingPO);
        }
        cache.setMap(map);
        // set isLoaded
        cache.setLoaded(true);
    }

    public void setWashingDAO(WashingDAO washingDAO) {
        this.washingDAO = washingDAO;
    }

    /**
     * cache
     * 
     * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Mar 5, 2009
     */
    private static class WashingCache {

        private List<WashingPO> list;
        private Map<Integer, WashingPO> map;
        private boolean isLoaded = false;

        public List<WashingPO> getList() {
            return list;
        }

        public void setList(List<WashingPO> list) {
            this.list = list;
        }

        public Map<Integer, WashingPO> getMap() {
            return map;
        }

        public void setMap(Map<Integer, WashingPO> map) {
            this.map = map;
        }

        public boolean isLoaded() {
            return isLoaded;
        }

        public void setLoaded(boolean isLoaded) {
            this.isLoaded = isLoaded;
        }

    }

}

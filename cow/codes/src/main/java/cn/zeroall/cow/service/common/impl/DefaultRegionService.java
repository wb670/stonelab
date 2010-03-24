package cn.zeroall.cow.service.common.impl;

import cn.zeroall.chameleon.vtree.Tree;
import cn.zeroall.chameleon.vtree.TreeException;
import cn.zeroall.cow.dal.common.dao.RegionDAO;
import cn.zeroall.cow.dal.common.po.RegionPO;
import cn.zeroall.cow.service.common.RegionService;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Feb 15, 2009
 */
public class DefaultRegionService implements RegionService {

    private RegionDAO regionDAO;

    private Tree<RegionPO> cached;

    public Tree<RegionPO> getRegion() throws TreeException {
        if (cached == null) {
            Tree<RegionPO> tree = new Tree<RegionPO>();
            tree.setNodeStore(regionDAO);
            tree.build();
            cached = tree;
        }
        return cached;
    }

    public void setRegionDAO(RegionDAO regionDAO) {
        this.regionDAO = regionDAO;
    }

}
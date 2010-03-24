package cn.zeroall.cow.service.common;

import cn.zeroall.chameleon.vtree.Tree;
import cn.zeroall.chameleon.vtree.TreeException;
import cn.zeroall.cow.dal.common.po.RegionPO;
import cn.zeroall.cow.service.BaseService;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Mar 5, 2009
 */
public interface RegionService extends BaseService {

    public Tree<RegionPO> getRegion() throws TreeException;

}
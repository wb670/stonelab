package cn.zeroall.cow.biz.misc.ao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.zeroall.chameleon.vtree.Node;
import cn.zeroall.chameleon.vtree.Tree;
import cn.zeroall.chameleon.vtree.TreeException;
import cn.zeroall.cow.biz.BaseAO;
import cn.zeroall.cow.dal.common.po.RegionPO;
import cn.zeroall.cow.service.common.RegionService;
import cn.zeroall.cow.service.member.model.Region;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> May 12, 2009
 */
public class RegionQueryAO implements BaseAO {

    private static final Log log = LogFactory.getLog(RegionQueryAO.class);

    private String id;

    private RegionService regionService;

    public List<Region> query() {
        Tree<RegionPO> regionTree = null;
        try {
            regionTree = regionService.getRegion();
        } catch (TreeException e) {
            log.error("get region tree error.", e);
            return new ArrayList<Region>(0);
        }
        if (regionTree == null) {
            return new ArrayList<Region>(0);
        }
        RegionPO regionPO = regionTree.getNodeById(getIntId());
        if (regionPO == null) {
            return new ArrayList<Region>(0);
        }
        return toRegionList(regionPO.getChildren());
    }

    private int getIntId() {
        if (StringUtils.isNumeric(id)) {
            return Integer.valueOf(id);
        }
        return -1;
    }

    private List<Region> toRegionList(List<Node> list) {
        if (list == null || list.isEmpty()) {
            return new ArrayList<Region>(0);
        }
        List<Region> regions = new ArrayList<Region>(list.size());
        for (Node node : list) {
            regions.add(new Region(node.getId(), node.getName()));
        }
        return regions;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setRegionService(RegionService regionService) {
        this.regionService = regionService;
    }

}

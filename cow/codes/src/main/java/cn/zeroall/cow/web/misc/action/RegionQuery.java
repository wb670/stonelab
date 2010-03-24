package cn.zeroall.cow.web.misc.action;

import java.util.List;

import cn.zeroall.cow.biz.misc.ao.RegionQueryAO;
import cn.zeroall.cow.service.member.model.Region;
import cn.zeroall.cow.web.BaseAction;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> May 11, 2009
 */
public class RegionQuery extends BaseAction {

    private static final long serialVersionUID = 2390835731777013425L;

    private String id;

    private List<Region> regions;

    private RegionQueryAO regionQueryAO;

    @Override
    public String execute() throws Exception {
        regionQueryAO.setId(id);
        regions = regionQueryAO.query();
        return SUCCESS;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Region> getRegions() {
        return regions;
    }

    public void setRegions(List<Region> regions) {
        this.regions = regions;
    }

    public void setRegionQueryAO(RegionQueryAO regionQueryAO) {
        this.regionQueryAO = regionQueryAO;
    }

}

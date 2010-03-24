package cn.zeroall.cow.biz.order.vo;

import cn.zeroall.chameleon.vtree.Tree;
import cn.zeroall.cow.dal.common.po.RegionPO;
import cn.zeroall.cow.dal.order.po.AddressInfoPO;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Sep 13, 2009
 */
public class AddressInfoVO extends AddressInfoPO {

    private static final long serialVersionUID = -7727961771217352573L;

    private Tree<RegionPO> tree;

    private String provinceName;
    private String cityName;
    private String areaName;

    public AddressInfoVO(Tree<RegionPO> tree) {
        this.tree = tree;
    }

    public String getProvinceName() {
        if (provinceName == null) {
            provinceName = getRegionName(getProvince());
        }
        return provinceName;
    }

    public String getCityName() {
        if (cityName == null) {
            cityName = getRegionName(getCity());
        }
        return cityName;
    }

    public String getAreaName() {
        if (areaName == null) {
            areaName = getRegionName(getArea());
        }
        return areaName;
    }

    private String getRegionName(Integer code) {
        RegionPO regionPO = tree.getNodeById(code);
        return regionPO != null ? regionPO.getName() : "";
    }

}

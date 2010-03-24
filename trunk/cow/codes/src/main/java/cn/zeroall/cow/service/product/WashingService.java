package cn.zeroall.cow.service.product;

import java.util.List;

import cn.zeroall.cow.dal.product.po.WashingPO;
import cn.zeroall.cow.service.BaseService;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Mar 5, 2009
 */
public interface WashingService extends BaseService {

    public WashingPO getWashingById(Integer id);

    public List<WashingPO> getWashingByIds(Integer[] ids);

    public List<WashingPO> getWashings();

}

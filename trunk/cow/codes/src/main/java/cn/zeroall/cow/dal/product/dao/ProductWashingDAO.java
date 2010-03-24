package cn.zeroall.cow.dal.product.dao;

import java.util.List;

import cn.zeroall.cow.dal.product.po.ProductWashingPO;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Mar 4, 2009
 */
public interface ProductWashingDAO {

    public ProductWashingPO findById(Integer id);

    public List<ProductWashingPO> findByProductId(Integer productId);

}

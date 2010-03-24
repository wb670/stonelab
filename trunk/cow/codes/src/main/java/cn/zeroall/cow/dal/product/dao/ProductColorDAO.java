package cn.zeroall.cow.dal.product.dao;

import java.util.List;

import cn.zeroall.cow.dal.product.po.ProductColorPO;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Mar 4, 2009
 */
public interface ProductColorDAO {

    public ProductColorPO findById(Integer id);

    public List<ProductColorPO> findByProductId(Integer productId);

}

package cn.zeroall.cow.dal.product.dao;

import java.util.List;

import cn.zeroall.cow.dal.product.po.ProductInfoPO;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Mar 4, 2009
 */
public interface ProductInfoDAO {

    public ProductInfoPO findById(Integer id);

    public List<ProductInfoPO> findByIds(Integer[] ids);

    public Integer countByCategoryIds(Integer[] categoryIds);

    public List<ProductInfoPO> findByCategoryIds(Integer[] categoryIds, Integer start, Integer limit);

    public Integer addProduct(ProductInfoPO pInfo);

    public Integer countByPriceRange(Integer min, Integer max);

    public List<ProductInfoPO> findByPriceRange(Integer min, Integer max, Integer start, Integer limit);
}

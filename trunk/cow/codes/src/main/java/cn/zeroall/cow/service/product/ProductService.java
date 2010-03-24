package cn.zeroall.cow.service.product;

import java.util.List;

import cn.zeroall.cow.service.BaseService;
import cn.zeroall.cow.service.product.model.Product;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Mar 7, 2009
 */
public interface ProductService extends BaseService {

    /**
     * 根据productInfo信息,得到产品概述信息
     * 
     * @param productInfoPOList
     * @return
     */
    public List<Product> getProductSummarysByIds(Integer[] ids);

    /**
     * 根据类目id,得到该类目下产品总数
     * 
     * @param categoryId
     * @return
     */
    public Integer countProductsByCategoryId(Integer categoryId);

    /**
     * 根据类目id,分页得到产品概述信息
     * 
     * @param categoryId
     * @param start
     * @param limit
     * @return
     */
    public List<Product> findProductSummarysByCategoryId(Integer categoryId, Integer start, Integer limit);

    /**
     * 查询产品detail信息
     * 
     * @param id
     * @return
     */
    public Product findProduct(Integer id);

    /**
     * 根据价格区间查询产品总数
     * 
     * @param min
     * @param max
     * @return
     */
    public Integer countProductsByPriceRange(Integer min, Integer max);

    /**
     * 根据价格区间查找产品
     * 
     * @param min
     * @param max
     * @param start
     * @param limit
     * @return
     */
    public List<Product> findProductsByPriceRange(Integer min, Integer max, Integer start, Integer limit);

}

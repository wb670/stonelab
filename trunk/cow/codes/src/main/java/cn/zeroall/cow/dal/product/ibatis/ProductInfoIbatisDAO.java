package cn.zeroall.cow.dal.product.ibatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import cn.zeroall.cow.dal.product.dao.ProductInfoDAO;
import cn.zeroall.cow.dal.product.po.ProductInfoPO;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Mar 4, 2009
 */
public class ProductInfoIbatisDAO extends SqlMapClientDaoSupport implements ProductInfoDAO {

    @Override
    public ProductInfoPO findById(Integer id) {
        return (ProductInfoPO) getSqlMapClientTemplate().queryForObject("ProductInfo.find-by-id", id);
    }

    @Override
    public List<ProductInfoPO> findByIds(Integer[] ids) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("ids", ids);
        return (List<ProductInfoPO>) getSqlMapClientTemplate().queryForList("ProductInfo.find-by-ids", param);
    }

    @Override
    public Integer countByCategoryIds(Integer[] categoryIds) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("categoryIds", categoryIds);
        return (Integer) getSqlMapClientTemplate().queryForObject("ProductInfo.count-by-category-ids", param);
    }

    @Override
    public List<ProductInfoPO> findByCategoryIds(Integer[] categoryIds, Integer start, Integer limit) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("categoryIds", categoryIds);
        param.put("start", start);
        param.put("limit", limit);
        return (List<ProductInfoPO>) getSqlMapClientTemplate().queryForList("ProductInfo.find-mini-by-category-ids",
                param);
    }

    @Override
    public Integer addProduct(ProductInfoPO pInfo) {
        return (Integer) getSqlMapClientTemplate().insert("ProductInfo.addProduct", pInfo);
    }

    @Override
    public Integer countByPriceRange(Integer min, Integer max) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("min", min);
        param.put("max", max);
        return (Integer) getSqlMapClientTemplate().queryForObject("ProductInfo.count-by-price-range", param);
    }

    @Override
    public List<ProductInfoPO> findByPriceRange(Integer min, Integer max, Integer start, Integer limit) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("min", min);
        param.put("max", max);
        param.put("start", start);
        param.put("limit", limit);
        return (List<ProductInfoPO>) getSqlMapClientTemplate().queryForList("ProductInfo.find-mini-by-price-range",
                param);
    }

}

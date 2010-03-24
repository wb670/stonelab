package cn.zeroall.cow.dal.product.ibatis;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import cn.zeroall.cow.dal.product.dao.ProductWashingDAO;
import cn.zeroall.cow.dal.product.po.ProductWashingPO;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Mar 4, 2009
 */
public class ProductWashingIbatisDAO extends SqlMapClientDaoSupport implements ProductWashingDAO {

    @Override
    public ProductWashingPO findById(Integer id) {
        return (ProductWashingPO) getSqlMapClientTemplate().queryForObject("ProductWashing.find-by-id", id);
    }

    @Override
    public List<ProductWashingPO> findByProductId(Integer productId) {
        return getSqlMapClientTemplate().queryForList("ProductWashing.find-by-product-id", productId);
    }

}

package cn.zeroall.cow.dal.product.ibatis;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import cn.zeroall.cow.dal.product.dao.ProductColorDAO;
import cn.zeroall.cow.dal.product.po.ProductColorPO;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Mar 4, 2009
 */
public class ProductColorIbatisDAO extends SqlMapClientDaoSupport implements ProductColorDAO {

    @Override
    public ProductColorPO findById(Integer id) {
        return (ProductColorPO) getSqlMapClientTemplate().queryForObject("ProductColor.find-by-id", id);
    }

    @Override
    public List<ProductColorPO> findByProductId(Integer productId) {
        return (List<ProductColorPO>) getSqlMapClientTemplate().queryForList("ProductColor.find-by-product-id",
                productId);
    }

}

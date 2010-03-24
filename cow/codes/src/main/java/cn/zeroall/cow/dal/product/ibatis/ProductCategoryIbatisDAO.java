package cn.zeroall.cow.dal.product.ibatis;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import cn.zeroall.cow.dal.product.dao.ProductCategoryDAO;
import cn.zeroall.cow.dal.product.po.ProductCategoryPO;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Sep 8, 2009
 */
public class ProductCategoryIbatisDAO extends SqlMapClientDaoSupport implements ProductCategoryDAO {

    @Override
    public List<ProductCategoryPO> findByType(String arg0) {
        ProductCategoryPO root = new ProductCategoryPO();
        root.setId(0);
        root.setParentId(-1);
        root.setName("root");
        root.setDescription("root");
        List<ProductCategoryPO> all = getSqlMapClientTemplate().queryForList("Category.findAll");
        all.add(root);
        return all;
    }

    @Override
    public void add(ProductCategoryPO arg0) {
        throw new UnsupportedOperationException("add method is unsupported.");
    }

    @Override
    public void delete(ProductCategoryPO arg0) {
        throw new UnsupportedOperationException("delete method is unsupported.");

    }

    @Override
    public void update(ProductCategoryPO arg0) {
        throw new UnsupportedOperationException("update method is unsupported.");

    }

}

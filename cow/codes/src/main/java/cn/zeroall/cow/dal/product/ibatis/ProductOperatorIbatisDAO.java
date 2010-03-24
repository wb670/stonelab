package cn.zeroall.cow.dal.product.ibatis;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import cn.zeroall.cow.dal.product.dao.ProductOperatorDAO;
import cn.zeroall.cow.dal.product.po.ProductOperatorPO;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Mar 8, 2009
 */
public class ProductOperatorIbatisDAO extends SqlMapClientDaoSupport implements ProductOperatorDAO {

    @Override
    public List<ProductOperatorPO> findByType(String type) {
        return (List<ProductOperatorPO>) getSqlMapClientTemplate().queryForList("ProductOperator.find-by-type", type);
    }

}

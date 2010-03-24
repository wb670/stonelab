package cn.zeroall.cow.dal.product.dao;

import java.util.List;

import cn.zeroall.cow.dal.BaseDAO;
import cn.zeroall.cow.dal.product.po.ProductOperatorPO;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Mar 8, 2009
 */
public interface ProductOperatorDAO extends BaseDAO {

    public List<ProductOperatorPO> findByType(String type);

}

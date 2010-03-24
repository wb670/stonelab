package cn.zeroall.cow.dal.product;

import java.util.List;

import cn.zeroall.cow.BaseTestCase;
import cn.zeroall.cow.dal.product.dao.ProductOperatorDAO;
import cn.zeroall.cow.dal.product.po.ProductOperatorPO;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Mar 8, 2009
 */
public class ProductOperatorIbatisDAO extends BaseTestCase {

    private ProductOperatorDAO productOperatorDAO = (ProductOperatorDAO) getBean("productOperatorDAO");

    public void testFindByType() {
        List<ProductOperatorPO> list = productOperatorDAO.findByType("RECOMMEND");
        for (ProductOperatorPO productOperatorPO : list) {
            System.out.println(productOperatorPO.getProductId());
        }
    }

}

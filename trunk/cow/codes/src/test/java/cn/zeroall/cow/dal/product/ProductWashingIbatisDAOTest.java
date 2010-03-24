package cn.zeroall.cow.dal.product;

import cn.zeroall.cow.BaseTestCase;
import cn.zeroall.cow.dal.product.dao.ProductWashingDAO;
import cn.zeroall.cow.dal.product.po.ProductWashingPO;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Mar 4, 2009
 */
public class ProductWashingIbatisDAOTest extends BaseTestCase {

    private static final Integer ID = 1;

    private ProductWashingDAO productWashingDAO = (ProductWashingDAO) getBean("productWashingDAO");

    public void testFindById() {
        ProductWashingPO productWashingPO = productWashingDAO.findById(ID);
        if (productWashingPO != null) {
            System.out.println(productWashingPO.getProductId());
        }
    }

}

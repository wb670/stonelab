package cn.zeroall.cow.dal.product;

import java.util.List;

import cn.zeroall.cow.BaseTestCase;
import cn.zeroall.cow.dal.product.dao.ProductColorDAO;
import cn.zeroall.cow.dal.product.po.ProductColorPO;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Mar 4, 2009
 */
public class ProductColorIbatisDAOTest extends BaseTestCase {

    private static final Integer ID = 1;

    private ProductColorDAO productColorDAO = (ProductColorDAO) getBean("productColorDAO");

    public void testFindById() {
        ProductColorPO productInfoPO = productColorDAO.findById(ID);
        if (productInfoPO != null) {
            System.out.println(productInfoPO.getColorName());
        }
    }

    public void testFindByProductId() {
        List<ProductColorPO> list = productColorDAO.findByProductId(1);
        for (ProductColorPO productColorPO : list) {
            System.out.println(productColorPO.getColorName());
        }
    }

}

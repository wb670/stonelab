package cn.zeroall.cow.service.product;

import java.util.List;

import cn.zeroall.cow.BaseTestCase;
import cn.zeroall.cow.service.product.enums.ProductOperatorType;
import cn.zeroall.cow.service.product.model.Product;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Mar 8, 2009
 */
public class ProductOperatorServiceTest extends BaseTestCase {

    private ProductOperatorService productOperatorService = (ProductOperatorService) getBean("productOperatorService");

    public void testFindProductsByOperatorType() {
        List<Product> list = productOperatorService
                .findProductsByOperatorType(ProductOperatorType.NEW);
        for (Product product : list) {
            System.out.println(product.getName() + ":" + product.getCoverImgName());
        }
    }

}

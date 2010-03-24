package cn.zeroall.cow.service.product;

import java.util.List;

import cn.zeroall.cow.BaseTestCase;
import cn.zeroall.cow.service.product.model.Product;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Mar 7, 2009
 */
public class ProductServiceTest extends BaseTestCase {

    private ProductService productService = (ProductService) getBean("productService");

    public void testCountProductsByCategoryId() {
        Integer total = productService.countProductsByCategoryId(1);
        System.out.println(total);
    }

    public void testFindProductsByCategoryId() {
        List<Product> products = productService.findProductSummarysByCategoryId(1, 0, 10);
        for (Product product : products) {
            System.out.println(product.getName() + ":" + product.getCoverImgName());
        }
    }

    public void testFindProductDetailById() {
        Product product = productService.findProduct(1);
        System.out.println(product.getId());
    }

}

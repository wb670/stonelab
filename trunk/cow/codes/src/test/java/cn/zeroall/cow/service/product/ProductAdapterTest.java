package cn.zeroall.cow.service.product;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.zeroall.cow.BaseTestCase;
import cn.zeroall.cow.dal.product.po.ProductInfoPO;
import cn.zeroall.cow.service.common.model.KeyValueAttribute;
import cn.zeroall.cow.service.product.adapter.ProductAdapter;
import cn.zeroall.cow.service.product.enums.ProductSize;
import cn.zeroall.cow.service.product.model.Product;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Mar 8, 2009
 */
public class ProductAdapterTest extends BaseTestCase {

    public void testProductInfoPO2Product() {
        System.out.println("============================================");
        Product product = new ProductAdapter(initProductInfoPO()).getProduct();
        System.out.println(product.getId());
        System.out.println(product.getTypeId());
        System.out.println(product.getName());
        System.out.println(product.getDescription());
        System.out.println(product.getMarketPrice());
        System.out.println(product.getMyPrice());
        System.out.println(product.getView());
        System.out.println(product.getGmtCreated());
        System.out.println(product.getGmtModified());
        List<KeyValueAttribute> attributes = product.getAttributes();
        List<ProductSize> sizes = product.getSizes();
        for (KeyValueAttribute attr : attributes) {
            System.out.print(attr.getKey() + ":" + attr.getValue() + ";");
        }
        System.out.println();
        for (ProductSize size : sizes) {
            System.out.print(size.getMessage() + ";");
        }
        System.out.println();
        System.out.println("============================================");
    }

    public void testProduct2ProductInfoPO() {
        System.out.println("============================================");
        ProductInfoPO info = new ProductAdapter(initProduct()).getProductInfoPO();
        System.out.println(info.getId());
        System.out.println(info.getTypeId());
        System.out.println(info.getName());
        System.out.println(info.getDescription());
        System.out.println(info.getAttributes());
        System.out.println(info.getMarketPrice());
        System.out.println(info.getMyPrice());
        System.out.println(info.getSizes());
        System.out.println(info.getView());
        System.out.println(info.getGmtCreated());
        System.out.println(info.getGmtModified());
        System.out.println("============================================");
    }

    private ProductInfoPO initProductInfoPO() {
        ProductInfoPO productInfoPO = new ProductInfoPO();
        productInfoPO.setId(1);
        productInfoPO.setTypeId(1);
        productInfoPO.setName("product");
        productInfoPO.setDescription("description");
        productInfoPO.setAttributes("a=b,c=d");
        productInfoPO.setMarketPrice(12.4);
        productInfoPO.setMyPrice(1.2);
        productInfoPO.setSizes("M,L");
        productInfoPO.setView(100);
        productInfoPO.setGmtCreated(new Date());
        productInfoPO.setGmtModified(new Date());
        return productInfoPO;
    }

    private Product initProduct() {
        Product product = new Product();
        product.setId(1);
        product.setTypeId(1);
        product.setName("product");
        product.setDescription("description");
        List<KeyValueAttribute> attributes = new ArrayList<KeyValueAttribute>();
        attributes.add(new KeyValueAttribute("a", "b"));
        attributes.add(new KeyValueAttribute("c", "d"));
        product.setAttributes(attributes);
        product.setMarketPrice(12.4);
        product.setMyPrice(1.2);
        List<ProductSize> sizes = new ArrayList<ProductSize>();
        sizes.add(ProductSize.L);
        sizes.add(ProductSize.M);
        product.setSizes(sizes);
        product.setView(100);
        product.setGmtCreated(new Date());
        product.setGmtModified(new Date());
        return product;
    }
}

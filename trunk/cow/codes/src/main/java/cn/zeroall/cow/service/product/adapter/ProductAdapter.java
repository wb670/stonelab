package cn.zeroall.cow.service.product.adapter;

import java.util.List;

import net.sf.cglib.beans.BeanCopier;
import cn.zeroall.cow.common.parser.EnumArrayParser;
import cn.zeroall.cow.common.parser.KeyValueAttributesParser;
import cn.zeroall.cow.common.parser.Parser;
import cn.zeroall.cow.dal.product.po.ProductInfoPO;
import cn.zeroall.cow.service.common.model.KeyValueAttribute;
import cn.zeroall.cow.service.product.enums.ProductSize;
import cn.zeroall.cow.service.product.model.Product;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Mar 7, 2009
 */
public class ProductAdapter {

    private static final BeanCopier productInfoPO2Product = BeanCopier
            .create(ProductInfoPO.class, Product.class, false);
    private static final BeanCopier product2ProductInfoPO = BeanCopier
            .create(Product.class, ProductInfoPO.class, false);

    private static final Parser<List<KeyValueAttribute>> attributesParser = new KeyValueAttributesParser("=", ",");
    private static final EnumArrayParser sizesParser = new EnumArrayParser(ProductSize.class, ",");

    private ProductInfoPO productInfoPO;
    private Product product;

    public ProductAdapter(ProductInfoPO productInfoPO) {
        this.productInfoPO = productInfoPO;
        initProductInfoPO2Product();
    }

    public ProductAdapter(Product product) {
        this.product = product;
        initProduct2ProductInfoPO();
    }

    public ProductInfoPO getProductInfoPO() {
        return productInfoPO;
    }

    public void setProductInfoPO(ProductInfoPO productInfoPO) {
        this.productInfoPO = productInfoPO;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    private void initProductInfoPO2Product() {
        if (productInfoPO == null) {
            return;
        }
        product = new Product();
        productInfoPO2Product.copy(productInfoPO, product, null);
        product.setAttributes(attributesParser.str2Obj(productInfoPO.getAttributes()));
//        product.setSizes((List<ProductSize>) sizesParser.str2Obj(productInfoPO.getSizes()));
        product.setSizes(sizesParser.str2Obj(productInfoPO.getSizes()));
        product.setSizeDescriptions(sizesParser.str2Obj(productInfoPO.getSizeDescriptions()));
        
    }

    private void initProduct2ProductInfoPO() {
        if (product == null) {
            return;
        }
        productInfoPO = new ProductInfoPO();
        product2ProductInfoPO.copy(product, productInfoPO, null);
        productInfoPO.setAttributes(attributesParser.obj2Str(product.getAttributes()));
        productInfoPO.setSizes(sizesParser.obj2Str(product.getSizes()));
       
    }
}

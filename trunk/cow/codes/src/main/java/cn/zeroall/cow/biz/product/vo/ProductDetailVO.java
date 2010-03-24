package cn.zeroall.cow.biz.product.vo;

import java.util.List;

import cn.zeroall.cow.biz.BaseVO;
import cn.zeroall.cow.dal.product.po.ProductColorPO;
import cn.zeroall.cow.dal.product.po.WashingPO;
import cn.zeroall.cow.service.product.model.Product;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Apr 28, 2009
 */
public class ProductDetailVO implements BaseVO {

    private static final long serialVersionUID = -421751502648081382L;

    private Product product;
    private List<ProductColorPO> productColors;
    private List<WashingPO> washings;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public List<ProductColorPO> getProductColors() {
        return productColors;
    }

    public void setProductColors(List<ProductColorPO> productColors) {
        this.productColors = productColors;
    }

    public List<WashingPO> getWashings() {
        return washings;
    }

    public void setWashings(List<WashingPO> washings) {
        this.washings = washings;
    }

}

package cn.zeroall.cow.biz.order.vo;

import cn.zeroall.cow.biz.BaseVO;
import cn.zeroall.cow.service.order.model.OrderProduct;
import cn.zeroall.cow.service.product.model.Product;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Jun 2, 2009
 */
public class CartProductVO implements BaseVO {

    private static final long serialVersionUID = 7685637231090816883L;

    private OrderProduct orderProduct;
    private Product product;
    private Double total;

    public CartProductVO(OrderProduct orderProduct, Product product) {
        this.orderProduct = orderProduct;
        this.product = product;
    }

    public Double getTotal() {
        if (total == null) {
            total = orderProduct.getAmount() * product.getMyPrice();
        }
        return total;
    }

    public OrderProduct getOrderProduct() {
        return orderProduct;
    }

    public void setOrderProduct(OrderProduct orderProduct) {
        this.orderProduct = orderProduct;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

}

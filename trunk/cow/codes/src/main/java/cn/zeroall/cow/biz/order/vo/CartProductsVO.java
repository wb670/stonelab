package cn.zeroall.cow.biz.order.vo;

import java.util.List;

import cn.zeroall.cow.biz.BaseVO;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Jun 10, 2009
 */
public class CartProductsVO implements BaseVO {

    private static final long serialVersionUID = 6211584698226948032L;

    private List<CartProductVO> cartProducts;
    private Double total;

    public Double getTotal() {
        if (total == null) {
            total = 0.0;
            for (CartProductVO cartProductVO : cartProducts) {
                total += cartProductVO.getTotal();
            }
        }
        return total;
    }

    public List<CartProductVO> getCartProducts() {
        return cartProducts;
    }

    public void setCartProducts(List<CartProductVO> cartProducts) {
        this.cartProducts = cartProducts;
    }

}

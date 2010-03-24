package cn.zeroall.cow.web.order.action;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import cn.zeroall.cow.biz.order.ao.CartAO;
import cn.zeroall.cow.biz.order.vo.CartProductsVO;
import cn.zeroall.cow.service.order.model.OrderProduct;
import cn.zeroall.cow.web.BaseAction;
import cn.zeroall.cow.web.common.constants.SessionConstant;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Aug 20, 2009
 */
public class CartAction extends BaseAction {

    private static final long serialVersionUID = -8228372690861769290L;

    private CartAO cartAO;

    protected List<OrderProduct> getCartSession() {
        List<OrderProduct> orderProducts = (List<OrderProduct>) getSession().get(SessionConstant.CART_SESSION);
        if (orderProducts == null) {
            orderProducts = new ArrayList<OrderProduct>(1);
        }
        return orderProducts;
    }

    protected void saveCartSession(List<OrderProduct> orderProducts) {
        getSession().put(SessionConstant.CART_SESSION, orderProducts);
    }

    /********************************************
     ** list页面
     ********************************************/
    private CartProductsVO cartProductsVO;

    public String list() {
        cartProductsVO = cartAO.getCartProducts(getCartSession());
        return SUCCESS;
    }

    /********************************************
     ** 添加动作
     ********************************************/
    private String productId;
    private String productName;
    private String productColor;
    private String productSize;
    private String productAmount;
    private String productPrice;

    public String add() {
        List<OrderProduct> orderProducts = getCartSession();
        addCurrent(orderProducts, getCurrent());
        saveCartSession(orderProducts);
        return SUCCESS;
    }

    private OrderProduct getCurrent() {
        OrderProduct orderProduct = new OrderProduct();
        orderProduct.setProductId(Integer.valueOf(productId));
        orderProduct.setName(productName);
        orderProduct.setColor(productColor);
        orderProduct.setSize(cartAO.getNameByDescription(productSize));
        orderProduct.setDescription(productSize);
        orderProduct.setAmount(Integer.valueOf(productAmount));
        orderProduct.setPrice(Double.valueOf(productPrice));
        return orderProduct;
    }

    private void addCurrent(List<OrderProduct> orderProducts, OrderProduct current) {
        for (OrderProduct orderProduct : orderProducts) {
            if (isMatched(orderProduct)) {
                orderProduct.setAmount(orderProduct.getAmount() + Integer.valueOf(productAmount));
                return;
            }
        }
        orderProducts.add(current);
    }

    private boolean isMatched(OrderProduct orderProduct) {
        boolean productIdMatched = orderProduct.getProductId().intValue() == Integer.valueOf(productId);
        boolean productColorMatched = StringUtils.equals(orderProduct.getColor(), productColor);
        boolean productSizeMatched = StringUtils.equals(orderProduct.getSize(), productSize);
        return productIdMatched && productColorMatched && productSizeMatched;
    }

    /********************************************
     ** 添加动作
     ********************************************/
    public String remove() {
        List<OrderProduct> orderProducts = getCartSession();
        remove(orderProducts);
        saveCartSession(orderProducts);
        return SUCCESS;
    }

    private void remove(List<OrderProduct> orderProducts) {
        if (!StringUtils.isNumeric(productId)) {
            return;
        }
        for (Iterator iterator = orderProducts.iterator(); iterator.hasNext();) {
            OrderProduct orderProduct = (OrderProduct) iterator.next();
            if (isMatched(orderProduct)) {
                iterator.remove();
                return;
            }
        }
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductColor() {
        return productColor;
    }

    public void setProductColor(String productColor) {
        this.productColor = productColor;
    }

    public String getProductSize() {
        return productSize;
    }

    public void setProductSize(String productSize) {
        this.productSize = productSize;
    }

    public String getProductAmount() {
        return productAmount;
    }

    public void setProductAmount(String productAmount) {
        this.productAmount = productAmount;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public CartProductsVO getCartProductsVO() {
        return cartProductsVO;
    }

    public void setCartProductsVO(CartProductsVO cartProductsVO) {
        this.cartProductsVO = cartProductsVO;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public void setCartAO(CartAO cartAO) {
        this.cartAO = cartAO;
    }

}

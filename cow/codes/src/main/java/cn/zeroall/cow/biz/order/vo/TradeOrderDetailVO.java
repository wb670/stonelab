package cn.zeroall.cow.biz.order.vo;

import java.util.List;

import cn.zeroall.cow.biz.BaseVO;
import cn.zeroall.cow.service.order.model.Delivery;
import cn.zeroall.cow.service.order.model.OrderProduct;
import cn.zeroall.cow.service.order.model.Payment;
import cn.zeroall.cow.service.order.model.TradeOrder;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Aug 22, 2009
 */
public class TradeOrderDetailVO implements BaseVO {

    private static final long serialVersionUID = 9049462871549901722L;

    private TradeOrder tradeOrder;
    private Payment payment;
    private Delivery delivery;
    private List<OrderProduct> orderProducts;

    public TradeOrder getTradeOrder() {
        return tradeOrder;
    }

    public void setTradeOrder(TradeOrder tradeOrder) {
        this.tradeOrder = tradeOrder;
    }

    public List<OrderProduct> getOrderProducts() {
        return orderProducts;
    }

    public void setOrderProducts(List<OrderProduct> orderProducts) {
        this.orderProducts = orderProducts;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public Delivery getDelivery() {
        return delivery;
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
    }

}

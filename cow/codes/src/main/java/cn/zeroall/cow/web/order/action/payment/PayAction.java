package cn.zeroall.cow.web.order.action.payment;

import cn.zeroall.cow.biz.BizException;
import cn.zeroall.cow.biz.order.ao.OrderPayAO;
import cn.zeroall.cow.service.order.model.TradeOrder;
import cn.zeroall.cow.web.BaseAction;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Aug 23, 2009
 */
public class PayAction extends BaseAction {

    private static final long serialVersionUID = 60203100711403971L;

    public static final String PAY_OK = "OK";

    private String orderId;

    private TradeOrder tradeOrder;

    private OrderPayAO orderPayAO;

    public String doPay() {
        try {
            tradeOrder = orderPayAO.getTradeOrder(Integer.valueOf(orderId));
        } catch (BizException e) {
            addActionError(e.getBizCode().getMessage());
            return ERROR;
        }
        return String.valueOf(tradeOrder.getPayId());
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public TradeOrder getTradeOrder() {
        return tradeOrder;
    }

    public void setTradeOrder(TradeOrder tradeOrder) {
        this.tradeOrder = tradeOrder;
    }

    public void setOrderPayAO(OrderPayAO orderPayAO) {
        this.orderPayAO = orderPayAO;
    }

}

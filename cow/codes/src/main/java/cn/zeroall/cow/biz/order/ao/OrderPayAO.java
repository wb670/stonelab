package cn.zeroall.cow.biz.order.ao;

import cn.zeroall.cow.biz.BaseAO;
import cn.zeroall.cow.biz.BizException;
import cn.zeroall.cow.biz.order.OrderCode;
import cn.zeroall.cow.service.order.TradeOrderService;
import cn.zeroall.cow.service.order.enums.TradeOrderStatus;
import cn.zeroall.cow.service.order.model.TradeOrder;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Aug 21, 2009
 */
public class OrderPayAO implements BaseAO {

    private TradeOrderService tradeOrderService;

    public TradeOrder getTradeOrder(Integer id) throws BizException {
        TradeOrder tradeOrder = tradeOrderService.get(id);
        if (tradeOrder == null) {
            throw new BizException(OrderCode.TRADE_ORDER_NOT_EXIST);
        }
        if (TradeOrderStatus.INITIALIZED != tradeOrder.getStatus() && TradeOrderStatus.NO_CHECK_ACCOUNT != tradeOrder.getStatus()) {
            throw new BizException(OrderCode.TRADE_ORDER_NEED_NOT_PAY);
        }
        return tradeOrder;
    }

    public TradeOrder getTradeOrder(Integer id, Integer memberId) throws BizException {
        TradeOrder tradeOrder = tradeOrderService.get(id);
        if (tradeOrder == null) {
            throw new BizException(OrderCode.TRADE_ORDER_NOT_EXIST);
        }
        if (TradeOrderStatus.INITIALIZED != tradeOrder.getStatus() && TradeOrderStatus.NO_CHECK_ACCOUNT != tradeOrder.getStatus()) {
            throw new BizException(OrderCode.TRADE_ORDER_NEED_NOT_PAY);
        }
        if (memberId == null || tradeOrder.getMemberId().intValue() != memberId.intValue()) {
            throw new BizException(OrderCode.MEMBER_ID_NOT_MATCHED);
        }
        return tradeOrder;
    }

    public void pay(Integer orderId, boolean isSuccess) throws BizException {
        if (!isSuccess) {
            throw new BizException(OrderCode.PAY_ERROR);
        }
        TradeOrder tradeOrder = tradeOrderService.get(orderId);
        if (tradeOrder == null) {
            throw new BizException(OrderCode.TRADE_ORDER_NOT_EXIST);
        }
        tradeOrder.setStatus(TradeOrderStatus.PAID);
        tradeOrderService.update(tradeOrder);
    }

    public void setTradeOrderService(TradeOrderService tradeOrderService) {
        this.tradeOrderService = tradeOrderService;
    }

}

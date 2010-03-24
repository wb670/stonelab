package cn.zeroall.cow.service.order.adapter;

import net.sf.cglib.beans.BeanCopier;
import cn.zeroall.cow.dal.order.po.TradeOrderPO;
import cn.zeroall.cow.service.order.enums.TradeOrderStatus;
import cn.zeroall.cow.service.order.model.TradeOrder;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> May 19, 2009
 */
public class TradeOrderAdapter {

    private static final BeanCopier model2PO = BeanCopier.create(TradeOrder.class, TradeOrderPO.class, false);
    private static final BeanCopier po2Model = BeanCopier.create(TradeOrderPO.class, TradeOrder.class, false);

    public TradeOrder adapter(TradeOrderPO tradeOrderPO) {
        if (tradeOrderPO == null) {
            return null;
        }
        TradeOrder tradeOrder = new TradeOrder();
        po2Model.copy(tradeOrderPO, tradeOrder, null);
        tradeOrder.setStatus(Enum.valueOf(TradeOrderStatus.class, tradeOrderPO.getStatus()));
        return tradeOrder;
    }

    public TradeOrderPO adapter(TradeOrder tradeOrder) {
        if (tradeOrder == null) {
            return null;
        }
        TradeOrderPO tradeOrderPO = new TradeOrderPO();
        model2PO.copy(tradeOrder, tradeOrderPO, null);
        tradeOrderPO.setStatus(tradeOrder.getStatus().toString());
        return tradeOrderPO;
    }

}

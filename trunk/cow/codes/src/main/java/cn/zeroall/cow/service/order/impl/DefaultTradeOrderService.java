package cn.zeroall.cow.service.order.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.zeroall.cow.dal.order.dao.TradeOrderDAO;
import cn.zeroall.cow.dal.order.po.TradeOrderPO;
import cn.zeroall.cow.service.order.TradeOrderService;
import cn.zeroall.cow.service.order.adapter.TradeOrderAdapter;
import cn.zeroall.cow.service.order.model.TradeOrder;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> May 19, 2009
 */
public class DefaultTradeOrderService implements TradeOrderService {

    private static final TradeOrderAdapter adapter = new TradeOrderAdapter();

    private TradeOrderDAO tradeOrderDAO;

    @Override
    public Integer add(TradeOrder tradeOrder) {
        Date now = new Date();
        tradeOrder.setGmtCreated(now);
        tradeOrder.setGmtModified(now);
        return tradeOrderDAO.add(new TradeOrderAdapter().adapter(tradeOrder));
    }

    @Override
    public TradeOrder get(Integer id) {
        return new TradeOrderAdapter().adapter(tradeOrderDAO.get(id));
    }

    @Override
    public void update(TradeOrder tradeOrder) {
        TradeOrderPO tradeOrderPO = adapter.adapter(tradeOrder);
        tradeOrderDAO.update(tradeOrderPO);

    }

    @Override
    public Integer getCountByMemberId(Integer memberId) {
        return tradeOrderDAO.countByMemberId(memberId);
    }

    @Override
    public List<TradeOrder> getByMemberId(Integer memberId, Integer start, Integer limit) {
        List<TradeOrderPO> list = tradeOrderDAO.findByMemberId(memberId, start, limit);
        List<TradeOrder> tradeOrders = new ArrayList<TradeOrder>(list.size());

        TradeOrderAdapter adapter = new TradeOrderAdapter();
        for (TradeOrderPO tradeOrderPO : list) {
            tradeOrders.add(adapter.adapter(tradeOrderPO));
        }
        return tradeOrders;
    }

    /**
     * @return 截至当前时间，您共有 N 张订单完成交易
     */
    public Integer countByMemberIdNowTime(Integer memberId) {

        return tradeOrderDAO.countByMemberIdNowTime(memberId);
    }

    /**
     * @return 截至当前时间，累计消费 N 元。
     */
    public Float sumTotalPriceByMemberId(Integer memberId) {
        return tradeOrderDAO.sumTotalPriceByMemberId(memberId);
    }

    public void setTradeOrderDAO(TradeOrderDAO tradeOrderDAO) {
        this.tradeOrderDAO = tradeOrderDAO;
    }

}

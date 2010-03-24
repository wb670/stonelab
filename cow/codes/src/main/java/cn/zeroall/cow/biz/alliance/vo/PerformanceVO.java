package cn.zeroall.cow.biz.alliance.vo;

import cn.zeroall.cow.dal.alliance.po.AlliedMemberPO;
import cn.zeroall.cow.dal.order.po.TradeOrderPO;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Oct 10, 2009
 */
public class PerformanceVO {

    private TradeOrderPO tradeOrderPO;
    private AlliedMemberPO alliedMemberPO;

    public TradeOrderPO getTradeOrderPO() {
        return tradeOrderPO;
    }

    public void setTradeOrderPO(TradeOrderPO tradeOrderPO) {
        this.tradeOrderPO = tradeOrderPO;
    }

    public AlliedMemberPO getAlliedMemberPO() {
        return alliedMemberPO;
    }

    public void setAlliedMemberPO(AlliedMemberPO alliedMemberPO) {
        this.alliedMemberPO = alliedMemberPO;
    }

}

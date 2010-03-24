package cn.zeroall.cow.dal.order.dao;

import java.util.List;

import cn.zeroall.cow.dal.order.po.TradeOrderPO;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> May 19, 2009
 */
public interface TradeOrderDAO {

    public Integer add(TradeOrderPO orderPO);

    public TradeOrderPO get(Integer id);

    public void update(TradeOrderPO orderPO);

    public Integer countByMemberId(Integer memberId);
    
    public Integer countByMemberIdNowTime(Integer memberId);
    
    public Float sumTotalPriceByMemberId(Integer memberId);

    public List<TradeOrderPO> findByMemberId(Integer memberId, Integer start, Integer limit);

}

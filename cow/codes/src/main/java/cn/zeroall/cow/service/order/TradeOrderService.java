package cn.zeroall.cow.service.order;

import java.util.List;

import cn.zeroall.cow.service.BaseService;
import cn.zeroall.cow.service.order.model.TradeOrder;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> May 19, 2009
 */
public interface TradeOrderService extends BaseService {

    public Integer add(TradeOrder tradeOrder);

    public TradeOrder get(Integer id);

    public void update(TradeOrder tradeOrder);

    public Integer getCountByMemberId(Integer memberId);

    public List<TradeOrder> getByMemberId(Integer memberId, Integer start, Integer limit);
    
    public Integer countByMemberIdNowTime(Integer memberId);
    
    public Float sumTotalPriceByMemberId(Integer memberId);

}

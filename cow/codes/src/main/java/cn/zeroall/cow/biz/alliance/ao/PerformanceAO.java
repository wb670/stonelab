package cn.zeroall.cow.biz.alliance.ao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.zeroall.cow.biz.BaseAO;
import cn.zeroall.cow.biz.alliance.vo.PerformanceVO;
import cn.zeroall.cow.dal.alliance.dao.AlliedOrderDAO;
import cn.zeroall.cow.dal.alliance.po.AlliedMemberPO;
import cn.zeroall.cow.dal.order.po.TradeOrderPO;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Oct 10, 2009
 */
public class PerformanceAO implements BaseAO {

    private AlliedOrderDAO alliedOrderDAO;

    public List<PerformanceVO> queryByOrderDate(Integer alliedMemberId, Date start, Date end) {
        List<Object[]> list = alliedOrderDAO.findByOrderDate(alliedMemberId, start, end);
        List<PerformanceVO> performances = new ArrayList<PerformanceVO>(list.size());
        for (Object[] data : list) {
            PerformanceVO vo = new PerformanceVO();
            vo.setAlliedMemberPO((AlliedMemberPO) data[0]);
            vo.setTradeOrderPO((TradeOrderPO) data[1]);
            performances.add(vo);
        }
        return performances;
    }
    
    public List<PerformanceVO> queryByOrderId(Integer alliedMemberId,Integer orderId) {
        List<Object[]> list = alliedOrderDAO.findByOrderId(alliedMemberId, orderId);
        List<PerformanceVO> performances = new ArrayList<PerformanceVO>(list.size());
        for (Object[] data : list) {
            PerformanceVO vo = new PerformanceVO();
            vo.setAlliedMemberPO((AlliedMemberPO) data[0]);
            vo.setTradeOrderPO((TradeOrderPO) data[1]);
            performances.add(vo);
        }
        return performances;
    }
    
    
    public void setAlliedOrderDAO(AlliedOrderDAO alliedOrderDAO) {
        this.alliedOrderDAO = alliedOrderDAO;
    }

}

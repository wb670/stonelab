package cn.zeroall.cow.dal.alliance.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.util.Assert;

import cn.zeroall.cow.dal.BaseDAO;
import cn.zeroall.cow.dal.alliance.po.AlliedOrderPO;
import cn.zeroall.cow.service.order.enums.TradeOrderStatus;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Oct 9, 2009
 */
public class AlliedOrderDAO extends HibernateDaoSupport implements BaseDAO {

    public Integer create(AlliedOrderPO alliedOrderPO) {
        Assert.notNull(alliedOrderPO, "alliedOrderPO is null.");
        Date now = new Date();
        alliedOrderPO.setGmtCreated(now);
        alliedOrderPO.setGmtModified(now);
        return (Integer) getHibernateTemplate().save(alliedOrderPO);
    }

    public List<Object[]> findByOrderDate(Integer alliedMemberId, Date startDate, Date endDate) {
        String hql = "from AlliedOrderPO alliedOrder,TradeOrderPO tradeOrder where alliedOrder.alliedMemberId = ? and alliedOrder.tradeOrderId = tradeOrder.id and tradeOrder.gmtCreated > ? and tradeOrder.gmtCreated < ?";
        return getHibernateTemplate().find(hql, new Object[] { alliedMemberId, startDate, endDate });
    }
    
    
    public List<Object[]> findByOrderId(Integer alliedMemberId,Integer orderId) {
        String hql = "from AlliedOrderPO alliedOrder,TradeOrderPO tradeOrder where alliedOrder.alliedMemberId = ? and alliedOrder.tradeOrderId = tradeOrder.id and tradeOrder.id=?";
        return getHibernateTemplate().find(hql, new Object[] { alliedMemberId,orderId});
    }
    

    public List getList(String hql, Object[] obj) {

        return getHibernateTemplate().find(hql, obj);

    }

    /**
     * 收益报表
     */
    public List<List> findByOrderYearAndMonth(Integer alliedMemberId, Date date) {
        
        List list=new ArrayList();
        Object obj[]=null;
        //不可提成单数************ 不可提成即是下了订单但未付款或者出现退款现象的订单，由于目前退款还没做，
        String no_deduct_count = "SELECT COUNT(*) FROM AlliedOrderPO alliedOrder,TradeOrderPO tradeOrder" +
        " WHERE alliedOrder.alliedMemberId = ?  AND alliedOrder.tradeOrderId = tradeOrder.id AND year(tradeOrder.gmtCreated) = ?" +
        " AND month(tradeOrder.gmtCreated)= ? AND tradeOrder.status!=?";
        obj=new Object[] {alliedMemberId, date.getYear(), date.getMonth(),TradeOrderStatus.values()[1].getMessage()};
        list.add(getList(no_deduct_count,obj));
        
        //可提成单数    状态条件是没有取消定单
        String deduct_count = "SELECT COUNT(*) FROM AlliedOrderPO alliedOrder,TradeOrderPO tradeOrder " +
        "WHERE alliedOrder.alliedMemberId = ?  AND alliedOrder.tradeOrderId = tradeOrder.id AND year(tradeOrder.gmtCreated) = ?" +
        " AND month(tradeOrder.gmtCreated)= ? AND tradeOrder.status!=?";
        obj=new Object[] {alliedMemberId, date.getYear(), date.getMonth(),TradeOrderStatus.values()[4].getMessage()};
        list.add(getList(deduct_count,obj));
        
        
        //可提成订单金额： 单笔订单生成30天内的出库金额－该订单生成30天内的入库金额－礼品卡金额。由于入库部分涉及到退货，还有礼品这部分，现在先把入库金额、礼品卡金额去了，状态条件是没有取消定单
        String deduct_count_price = "SELECT SUM(tradeOrder.totalPrice) FROM AlliedOrderPO alliedOrder,TradeOrderPO tradeOrder " +
        "WHERE alliedOrder.alliedMemberId = ?  AND alliedOrder.tradeOrderId = tradeOrder.id AND year(tradeOrder.gmtCreated) = ?" +
        " AND month(tradeOrder.gmtCreated)= ? AND tradeOrder.status!=?";
        obj=new Object[] {alliedMemberId, date.getYear(), date.getMonth(),TradeOrderStatus.values()[4].getMessage()};
        list.add(getList(deduct_count_price,obj));
        
        
        // 出库金额合计
        String totalPriceOut = "SELECT SUM(tradeOrder.totalPrice) FROM AlliedOrderPO alliedOrder,TradeOrderPO tradeOrder " +
        "WHERE alliedOrder.alliedMemberId = ?  AND alliedOrder.tradeOrderId = tradeOrder.id AND year(tradeOrder.gmtCreated) = ?" +
        " AND month(tradeOrder.gmtCreated)= ? AND tradeOrder.status!=?";
        obj=new Object[] {alliedMemberId, date.getYear(), date.getMonth(),TradeOrderStatus.values()[4].getMessage()};
        list.add(getList(totalPriceOut,obj));
        
       
        return list;
        
        
    }
}

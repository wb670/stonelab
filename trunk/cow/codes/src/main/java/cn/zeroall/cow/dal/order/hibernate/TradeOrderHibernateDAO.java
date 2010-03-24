package cn.zeroall.cow.dal.order.hibernate;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.zeroall.cow.dal.order.dao.TradeOrderDAO;
import cn.zeroall.cow.dal.order.po.TradeOrderPO;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> May 19, 2009
 */
public class TradeOrderHibernateDAO extends HibernateDaoSupport implements TradeOrderDAO {

    @Override
    public Integer add(TradeOrderPO orderPO) {
        return (Integer) getHibernateTemplate().save(orderPO);
    }

    @Override
    public TradeOrderPO get(Integer id) {
        return (TradeOrderPO) getHibernateTemplate().get(TradeOrderPO.class, id);
    }

    @Override
    public void update(TradeOrderPO orderPO) {
        getHibernateTemplate().update(orderPO);
    }

    @Override
    public Integer countByMemberId(Integer memberId) {
        DetachedCriteria criteria = DetachedCriteria.forClass(TradeOrderPO.class).add(
                Expression.eq("memberId", memberId));
        criteria.setProjection(Projections.rowCount());
        return (Integer) getHibernateTemplate().findByCriteria(criteria).get(0);
    }
    
    
    /**
     * @return 截至当前时间，您共有 N 张订单完成交易
     */
    public Integer countByMemberIdNowTime(Integer memberId) {
        DetachedCriteria criteria = DetachedCriteria.forClass(TradeOrderPO.class).add(
                Expression.eq("memberId", memberId)).add(Expression.eq("gmtCreated", new Date()));
        criteria.setProjection(Projections.rowCount());
        return (Integer) getHibernateTemplate().findByCriteria(criteria).get(0);
    }
    
    
    /**
     * @return 截至当前时间，累计消费 N 元。
     */
    public Float sumTotalPriceByMemberId(Integer memberId) {
        DetachedCriteria criteria = DetachedCriteria.forClass(TradeOrderPO.class).add(
                Expression.eq("memberId", memberId)).add(Expression.eq("gmtCreated", new Date()));
        criteria.setProjection(Projections.sum("totalPrice"));
        return (Float) getHibernateTemplate().findByCriteria(criteria).get(0);
    }
    
    

    @Override
    public List<TradeOrderPO> findByMemberId(Integer memberId, Integer start, Integer limit) {
        DetachedCriteria criteria = DetachedCriteria.forClass(TradeOrderPO.class).add(
                Expression.eq("memberId", memberId)).addOrder(Order.desc("id"));
        return getHibernateTemplate().findByCriteria(criteria, start, limit);
    }

}

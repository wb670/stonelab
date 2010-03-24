package cn.zeroall.cow.dal.order.hibernate;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.zeroall.cow.dal.order.dao.OrderProductDAO;
import cn.zeroall.cow.dal.order.po.OrderProductPO;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> May 19, 2009
 */
public class OrderProductHibernateDAO extends HibernateDaoSupport implements OrderProductDAO {

    @Override
    public Integer add(OrderProductPO orderProductPO) {
        return (Integer) getHibernateTemplate().save(orderProductPO);
    }

    @Override
    public List<OrderProductPO> findByOrderId(Integer orderId) {
        DetachedCriteria criteria = DetachedCriteria.forClass(OrderProductPO.class).add(
                Expression.eq("orderId", orderId));
        return getHibernateTemplate().findByCriteria(criteria);
    }

}

package cn.zeroall.cow.dal.alliance.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.zeroall.cow.dal.BaseDAO;
import cn.zeroall.cow.dal.alliance.po.AlliedLinkPO;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Sep 29, 2009
 */
public class AlliedLinkDAO extends HibernateDaoSupport implements BaseDAO {

    public AlliedLinkPO findById(Integer id) {
        return (AlliedLinkPO) getHibernateTemplate().get(AlliedLinkPO.class, id);
    }

    public List<AlliedLinkPO> findByType(String type) {
        DetachedCriteria criteria = DetachedCriteria.forClass(AlliedLinkPO.class).add(Expression.eq("type", type))
                .addOrder(Order.asc("id"));
        return getHibernateTemplate().findByCriteria(criteria);
    }

}

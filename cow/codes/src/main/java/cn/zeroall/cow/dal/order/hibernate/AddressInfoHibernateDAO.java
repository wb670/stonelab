package cn.zeroall.cow.dal.order.hibernate;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.zeroall.cow.dal.order.dao.AddressInfoDAO;
import cn.zeroall.cow.dal.order.po.AddressInfoPO;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Sep 13, 2009
 */
public class AddressInfoHibernateDAO extends HibernateDaoSupport implements AddressInfoDAO {

    @Override
    public void add(AddressInfoPO addressInfoPO) {
        getHibernateTemplate().save(addressInfoPO);
    }

    @Override
    public void delete(AddressInfoPO addressInfoPO) {
        getHibernateTemplate().delete(addressInfoPO);
    }

    @Override
    public void update(AddressInfoPO addressInfoPO) {
        getHibernateTemplate().update(addressInfoPO);
    }

    @Override
    public List<AddressInfoPO> findByMemberId(Integer memberId) {
        DetachedCriteria criteria = DetachedCriteria.forClass(AddressInfoPO.class).add(
                Expression.eq("memberId", memberId)).addOrder(Order.desc("gmtModified"));
        return getHibernateTemplate().findByCriteria(criteria);
    }

}

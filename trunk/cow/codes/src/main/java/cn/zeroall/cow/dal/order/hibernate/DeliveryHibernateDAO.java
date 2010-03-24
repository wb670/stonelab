package cn.zeroall.cow.dal.order.hibernate;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.zeroall.cow.dal.order.dao.DeliveryDAO;
import cn.zeroall.cow.dal.order.po.DeliveryPO;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> May 19, 2009
 */
public class DeliveryHibernateDAO extends HibernateDaoSupport implements DeliveryDAO {

    @Override
    public Integer add(DeliveryPO deleveryPO) {
        return (Integer) getHibernateTemplate().save(deleveryPO);
    }

    @Override
    public List<DeliveryPO> findAll() {
        return getHibernateTemplate().loadAll(DeliveryPO.class);
    }

}

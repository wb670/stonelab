package cn.zeroall.cow.dal.order.hibernate;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.zeroall.cow.dal.order.dao.PaymentDAO;
import cn.zeroall.cow.dal.order.po.PaymentPO;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> May 19, 2009
 */
public class PaymentHibernateDAO extends HibernateDaoSupport implements PaymentDAO {

    @Override
    public Integer add(PaymentPO paymentPO) {
        return (Integer) getHibernateTemplate().save(paymentPO);
    }

    @Override
    public List<PaymentPO> findAll() {
        return getHibernateTemplate().loadAll(PaymentPO.class);
    }

}

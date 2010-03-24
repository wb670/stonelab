package cn.zeroall.cow.dal.test.hibernate;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.zeroall.cow.dal.test.dao.TestDAO;
import cn.zeroall.cow.dal.test.po.TestPO;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> May 18, 2009
 */
public class TestHibernateDAO extends HibernateDaoSupport implements TestDAO {

    @Override
    public TestPO findById(Integer id) {
        return (TestPO) getHibernateTemplate().get(TestPO.class, id);
    }

    @Override
    public Integer add(TestPO testPO) {
        return (Integer) getHibernateTemplate().save(testPO);
    }

    @Override
    public List<TestPO> findByIds(Integer[] ids) {
        DetachedCriteria criteria = DetachedCriteria.forClass(TestPO.class).add(Expression.in("id", ids)).addOrder(
                Order.desc("id"));
        criteria.setProjection(Projections.rowCount());

        System.out.println(getHibernateTemplate().findByCriteria(criteria).get(0) + "==========");

        return getHibernateTemplate().findByCriteria(
                DetachedCriteria.forClass(TestPO.class).add(Expression.in("id", ids)).addOrder(Order.desc("id")), 1, 2);
    }

}

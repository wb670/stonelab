package cn.zeroall.cow.dal.alliance.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import cn.zeroall.cow.dal.BaseDAO;
import cn.zeroall.cow.dal.alliance.po.ReceivePO;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Sep 23, 2009
 */
public class AlliedReceiveDAO extends HibernateDaoSupport implements BaseDAO {

    public ReceivePO findByAlliedMemberId(Integer id) {
        DetachedCriteria criteria = DetachedCriteria.forClass(ReceivePO.class).add(
                Expression.eq("alliedMemberPO.id", id));
        List<ReceivePO> list = getHibernateTemplate().findByCriteria(criteria);
        return list.isEmpty() ? null : list.get(0);
    }
    
    public void create(ReceivePO receivePO) {
        if (receivePO == null) {
            throw new IllegalArgumentException("receivePO is null");
        }
        getHibernateTemplate().save(receivePO);
    }

    public void update(ReceivePO receivePO) {
        if (receivePO == null) {
            throw new IllegalArgumentException("receivePO is null");
        }
        getHibernateTemplate().update(receivePO);
    }

}

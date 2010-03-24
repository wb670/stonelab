package cn.zeroall.cow.dal.account.hibernate;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import cn.zeroall.cow.dal.account.dao.AccountTradeDetailDAO;
import cn.zeroall.cow.dal.account.po.AccountTradeDetailPO;
/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> May 19, 2009
 */
public class AccountTradeDetailHibernateDAO extends HibernateDaoSupport implements AccountTradeDetailDAO {

    @Override
    public Integer add(AccountTradeDetailPO AccountTradeDetailPO) {
        return (Integer) getHibernateTemplate().save(AccountTradeDetailPO);
    }

    @Override
    public AccountTradeDetailPO get(Integer id) {
        return (AccountTradeDetailPO) getHibernateTemplate().get(AccountTradeDetailPO.class, id);
    }

    @Override
    public void update(AccountTradeDetailPO AccountTradeDetailPO) {
        getHibernateTemplate().update(AccountTradeDetailPO);
    }

    @Override
    public List<AccountTradeDetailPO> getAccountTradeDetailByMemberId(Integer memberId) {
        DetachedCriteria criteria = DetachedCriteria.forClass(AccountTradeDetailPO.class).add(
                Expression.eq("memberId", memberId)).addOrder(Order.desc("id"));
        return getHibernateTemplate().findByCriteria(criteria);
    }
    
    
    @Override
    public List<AccountTradeDetailPO> getByMemberIdAndTime(Integer memberId,Date startTime,Date endTime) {
        DetachedCriteria criteria = DetachedCriteria.forClass(AccountTradeDetailPO.class).add(
                Expression.eq("memberId", memberId)).add(Expression.between("gmtModified", startTime, endTime)).addOrder(Order.desc("id"));
        return getHibernateTemplate().findByCriteria(criteria);
    }

}

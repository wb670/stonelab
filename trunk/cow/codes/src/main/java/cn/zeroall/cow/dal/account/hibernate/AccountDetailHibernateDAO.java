package cn.zeroall.cow.dal.account.hibernate;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.zeroall.cow.dal.account.dao.AccountDetailDAO;
import cn.zeroall.cow.dal.account.po.AccountDetailPO;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> May 19, 2009
 */
public class AccountDetailHibernateDAO extends HibernateDaoSupport implements AccountDetailDAO {

    @Override
    public Integer add(AccountDetailPO accountDetailPO) {
        return (Integer) getHibernateTemplate().save(accountDetailPO);
    }

    @Override
    public AccountDetailPO get(Integer id) {
        return (AccountDetailPO) getHibernateTemplate().get(AccountDetailPO.class, id);
    }

    @Override
    public void update(AccountDetailPO accountDetailPO) {
        getHibernateTemplate().update(accountDetailPO);
    }

    @Override
    public List<AccountDetailPO> getAccountDetailByMemberId(Integer memberId) {
        DetachedCriteria criteria = DetachedCriteria.forClass(AccountDetailPO.class).add(
                Expression.eq("memberId", memberId)).addOrder(Order.desc("id"));
        return getHibernateTemplate().findByCriteria(criteria);
    }

    @Override
    public Double sumTotalAmountByMemberId(Integer memberId) {
        DetachedCriteria criteria = DetachedCriteria.forClass(AccountDetailPO.class).add(
                Expression.eq("memberId", memberId)).add(Expression.gt("invalidDate", new Date()));
        criteria.setProjection(Projections.sum("amount"));
        return (Double) getHibernateTemplate().findByCriteria(criteria).get(0);
    }
    
    
    private List<AccountDetailPO> getAmountByMemberId(Integer memberId) {

        DetachedCriteria criteria = DetachedCriteria.forClass(AccountDetailPO.class).add(
                Expression.eq("memberId", memberId)).add(Expression.gt("invalidDate", new Date()));
        return getHibernateTemplate().findByCriteria(criteria);
    }

    @Override
    public double updateAmount(double cutAccount, Integer memberId) {

        double tempCutAccount = cutAccount;
        List<AccountDetailPO> accountDetailList = this.getAmountByMemberId(memberId);
        for (AccountDetailPO po : accountDetailList) {

            double tempAmount = po.getAmount();
            //判断每张购物券余额是否够支付
            if (tempCutAccount < tempAmount) {
                po.setAmount(tempAmount - tempCutAccount);
                this.update(po);
                tempCutAccount = 0.0;
                break;
            } else {
                tempCutAccount -= tempAmount;
                po.setAmount(0.0);// 全部扣除
                this.update(po);
            }
        }

        return tempCutAccount;
    }

}

package cn.zeroall.cow.biz.account.ao;

import java.util.List;

import cn.zeroall.cow.biz.BaseAO;
import cn.zeroall.cow.biz.BizException;
import cn.zeroall.cow.dal.account.dao.AccountDetailDAO;
import cn.zeroall.cow.dal.account.po.AccountDetailPO;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Sep 24, 2009
 */
public class AccountDetailAO implements BaseAO {

    private AccountDetailDAO accountDetailHibernateDAO;

    public List<AccountDetailPO> findByMemberId(Integer id) throws BizException {

        if (id == null) {
            throw new IllegalArgumentException("Member is null");
        }
        return accountDetailHibernateDAO.getAccountDetailByMemberId(id);

    }

    public Double sumTotalAmountByMemberId(Integer memberId)
    {
        Double total= accountDetailHibernateDAO.sumTotalAmountByMemberId(memberId);
        return total==null?Double.valueOf(0):total;
    }
    
    public void add(AccountDetailPO accountDetailPO) throws BizException {

        if (accountDetailPO == null) {
            throw new IllegalArgumentException("accountDetailPO is null");
        }
        accountDetailHibernateDAO.add(accountDetailPO);

    }

    public void update(AccountDetailPO accountDetailPO) throws BizException {

        if (accountDetailPO == null) {
            throw new IllegalArgumentException("accountDetailPO is null");
        }

        accountDetailHibernateDAO.update(accountDetailPO);
    }

    public void setAccountDetailHibernateDAO(AccountDetailDAO accountDetailHibernateDAO) {
        this.accountDetailHibernateDAO = accountDetailHibernateDAO;
    }

}

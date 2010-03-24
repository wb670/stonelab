package cn.zeroall.cow.biz.account.ao;

import java.util.Date;
import java.util.List;
import cn.zeroall.cow.biz.BaseAO;
import cn.zeroall.cow.biz.BizException;
import cn.zeroall.cow.dal.account.dao.AccountTradeDetailDAO;
import cn.zeroall.cow.dal.account.po.AccountTradeDetailPO;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Sep 24, 2009
 */
public class AccountTradeDetailAO implements BaseAO {

    private AccountTradeDetailDAO accountTradeDetailHibernateDAO;

    public List<AccountTradeDetailPO> findByMemberId(Integer memberId) throws BizException {

        if (memberId == null) {
            throw new IllegalArgumentException("memberId is null");
        }
        return accountTradeDetailHibernateDAO.getAccountTradeDetailByMemberId(memberId);

    }

    public List<AccountTradeDetailPO> findByMemberIdAndTime(Integer memberId, Date startTime,
            Date endTime) throws BizException {

        if (memberId == null) {
            throw new IllegalArgumentException("memberId is null");
        }
        return accountTradeDetailHibernateDAO.getByMemberIdAndTime(memberId, startTime, endTime);
    }

    public void add(AccountTradeDetailPO accountTradeDetailPO) throws BizException {

        if (accountTradeDetailPO == null) {
            throw new IllegalArgumentException("AccountTradeDetailPO is null");
        }
        accountTradeDetailHibernateDAO.add(accountTradeDetailPO);

    }

    public void update(AccountTradeDetailPO accountTradeDetailPO) throws BizException {

        if (accountTradeDetailPO == null) {
            throw new IllegalArgumentException("AccountTradeDetailPO is null");
        }

        accountTradeDetailHibernateDAO.update(accountTradeDetailPO);
    }

    public void setAccountTradeDetailHibernateDAO(
            AccountTradeDetailDAO accountTradeDetailHibernateDAO) {
        this.accountTradeDetailHibernateDAO = accountTradeDetailHibernateDAO;
    }

}

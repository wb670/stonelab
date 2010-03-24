package cn.zeroall.cow.dal.account.dao;

import java.util.Date;
import java.util.List;

import cn.zeroall.cow.dal.account.po.AccountTradeDetailPO;

public interface AccountTradeDetailDAO {

    public Integer add(AccountTradeDetailPO accountTradeDetailPO);

    public AccountTradeDetailPO get(Integer id);

    public void update(AccountTradeDetailPO accountTradeDetailPO);

    public List<AccountTradeDetailPO> getAccountTradeDetailByMemberId(Integer memberId);
    
    public List<AccountTradeDetailPO> getByMemberIdAndTime(Integer memberId,Date startTime,Date endTime);

}

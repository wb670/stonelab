package cn.zeroall.cow.dal.account.dao;

import java.util.List;

import cn.zeroall.cow.dal.account.po.AccountDetailPO;

public interface AccountDetailDAO {

    public Integer add(AccountDetailPO accountDetailPO);

    public AccountDetailPO get(Integer id);

    public void update(AccountDetailPO accountDetailPO);

    public List<AccountDetailPO> getAccountDetailByMemberId(Integer memberId);

    public Double sumTotalAmountByMemberId(Integer memberId);
    
    public double updateAmount(double cutAccount,Integer memberId);
}

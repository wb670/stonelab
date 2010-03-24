package cn.zeroall.cow.biz.account.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import cn.zeroall.cow.dal.account.po.AccountDetailPO;
import cn.zeroall.cow.dal.account.po.AccountTradeDetailPO;

public class AccountVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<AccountDetailPO> accountDetailList;

    private List<AccountTradeDetailPO> accountTradeDetailList;

    private Integer memberId;

    private Date deadline;

    private Integer tradeCount;
    
    private Float totalPrice;
    
    private Double totalAmount;//帐户金额
    
    
    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public List<AccountDetailPO> getAccountDetailList() {
        return accountDetailList;
    }

    public void setAccountDetailList(List<AccountDetailPO> accountDetailList) {
        this.accountDetailList = accountDetailList;
    }

    public List<AccountTradeDetailPO> getAccountTradeDetailList() {
        return accountTradeDetailList;
    }

    public void setAccountTradeDetailList(List<AccountTradeDetailPO> accountTradeDetailList) {
        this.accountTradeDetailList = accountTradeDetailList;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public Integer getTradeCount() {
        return tradeCount;
    }

    public void setTradeCount(Integer tradeCount) {
        this.tradeCount = tradeCount;
    }

    public Float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

}

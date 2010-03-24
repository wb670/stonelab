package cn.zeroall.cow.biz.account.ao;

import java.util.Date;

import cn.zeroall.cow.biz.BaseAO;
import cn.zeroall.cow.biz.BizException;
import cn.zeroall.cow.biz.account.vo.AccountVO;
import cn.zeroall.cow.biz.order.ao.TradeOrderAO;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Sep 24, 2009
 */
public class AccountAO implements BaseAO {

    private AccountDetailAO accountDetailAO;

    private AccountTradeDetailAO accountTradeDetailAO;

    private TradeOrderAO tradeOrderAO;

    AccountVO accountVO = new AccountVO();

    public AccountVO accountList(Integer memberId) throws BizException {

        this.fillAttr(memberId);
        accountVO.setAccountTradeDetailList(accountTradeDetailAO.findByMemberId(memberId));

        return accountVO;
    }

    public void fillAttr(Integer memberId) throws BizException {
        accountVO.setAccountDetailList(accountDetailAO.findByMemberId(memberId));
        accountVO.setTradeCount(tradeOrderAO.countByMemberIdNowTime(memberId));
        accountVO.setTotalPrice(tradeOrderAO.sumTotalPriceByMemberId(memberId));
        accountVO.setTotalAmount(accountDetailAO.sumTotalAmountByMemberId(memberId));
    }

    public AccountVO listByDate(Integer memberId, Date startTime, Date endTime) throws BizException {

        this.fillAttr(memberId);
        accountVO.setAccountTradeDetailList(accountTradeDetailAO.findByMemberIdAndTime(memberId,
                startTime, endTime));

        return accountVO;
    }

    public void setAccountDetailAO(AccountDetailAO accountDetailAO) {
        this.accountDetailAO = accountDetailAO;
    }

    public void setAccountTradeDetailAO(AccountTradeDetailAO accountTradeDetailAO) {
        this.accountTradeDetailAO = accountTradeDetailAO;
    }

    public void setTradeOrderAO(TradeOrderAO tradeOrderAO) {
        this.tradeOrderAO = tradeOrderAO;
    }

}

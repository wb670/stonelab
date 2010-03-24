package cn.zeroall.cow.web.member.action;

import java.text.ParseException;
import java.util.Date;
import org.apache.commons.lang.time.DateUtils;
import cn.zeroall.cow.biz.BizException;
import cn.zeroall.cow.biz.account.ao.AccountAO;
import cn.zeroall.cow.biz.account.vo.AccountVO;
import cn.zeroall.cow.service.member.model.Member;
import cn.zeroall.cow.web.BaseAction;
import cn.zeroall.cow.web.common.constants.SessionConstant;

/**
 * @category 爱可帐户
 */
public class AccountAction extends BaseAction {

    private static final long serialVersionUID = -3359372962548452541L;

    private AccountVO accountVO;

    private AccountAO accountAO;

    private String startYear;

    private String startMonth;

    private String endYear;

    private String endMonth;

    private Integer getMemberId() {
        Member member = (Member) getSession().get(SessionConstant.MEMBER_SESSION);
        return member != null ? Integer.valueOf(member.getId()) : null;
    }

    /***************************************************************************
     * * 爱可帐户列表
     **************************************************************************/
    public String accountList() {

        Integer memberId = this.getMemberId();
        try {
            accountVO = accountAO.accountList(memberId);
            accountVO.setMemberId(memberId);
            accountVO.setDeadline(new Date());

        } catch (BizException e) {
            addActionError(e.getBizCode().getMessage());
            return INPUT;
        }
        return SUCCESS;
    }

    /***************************************************************************
     * * 爱可帐户列表,根据日期查询
     **************************************************************************/
    public String listByDate() {

        Integer memberId = this.getMemberId();

        try
        {
            Date startDate=DateUtils.parseDate(startYear + "-"
                    + startMonth + "-01", new String[] { "yyyy-MM-dd" });
            Date endDate= DateUtils.parseDate(
                    endYear + "-" + endMonth + "-01", new String[] {"yyyy-MM-dd" });
            accountVO = accountAO.listByDate(memberId,startDate ,endDate);
            accountVO.setMemberId(memberId);
            accountVO.setDeadline(new Date());

        } catch (BizException e) {
            addActionError(e.getBizCode().getMessage());
            return INPUT;
        } catch (ParseException e) {
            return INPUT;
        }
        return SUCCESS;
    }

    public String getStartYear() {
        return startYear;
    }

    public void setStartYear(String startYear) {
        this.startYear = startYear;
    }

    public String getStartMonth() {
        return startMonth;
    }

    public void setStartMonth(String startMonth) {
        this.startMonth = startMonth;
    }

    public String getEndYear() {
        return endYear;
    }

    public void setEndYear(String endYear) {
        this.endYear = endYear;
    }

    public String getEndMonth() {
        return endMonth;
    }

    public void setEndMonth(String endMonth) {
        this.endMonth = endMonth;
    }

    public AccountVO getAccountVO() {
        return accountVO;
    }

    public void setAccountVO(AccountVO accountVO) {
        this.accountVO = accountVO;
    }

    public void setAccountAO(AccountAO accountAO) {
        this.accountAO = accountAO;
    }

}

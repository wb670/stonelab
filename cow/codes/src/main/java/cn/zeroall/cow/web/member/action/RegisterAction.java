package cn.zeroall.cow.web.member.action;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.struts2.ServletActionContext;
import cn.zeroall.cow.biz.account.AccountCode;
import cn.zeroall.cow.biz.BizException;
import cn.zeroall.cow.biz.account.ao.AccountDetailAO;
import cn.zeroall.cow.biz.account.ao.AccountTradeDetailAO;
import cn.zeroall.cow.biz.member.ao.InviteAO;
import cn.zeroall.cow.biz.member.ao.MemberAO;
import cn.zeroall.cow.dal.account.po.AccountDetailPO;
import cn.zeroall.cow.dal.account.po.AccountTradeDetailPO;
import cn.zeroall.cow.dal.member.po.InvitePO;
import cn.zeroall.cow.service.member.model.Member;
import cn.zeroall.cow.web.BaseAction;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> May 2, 2009
 */
public class RegisterAction extends BaseAction {

    private static final long serialVersionUID = -3359372962548452541L;

    private MemberAO memberAO;

    private InviteAO inviteAO;

    private AccountDetailAO accountDetailAO;

    private AccountTradeDetailAO accountTradeDetailAO;
    
    Date date = new Date();

    private Integer getMemberId() {
        Integer memberId = (Integer) getSession().get("memberId");
        return memberId != null ? memberId : null;
    }

    private Integer getInviteId() {
        Integer inviteId = (Integer) getSession().get("inviteId");
        return inviteId != null ? inviteId : null;
    }

    private HttpServletRequest getReqeustInfo() {
        return ServletActionContext.getRequest();
    }

    /***************************************************************************
     * * 注册页面
     **************************************************************************/
    public String register() {
        String memberStr = getReqeustInfo().getParameter("memberId");
        String inviteStr = getReqeustInfo().getParameter("inviteId");
        if (memberStr != null) {
            Integer memberId = Integer.valueOf(memberStr);
            if (memberId != null) {
                memberId = memberAO.getId(memberId);
                if (memberId != null) {
                    this.getSession().put("memberId", memberId);
                }
            }
        }
        if (inviteStr != null) {
            Integer inviteId = Integer.valueOf(inviteStr);
            if (inviteId != null) {
                this.getSession().put("inviteId", inviteId);
            }
        }
        return SUCCESS;
    }

    /***************************************************************************
     * * 用户注册
     **************************************************************************/
    private String loginId;
    private String password;
    private String repassword;
    private String checkCode;

    public String doRegister() {

        Integer memberId = getMemberId();//邀请人MEMBER表中的ID号
        Integer inviteId = getInviteId();
        // 表示是通过qq/msn介绍注册的
        if (memberId != null) {
            InvitePO invitePO = new InvitePO();
            invitePO.setMemberId(memberId);
            invitePO.setInviteType((short) 0);
            invitePO.setInviteIp(getReqeustInfo().getRemoteAddr());
            invitePO.setInviteSuccess(true);
            try {
                inviteAO.add(invitePO);
                
            } catch (BizException e) {
                addActionError(e.getBizCode().getMessage());
            }
        }
        // 表示是通过email介绍注册的
        if (inviteId != null) {
            InvitePO invitePO = new InvitePO();
            List<InvitePO> invitePOList = inviteAO.findByInviteIdEmailInfo(inviteId);
            if (invitePOList != null) {
                invitePO = invitePOList.get(0);
                invitePO.setInviteIp(getReqeustInfo().getRemoteAddr());
                invitePO.setInviteSuccess(true);
                try {
                    inviteAO.update(invitePO);
                    
                } catch (BizException e) {
                    addActionError(e.getBizCode().getMessage());
                }
            }

        }

        Member member = new Member();
        member.setLoginId(loginId);
        member.setPassword(password);
        try {
            Integer insertedMemberId=memberAO.add(member);
            //判断邀请成功个数是否超过20个
            Integer inviteCountByMemberId=inviteAO.countInviteSuccess(memberId);
            if(inviteCountByMemberId<20)
            {
                accountDetailAO.add(fillAccountDetailAttr(memberId,1));
                accountTradeDetailAO.add(fillAccountTradeDetailAttr(memberId,1));
            }
            
            accountDetailAO.add(fillAccountDetailAttr(insertedMemberId,10));
            accountTradeDetailAO.add(fillAccountTradeDetailAttr(insertedMemberId,10));
            

        } catch (BizException e) {
            addActionError(e.getBizCode().getMessage());
            return INPUT;
        }
        return SUCCESS;
    }

    public AccountDetailPO fillAccountDetailAttr(Integer memberId,double account) {
        
        AccountDetailPO accountDetailPO = new AccountDetailPO();
        try 
        {
            accountDetailPO.setAmount(account);
            accountDetailPO.setMemberId(memberId);
            accountDetailPO.setGmtCreated(date);
            accountDetailPO.setGmtModified(date);
            Date validDate = DateUtils.parseDate("2009-10-15 00:00:00",
                    new String[] { "yyyy-MM-dd hh:mm:ss" });
            Date invalidDate = DateUtils.parseDate("2010-01-30 00:00:00",
                    new String[] { "yyyy-MM-dd hh:mm:ss" });
            accountDetailPO.setValidDate(validDate);
            accountDetailPO.setInvalidDate(invalidDate);
            accountDetailPO.setType(AccountCode.ACCOUNT_DETAIL_SHORT_TYPE.getMessage());
            accountDetailPO.setStatus(AccountCode.ACCOUNT_DETAIL_IN_EFFECT_STATUS.getMessage());
            
        } catch (ParseException e) {
        }
        return accountDetailPO;
    }

    public AccountTradeDetailPO fillAccountTradeDetailAttr(Integer memberId,double account) {
        AccountTradeDetailPO accountTradeDetailPO = new AccountTradeDetailPO();
        accountTradeDetailPO.setAmount(account);
        accountTradeDetailPO.setGmtCreated(date);
        accountTradeDetailPO.setGmtModified(date);
        accountTradeDetailPO.setMemberId(memberId);
        accountTradeDetailPO.setType(AccountCode.ACCOUNT_TREDE__DETAIL_INPUT_TYPE.getMessage());
        return accountTradeDetailPO;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = StringUtils.trim(loginId);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepassword() {
        return repassword;
    }

    public void setRepassword(String repassword) {
        this.repassword = repassword;
    }

    public String getCheckCode() {
        return checkCode;
    }

    public void setCheckCode(String checkCode) {
        this.checkCode = checkCode;
    }

    public void setMemberAO(MemberAO memberAO) {
        this.memberAO = memberAO;
    }

    public void setInviteAO(InviteAO inviteAO) {
        this.inviteAO = inviteAO;
    }

    public void setAccountDetailAO(AccountDetailAO accountDetailAO) {
        this.accountDetailAO = accountDetailAO;
    }

    public void setAccountTradeDetailAO(AccountTradeDetailAO accountTradeDetailAO) {
        this.accountTradeDetailAO = accountTradeDetailAO;
    }

}

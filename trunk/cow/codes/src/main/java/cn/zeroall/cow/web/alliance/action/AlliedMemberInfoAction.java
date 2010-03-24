package cn.zeroall.cow.web.alliance.action;

import cn.zeroall.cow.biz.BizException;
import cn.zeroall.cow.biz.alliance.AlliedMemberCode;
import cn.zeroall.cow.biz.alliance.ao.AlliedMemberAO;
import cn.zeroall.cow.dal.alliance.po.AlliedMemberPO;
import cn.zeroall.cow.web.BaseAction;
import cn.zeroall.cow.web.common.constants.SessionConstant;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Sep 23, 2009
 */
public class AlliedMemberInfoAction extends BaseAction {

    private static final long serialVersionUID = -3204340154415344840L;
    private AlliedMemberAO alliedMemberAO;
    private AlliedMemberPO alliedMember;
    private String repassword;

    /***************************************************************************
     * *start 修改信息页面
     **************************************************************************/
    public String modify() {
        AlliedMemberPO member = alliedMemberAO.getAlliedMember(getLoginId());
        if (member != null) {
            alliedMember = member;
        }
        return SUCCESS;
    }

    public String doModify() {
        try {
            alliedMember.setLoginId(getLoginId());
            alliedMemberAO.update(alliedMember);
        } catch (BizException e) {
            addActionError(e.getBizCode().getMessage());
            return INPUT;
        }
        addActionMessage(AlliedMemberCode.MEMBER_MODIFY_OK.getMessage());
        return SUCCESS;
    }

    /***************************************************************************
     * *end 修改信息页面
     **************************************************************************/

    /***************************************************************************
     * *start 修改密码
     **************************************************************************/

    private String password;
    private String newpassword;
    private String renewpassword;

    public String modifyPwd() {
        return SUCCESS;
    }

    public String doModifyPwd() {

        try {
            alliedMemberAO.updatePwd(getLoginId(), password, newpassword);
        } catch (BizException e) {
            addActionError(e.getBizCode().getMessage());
            return INPUT;
        }
        addActionMessage(AlliedMemberCode.MEMBER_PWD_MODIFY_OK.getMessage());
        return SUCCESS;
    }

    /***************************************************************************
     * *end 修改密码
     **************************************************************************/

    /**
     * fill attribute
     */
    // private void fillMemberAttrs(AlliedMemberPO member) {
    //
    // member.setAddress(alliedMember.getAddress());
    // member.setEmail(alliedMember.getEmail());
    // member.setEnterpriseName(alliedMember.getEnterpriseName());
    // member.setFax(alliedMember.getFax());
    // member.setIdentityCard(alliedMember.getIdentityCard());
    // member.setMobile(alliedMember.getMobile());
    // member.setMsn(alliedMember.getMsn());
    // member.setName(alliedMember.getName());
    // member.setQq(alliedMember.getQq());
    // member.setSiteAddress(alliedMember.getSiteAddress());
    // member.setSiteKind(alliedMember.getSiteKind());
    // member.setSiteName(alliedMember.getSiteName());
    // member.setSiteType(alliedMember.getSiteType());
    // member.setTelphone(alliedMember.getTelphone());
    // member.setZip(alliedMember.getZip());
    // }
    private String getLoginId() {
        AlliedMemberPO member = (AlliedMemberPO) getSession().get(
                SessionConstant.ALLIED_MEMBER_SESSION);
        return member != null ? member.getLoginId() : null;
    }

    public AlliedMemberPO getAlliedMember() {
        return alliedMember;
    }

    public void setAlliedMember(AlliedMemberPO alliedMember) {
        this.alliedMember = alliedMember;
    }

    public String getRepassword() {
        return repassword;
    }

    public void setRepassword(String repassword) {
        this.repassword = repassword;
    }

    public void setAlliedMemberAO(AlliedMemberAO alliedMemberAO) {
        this.alliedMemberAO = alliedMemberAO;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNewpassword() {
        return newpassword;
    }

    public void setNewpassword(String newpassword) {
        this.newpassword = newpassword;
    }

    public String getRenewpassword() {
        return renewpassword;
    }

    public void setRenewpassword(String renewpassword) {
        this.renewpassword = renewpassword;
    }

}

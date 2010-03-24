package cn.zeroall.cow.web.alliance.action;

import net.sf.cglib.beans.BeanCopier;
import cn.zeroall.cow.biz.BizException;
import cn.zeroall.cow.biz.alliance.AlliedMemberCode;
import cn.zeroall.cow.biz.alliance.ao.AlliedMemberAO;
import cn.zeroall.cow.dal.alliance.po.AlliedMemberPO;
import cn.zeroall.cow.web.BaseAction;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Sep 23, 2009
 */
public class AlliedMemberAction extends BaseAction {

    private static final long serialVersionUID = -3204340154415344840L;
    
    private static final String REGISTER_SUCCESS="registerSuccess";

    private static final BeanCopier COPIER = BeanCopier.create(AlliedMemberPO.class, AlliedMemberPO.class, false);

    private AlliedMemberAO alliedMemberAO;

    private AlliedMemberPO alliedMember;
    private String repassword;

    public String register() {
        return SUCCESS;
    }
    
    public String doRegister() {
        try {
            alliedMemberAO.add(cloneAlliedMember());
        } catch (BizException e) {
            addActionError(e.getBizCode().getMessage());
            return INPUT;
        }
        addActionMessage(AlliedMemberCode.REGISTER_SUCCESS.getMessage());
        return REGISTER_SUCCESS;
    }
        

    private AlliedMemberPO cloneAlliedMember() {
        AlliedMemberPO clone = new AlliedMemberPO();
        COPIER.copy(alliedMember, clone, null);
        return clone;
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

}

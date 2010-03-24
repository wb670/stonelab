package cn.zeroall.cow.web.alliance.action;

import cn.zeroall.cow.biz.BizException;
import cn.zeroall.cow.biz.alliance.AlliedMemberCode;
import cn.zeroall.cow.biz.alliance.ao.AlliedReceiveAO;
import cn.zeroall.cow.dal.alliance.po.AlliedMemberPO;
import cn.zeroall.cow.dal.alliance.po.ReceivePO;
import cn.zeroall.cow.web.BaseAction;
import cn.zeroall.cow.web.common.constants.SessionConstant;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Sep 23, 2009
 */
public class ReceiveAction extends BaseAction {

    private static final long serialVersionUID = -3204340154415344840L;

    private static final String MODIFY = "modify";

    private AlliedReceiveAO alliedReceiveAO;

    private ReceivePO receivePO;

    
    private AlliedMemberPO getAlliedMember() {
        AlliedMemberPO member = (AlliedMemberPO) getSession().get(
                SessionConstant.ALLIED_MEMBER_SESSION);
        return member;
    }

    private ReceivePO getPo() {

        ReceivePO po = null;
        try {
            po = alliedReceiveAO.findByAlliedMemberId(getAlliedMember().getId());
        } catch (BizException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return receivePO = po;

    }

    /**
     * ************************************************
     * @添加部分
     * ************************************************
     */

    public String doAdd() {
        try {
            receivePO.setAlliedMemberPO(getAlliedMember());
            alliedReceiveAO.add(receivePO);
        } catch (Exception e) {
            e.printStackTrace();
            addActionMessage(AlliedMemberCode.MEMBER_RCEIVE_ADD_FAIL.getMessage());
            return INPUT;
        }
        addActionMessage(AlliedMemberCode.MEMBER_RCEIVE_ADD_OK.getMessage());
        return MODIFY;
    }

    
    
    /**
     * ************************************************
     * @修改部分
     * ************************************************
     */
    public String modify() {
        this.getPo();
        return receivePO == null ? SUCCESS : MODIFY;
    }

    public String doModify() {
        try {
            receivePO.setAlliedMemberPO(getAlliedMember());
            alliedReceiveAO.update(receivePO);
        } catch (Exception e) {
            e.printStackTrace();
            addActionMessage(AlliedMemberCode.MEMBER_MODIFY_FAIL.getMessage());
            return INPUT;
        }
        addActionMessage(AlliedMemberCode.MEMBER_MODIFY_OK.getMessage());
        return MODIFY;
    }

    public void setAlliedReceiveAO(AlliedReceiveAO alliedReceiveAO) {
        this.alliedReceiveAO = alliedReceiveAO;
    }

    public ReceivePO getReceivePO() {
        return receivePO;
    }

    public void setReceivePO(ReceivePO receivePO) {
        this.receivePO = receivePO;
    }

}

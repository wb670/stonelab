package cn.zeroall.cow.biz.member.ao;

import java.util.List;

import cn.zeroall.cow.biz.BaseAO;
import cn.zeroall.cow.biz.BizException;
import cn.zeroall.cow.dal.member.hibernate.InviteHibernateDAO;
import cn.zeroall.cow.dal.member.po.InvitePO;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Aug 13, 2009
 */
public class InviteAO implements BaseAO {

    private InviteHibernateDAO inviteHibernateDAO;

    public void add(InvitePO invitePO) throws BizException {
        if (invitePO == null) {
            throw new IllegalArgumentException("invitePO is null.");
        }
        inviteHibernateDAO.add(invitePO);
    }

    public void update(InvitePO invitePO) throws BizException {
        if (invitePO == null) {
            throw new IllegalArgumentException("invitePO is null.");
        }
        inviteHibernateDAO.update(invitePO);
    }

    public List<InvitePO> findByMemberId(Integer memberId) {

        if (memberId == null) {
            throw new IllegalArgumentException("memberId is null.");
        }
        return inviteHibernateDAO.findByMemberId(memberId);
    }

    public Integer countInviteSuccess(Integer memberId) {

        if (memberId == null) {
            throw new IllegalArgumentException("memberId is null.");
        }
        return inviteHibernateDAO.countInviteSuccess(memberId);
    }
    
    public List<InvitePO> findByInviteIdEmailInfo(Integer inviteId)
    {
        if (inviteId == null) {
            throw new IllegalArgumentException("inviteId is null.");
        }
        return inviteHibernateDAO.findByInviteIdEmailInfo(inviteId);
    }

    public List<InvitePO> findByMemberIdEmailInfo(Integer memberId) {

        if (memberId == null) {
            throw new IllegalArgumentException("memberId is null.");
        }
        return inviteHibernateDAO.findByMemberIdEmailInfo(memberId);
    }

    public void setInviteHibernateDAO(InviteHibernateDAO inviteHibernateDAO) {
        this.inviteHibernateDAO = inviteHibernateDAO;
    }

}

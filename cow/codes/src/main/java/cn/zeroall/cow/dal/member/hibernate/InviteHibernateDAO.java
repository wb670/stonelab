package cn.zeroall.cow.dal.member.hibernate;

import java.util.Date;
import java.util.List;
import org.hibernate.criterion.*;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.zeroall.cow.dal.member.po.InvitePO;
import cn.zeroall.cow.dal.member.po.MemberPO;

public class InviteHibernateDAO extends HibernateDaoSupport {

    
    public void add(MemberPO memberPO) {
        getHibernateTemplate().save(memberPO);
    }
    
    public void add(InvitePO invitePO) {
        Date now = new Date();
        invitePO.setGmtCreated(now);
        invitePO.setGmtModified(now);
        getHibernateTemplate().save(invitePO);
    }
    
    public void update(InvitePO invitePO) {
        invitePO.setGmtModified(new Date());
        
        getHibernateTemplate().update(invitePO);
    }

    public List<InvitePO> findByMemberId(Integer memberId) {
        DetachedCriteria criteria = DetachedCriteria.forClass(InvitePO.class).add(
                Expression.eq("memberId", memberId));
        return getHibernateTemplate().findByCriteria(criteria);
    }
    
    public Integer countInviteSuccess(Integer memberId) {
        DetachedCriteria criteria = DetachedCriteria.forClass(InvitePO.class).add(
                Expression.eq("memberId", memberId)).add(Expression.eq("inviteSuccess", true)).setProjection(Projections.rowCount());
        
        return (Integer)getHibernateTemplate().findByCriteria(criteria).get(0);
    }
    
    public List<InvitePO> findByMemberIdEmailInfo(Integer memberId) {
        DetachedCriteria criteria = DetachedCriteria.forClass(InvitePO.class).add(
                Expression.eq("memberId", memberId)).add(Expression.eq("inviteType", (short)1));
        return getHibernateTemplate().findByCriteria(criteria);
    }
    
    public List<InvitePO> findByInviteIdEmailInfo(Integer id) {
        DetachedCriteria criteria = DetachedCriteria.forClass(InvitePO.class).add(
                Expression.eq("id", id)).add(Expression.eq("inviteType", (short)1));
        return getHibernateTemplate().findByCriteria(criteria);
    }
    
    

}

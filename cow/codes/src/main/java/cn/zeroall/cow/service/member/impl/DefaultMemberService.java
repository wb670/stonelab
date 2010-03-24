package cn.zeroall.cow.service.member.impl;

import org.apache.commons.lang.StringUtils;

import cn.zeroall.chameleon.vtree.Tree;
import cn.zeroall.chameleon.vtree.TreeException;
import cn.zeroall.cow.common.util.Md5Util;
import cn.zeroall.cow.dal.common.po.RegionPO;
import cn.zeroall.cow.dal.member.dao.MemberDAO;
import cn.zeroall.cow.dal.member.hibernate.InviteHibernateDAO;
import cn.zeroall.cow.dal.member.po.MemberPO;
import cn.zeroall.cow.service.common.RegionService;
import cn.zeroall.cow.service.member.MemberService;
import cn.zeroall.cow.service.member.adapter.MemberAdapter;
import cn.zeroall.cow.service.member.model.Member;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Apr 30, 2009
 */
public class DefaultMemberService implements MemberService {

    private MemberDAO memberDAO;

    private RegionService regionService;
    

    @Override
    public Integer create(Member member) {
        if (member == null) {
            throw new IllegalArgumentException("member is null");
        }
        MemberPO memberPO = new MemberAdapter(getRegionTree(), member).getMemberPO();
        memberPO.setSeed(String.valueOf(System.currentTimeMillis()));
        memberPO.setPassword(Md5Util.encrypt(member.getPassword(), memberPO.getSeed()));
        return memberDAO.create(memberPO);
    }

    @Override
    public Member findByLoginId(String loginId) {
        if (StringUtils.isBlank(loginId)) {
            throw new IllegalArgumentException("loginId is blank.");
        }
        MemberPO memberPO = memberDAO.findByLoginId(StringUtils.trim(loginId));
        if (memberPO == null) {
            return null;
        }
        return new MemberAdapter(getRegionTree(), memberPO).getMember();
    }
    
    @Override
    public Integer findById(Integer id) {
        
        Integer memberId= memberDAO.findById(id).getId();
        if (memberId == null) {
            return null;
        } 
        return memberId;
    }
   

    @Override
    public boolean isLogin(String loginId, String password) {
        if (StringUtils.isBlank(loginId)) {
            throw new IllegalArgumentException("loginId is blank.");
        }
        MemberPO memberPO = memberDAO.findByLoginId(StringUtils.trim(loginId));
        if (memberPO == null) {
            return false;
        }
        String encrypted = Md5Util.encrypt(password, memberPO.getSeed());
        return StringUtils.equals(encrypted, memberPO.getPassword());
    }

    @Override
    public void update(Member member) {
        if (member == null || member.getId() == null) {
            throw new IllegalArgumentException("member or member's id is null");
        }
        MemberPO memberPO = new MemberAdapter(getRegionTree(), member).getMemberPO();
        memberDAO.updateById(memberPO);
    }

    @Override
    public void updatePasswordById(Integer id, String password) {
        if (id == null || StringUtils.isBlank(password)) {
            throw new IllegalArgumentException("id is null or password is blank");
        }
        MemberPO memberPO = memberDAO.findById(id);
        if (memberPO == null) {
            return;// do nothing
        }
        String encrypted = Md5Util.encrypt(password, memberPO.getSeed());
        memberDAO.updatePasswordById(id, encrypted);
    }

    private Tree<RegionPO> getRegionTree() {
        Tree<RegionPO> regionTree;
        try {
            regionTree = regionService.getRegion();
        } catch (TreeException e) {
            throw new RuntimeException("get region error.", e);
        }
        return regionTree;
    }

    public void setMemberDAO(MemberDAO memberDAO) {
        this.memberDAO = memberDAO;
    }

    public void setRegionService(RegionService regionService) {
        this.regionService = regionService;
    }

}

package cn.zeroall.cow.service.member.adapter;

import net.sf.cglib.beans.BeanCopier;

import org.apache.commons.lang.StringUtils;

import cn.zeroall.chameleon.vtree.Tree;
import cn.zeroall.cow.dal.common.po.RegionPO;
import cn.zeroall.cow.dal.member.po.MemberPO;
import cn.zeroall.cow.service.member.enums.Sex;
import cn.zeroall.cow.service.member.model.Member;
import cn.zeroall.cow.service.member.model.Region;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Apr 30, 2009
 */
public class MemberAdapter {

    private static final String CN = "CN";

    private static final BeanCopier member2MemberPO = BeanCopier.create(Member.class, MemberPO.class, false);
    private static final BeanCopier memberPO2Member = BeanCopier.create(MemberPO.class, Member.class, false);

    private Tree<RegionPO> regionTree;

    private Member member;
    private MemberPO memberPO;

    public MemberAdapter(Tree<RegionPO> regionTree, Member member) {
        this.regionTree = regionTree;
        this.member = member;
        initMember2MemberPO();
    }

    public MemberAdapter(Tree<RegionPO> regionTree, MemberPO memberPO) {
        this.regionTree = regionTree;
        this.memberPO = memberPO;
        initMemberPO2Member();
    }

    public Member getMember() {
        return member;
    }

    public MemberPO getMemberPO() {
        return memberPO;
    }

    private void initMember2MemberPO() {
        memberPO = new MemberPO();
        member2MemberPO.copy(member, memberPO, null);
        memberPO.setLoginId(StringUtils.trim(memberPO.getLoginId()));
        memberPO.setProvince(getRegionName(member.getCountry(), member.getProvince()));
        memberPO.setCity(getRegionName(member.getCountry(), member.getCity()));
        memberPO.setArea(getRegionName(member.getCountry(), member.getArea()));
        if (member.getSex() != null) {
            memberPO.setSex(member.getSex().toString());
        }
    }

    private void initMemberPO2Member() {
        member = new Member();
        memberPO2Member.copy(memberPO, member, null);
        member.setLoginId(StringUtils.trim(member.getLoginId()));
        member.setProvince(getRegion(memberPO.getCountry(), memberPO.getProvince()));
        member.setCity(getRegion(memberPO.getCountry(), memberPO.getCity()));
        member.setArea(getRegion(memberPO.getCountry(), memberPO.getArea()));
        if (memberPO.getSex() != null) {
            member.setSex(Enum.valueOf(Sex.class, memberPO.getSex()));
        }
    }

    private Region getRegion(String country, String value) {
        if (StringUtils.isBlank(value)) {
            return null;
        }
        if (StringUtils.equals(CN, country)) {
            RegionPO regionPO = regionTree.getNodeById(Integer.valueOf(value));
            String name = (regionPO == null ? null : regionPO.getName());
            return new Region(Integer.valueOf(value), name);
        }
        return new Region(value);
    }

    private String getRegionName(String country, Region region) {
        if (region == null) {
            return null;
        }
        if (StringUtils.equals(CN, country)) {
            return String.valueOf(region.getCode());
        }
        return region.getName();
    }
}

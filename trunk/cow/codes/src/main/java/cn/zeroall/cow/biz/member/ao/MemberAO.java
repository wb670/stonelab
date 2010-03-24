package cn.zeroall.cow.biz.member.ao;

import cn.zeroall.cow.biz.BaseAO;
import cn.zeroall.cow.biz.BizException;
import cn.zeroall.cow.biz.member.MemberCode;
import cn.zeroall.cow.service.member.MemberService;
import cn.zeroall.cow.service.member.model.Member;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Aug 13, 2009
 */
public class MemberAO implements BaseAO {

    private MemberService memberService;

    public Integer add(Member member) throws BizException {
        if (member == null) {
            throw new IllegalArgumentException("member is null.");
        }
        if (memberService.findByLoginId(member.getLoginId()) != null) {
            throw new BizException(MemberCode.DUPLICATE_LOGIN_ID);
        }
        return  memberService.create(member);
    }

    public Member getMember(String loginId) {
        return memberService.findByLoginId(loginId);
    }
    
    
    public Integer getId(Integer memberId) {
        return memberService.findById(memberId);
    }
    

    public void update(Member member) {
        if (member == null) {
            throw new IllegalArgumentException("member is null.");
        }
        memberService.update(member);
    }

    public void updatePwd(String loginId, String password, String newPassword) throws BizException {
        boolean isLogin = memberService.isLogin(loginId, password);
        if (!isLogin) {
            throw new BizException(MemberCode.MEMBER_PWD_INCORRECT);
        }
        Member member = memberService.findByLoginId(loginId);
        memberService.updatePasswordById(member.getId(), newPassword);
    }

    public void setMemberService(MemberService memberService) {
        this.memberService = memberService;
    }

}

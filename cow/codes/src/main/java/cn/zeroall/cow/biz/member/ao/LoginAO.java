package cn.zeroall.cow.biz.member.ao;

import org.apache.commons.lang.StringUtils;

import cn.zeroall.cow.biz.BaseAO;
import cn.zeroall.cow.service.member.MemberService;
import cn.zeroall.cow.service.member.model.Member;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> May 2, 2009
 */
public class LoginAO implements BaseAO {

    private MemberService memberService;

    public boolean isLogin(String loginId, String password) {
        if (StringUtils.isBlank(loginId)) {
            return false;
        }
        return memberService.isLogin(loginId, password);
    }

    public Member getLoginedMember(String loginId) {
        return memberService.findByLoginId(loginId);
    }

    public void setMemberService(MemberService memberService) {
        this.memberService = memberService;
    }

}

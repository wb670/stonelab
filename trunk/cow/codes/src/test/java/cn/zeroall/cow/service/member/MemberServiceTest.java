package cn.zeroall.cow.service.member;

import cn.zeroall.cow.BaseTestCase;
import cn.zeroall.cow.service.member.enums.Sex;
import cn.zeroall.cow.service.member.model.Member;
import cn.zeroall.cow.service.member.model.Region;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Apr 30, 2009
 */
public class MemberServiceTest extends BaseTestCase {

    private MemberService memberService;

    @Override
    protected void setUp() throws Exception {
        memberService = (MemberService) getBean("memberService");
    }

    public void testCreate() {
        Member member = new Member();
        member.setLoginId(String.valueOf(System.currentTimeMillis()));
        member.setPassword("password");
        member.setSex(Sex.M);
        member.setCountry("CN");
        member.setProvince(new Region(1, "浙江"));
        member.setCity(new Region(2, "杭州"));
        member.setArea(new Region(3, "滨江区"));
        memberService.create(member);
        
    }

    public void testUpdate() {
        Member member = new Member();
        member.setId(6);
        member.setSex(Sex.M);
        member.setCountry("CN");
        member.setProvince(new Region(1, ""));
        member.setCity(new Region(1, ""));
        member.setArea(new Region(1, ""));
        member.setAddress("address");
        member.setTelphone("1234");
        member.setMobile("5678");
        memberService.update(member);
    }

    public void testUpdatePassword() {
        memberService.updatePasswordById(6, "000000");
    }

    public void testIsLogin() {
        System.out.println(memberService.isLogin("stone2083@yahoo.cn", "000000"));
    }

}

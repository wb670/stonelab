package cn.zeroall.cow.service.member;

import cn.zeroall.cow.service.BaseService;
import cn.zeroall.cow.service.member.model.Member;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Apr 30, 2009
 */
public interface MemberService extends BaseService {

    public Integer create(Member member);

    public Member findByLoginId(String loginId);
    
    public Integer findById(Integer id);

    public boolean isLogin(String loginId, String password);

    public void update(Member member);

    public void updatePasswordById(Integer id, String password);

}

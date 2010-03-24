package cn.zeroall.cow.web.common.interceptors;

import cn.zeroall.cow.service.member.model.Member;
import cn.zeroall.cow.web.common.constants.SessionConstant;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> May 11, 2009
 */
public class MemberAuthInterceptor extends BaseAuthInterceptor {

    private static final long serialVersionUID = -6185061373878960195L;

    @Override
    protected Object getAuthSession() {
        return (Member) getSession().get(SessionConstant.MEMBER_SESSION);
    }

    @Override
    protected boolean isAuth(Object authSession) {
        if (authSession instanceof Member) {
            return authSession != null;
        }
        return false;
    }

}

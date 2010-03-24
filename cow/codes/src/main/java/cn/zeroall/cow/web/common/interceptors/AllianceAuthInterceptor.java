package cn.zeroall.cow.web.common.interceptors;

import cn.zeroall.cow.dal.alliance.po.AlliedMemberPO;
import cn.zeroall.cow.web.common.constants.SessionConstant;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Oct 9, 2009
 */
public class AllianceAuthInterceptor extends BaseAuthInterceptor {

    private static final long serialVersionUID = -5189087178674782813L;

    @Override
    protected Object getAuthSession() {
        return (AlliedMemberPO) getSession().get(SessionConstant.ALLIED_MEMBER_SESSION);
    }

    @Override
    protected boolean isAuth(Object authSession) {
        if (authSession instanceof AlliedMemberPO) {
            return authSession != null;
        }
        return false;
    }

}

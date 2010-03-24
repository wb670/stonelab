package cn.zeroall.cow.web.alliance.action;

import java.util.Map;

import org.apache.commons.lang.StringUtils;

import cn.zeroall.cow.biz.alliance.AlliedMemberCode;
import cn.zeroall.cow.biz.alliance.ao.AlliedMemberLoginAO;
import cn.zeroall.cow.dal.alliance.po.AlliedMemberPO;
import cn.zeroall.cow.web.BaseAction;
import cn.zeroall.cow.web.common.constants.SessionConstant;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Aug 13, 2009
 */
public class AlliedLoginAction extends BaseAction {

    private static final long serialVersionUID = -145353815611021319L;

    private AlliedMemberLoginAO alliedLoginAO;

    /***************************************************************************
     * * 登录页面
     **************************************************************************/
    public String login() {
        return SUCCESS;
    }

    /***************************************************************************
     * * 登录动作
     **************************************************************************/
    private static final String HOME = "home";
    private static final String URL = "url";
    private static final String OUTURL = "outurl";
    
    
    private String loginId;
    private String password;
    private String redirectUrl;

    public String doLogin() {
        boolean isLogin = alliedLoginAO.isLogin(loginId, password);
        // 用户或密码不对
        if (!isLogin) {
            addActionError(AlliedMemberCode.LOGIN_ERROR.getMessage());
            return INPUT;
        }
        // 做登录操作
        AlliedMemberPO member = alliedLoginAO.getLoginedMember(loginId);
        Map<String, Object> session = getSession();
        session.put(SessionConstant.ALLIED_MEMBER_SESSION, member);
        setSession(session);
        // 设置需要跳转的页面
        redirectUrl = (String) getSession().get(SessionConstant.REDIRECT_URL);
        // 返回页面
        return StringUtils.isBlank(redirectUrl) ? HOME : URL;
    }

    /***************************************************************************
     * * 登出动作
     **************************************************************************/
    public String doLoginout() {
        Map<String, Object> session = getSession();
        session.remove(SessionConstant.ALLIED_MEMBER_SESSION);
        session.remove(SessionConstant.REDIRECT_URL);
        session.remove(SessionConstant.CODE_IMAGE_SESSION);
        setSession(session);
        return OUTURL;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public String getLoginId() {
        return loginId;
    }

    public String getPassword() {
        return password;
    }

    public void setAlliedLoginAO(AlliedMemberLoginAO alliedLoginAO) {
        this.alliedLoginAO = alliedLoginAO;
    }

}

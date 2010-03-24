package cn.zeroall.cow.web.member.action;

import java.util.Map;

import org.apache.commons.lang.StringUtils;

import cn.zeroall.cow.biz.member.MemberCode;
import cn.zeroall.cow.biz.member.ao.LoginAO;
import cn.zeroall.cow.service.member.model.Member;
import cn.zeroall.cow.web.BaseAction;
import cn.zeroall.cow.web.common.constants.SessionConstant;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Aug 13, 2009
 */
public class LoginAction extends BaseAction {

    private static final long serialVersionUID = -145353815611021319L;

    private LoginAO loginAO;

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

    private String loginId;
    private String password;

    private String redirectUrl;

    public String doLogin() {
        boolean isLogin = loginAO.isLogin(loginId, password);
        // 没有登录，返回登录页面
        if (!isLogin) {
            addActionError(MemberCode.LOGIN_ERROR.getMessage());
            return INPUT;
        }
        // 做登录操作
        Member member = loginAO.getLoginedMember(loginId);
        Map<String, Object> session = getSession();
        session.put(SessionConstant.MEMBER_SESSION, member);
        setSession(session);
        // 设置需要跳转的页面
        redirectUrl = (String) getSession().get(SessionConstant.REDIRECT_URL);
        // 返回页面
        return StringUtils.isBlank(redirectUrl) ? HOME : URL;
    }

    // result
    private Boolean loginResult = false;
    

    public String doLoginByJquery() {
        boolean isLogin = loginAO.isLogin(loginId, password);
        
        if (isLogin) {
            loginResult=true;
        }
        // 做登录操作
        Member member = loginAO.getLoginedMember(loginId);
        Map<String, Object> session = getSession();
        session.put(SessionConstant.MEMBER_SESSION, member);
        setSession(session);
        
        
        return SUCCESS;
    }
    
    
 

    /***************************************************************************
     * * 登出动作
     **************************************************************************/
    public String doLogout() {
        Map<String, Object> session = getSession();
        session.remove(SessionConstant.MEMBER_SESSION);
        session.remove(SessionConstant.REDIRECT_URL);
        session.remove(SessionConstant.CART_SESSION);
        session.remove(SessionConstant.CODE_IMAGE_SESSION);
        setSession(session);
        return SUCCESS;
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

    public void setLoginAO(LoginAO loginAO) {
        this.loginAO = loginAO;
    }

    public Boolean getLoginResult() {
        return loginResult;
    }

    public void setLoginResult(Boolean loginResult) {
        this.loginResult = loginResult;
    }


}

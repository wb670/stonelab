package cn.zeroall.cow.web.common.interceptors;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import cn.zeroall.cow.web.common.constants.SessionConstant;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> May 11, 2009
 */
public abstract class BaseAuthInterceptor extends AbstractInterceptor {

    private static final long serialVersionUID = 2756750948662780128L;

    private static final String DEFAULT_ENCODING = "UTF-8";
    private static final String LOGIN_URL = "login";

    @Override
    public String intercept(ActionInvocation invocation) throws Exception {
        // 得到authSession
        Object authSession = getAuthSession();
        // 判断权限
        if (isAuth(authSession)) {
            return invocation.invoke();
        } else {
            // 设置当前url
            getSession().put(SessionConstant.REDIRECT_URL, getUrl());
            return LOGIN_URL;
        }
    }

    /**
     * 得到session对象
     * 
     * @return
     */
    protected Map<String, Object> getSession() {
        return ActionContext.getContext().getSession();
    }

    /**
     * 得到session中的auth对象
     * 
     * @return
     */
    protected abstract Object getAuthSession();

    /**
     * 判断auth对象是否授权
     * 
     * @param authSession
     * @return
     */
    protected abstract boolean isAuth(Object authSession);

    /*
     * 得到当前的url
     */
    private String getUrl() {
        HttpServletRequest req = getHttpServletRequest();
        String url;
        String queryStr = getQueryStr();
        if (StringUtils.isBlank(queryStr)) {
            url = req.getRequestURL().toString();
        } else {
            url = req.getRequestURL().toString() + "?" + queryStr;
        }
        return url;
    }

    /*
     * FIXME:多维数组的实现存在BUG
     */
    private String getQueryStr() {
        Map<String, Object> map = ActionContext.getContext().getParameters();
        if (map.isEmpty()) {
            return null;
        }
        String query = "";
        int count = 0;

        Set<Entry<String, Object>> set = map.entrySet();
        for (Entry<String, Object> entry : set) {
            String key = entry.getKey();
            String[] values = (String[]) entry.getValue();
            String value;
            try {
                value = URLEncoder.encode(values[0], DEFAULT_ENCODING);
            } catch (UnsupportedEncodingException e) {
                value = values[0];
            }
            if (count == 0) {
                query += (key + "=" + value);
            } else {
                query += ("&" + key + "=" + value);
            }
            count++;
        }
        return query;
    }

    private HttpServletRequest getHttpServletRequest() {
        return ServletActionContext.getRequest();
    }

}

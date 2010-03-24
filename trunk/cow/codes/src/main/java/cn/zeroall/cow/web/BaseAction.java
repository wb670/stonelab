package cn.zeroall.cow.web;

import java.util.HashMap;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Feb 16, 2009
 */
public abstract class BaseAction extends ActionSupport {

    private static final long serialVersionUID = 7859829371160910291L;

    public static final String TARGET = "target";

    protected String target;

    protected Map<String, Object> getSession() {
        Map<String, Object> session = ActionContext.getContext().getSession();
        if (session == null) {
            session = new HashMap<String, Object>();
        }
        return session;
    }

    protected void setSession(Map<String, Object> session) {
        ActionContext.getContext().setSession(session);
    }

    protected String render(String target) {
        setTarget(target);
        return TARGET;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

}

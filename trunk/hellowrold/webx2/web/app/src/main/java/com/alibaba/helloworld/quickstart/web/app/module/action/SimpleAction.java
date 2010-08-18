package com.alibaba.helloworld.quickstart.web.app.module.action;

import java.io.IOException;

import com.alibaba.service.form.Form;
import com.alibaba.service.form.FormService;
import com.alibaba.service.form.Group;
import com.alibaba.service.template.TemplateContext;
import com.alibaba.service.uribroker.URIBrokerService;
import com.alibaba.turbine.module.action.TemplateAction;
import com.alibaba.turbine.service.rundata.RunData;
import com.alibaba.turbine.service.uribroker.uri.TurbineURIBroker;
import com.alibaba.webx.WebxException;
import com.alibaba.helloworld.webx2.biz.app.App;

public abstract class SimpleAction extends TemplateAction {

    protected abstract FormService getFormService();

    protected abstract URIBrokerService getURIBrokerService();

    private App app;

    public void doGreeting(RunData rundata, TemplateContext context) throws WebxException {
        Form form = getFormService().getForm(rundata);

        if (form.isValid()) {
            Group group = form.getGroup("simple");
            String name = group.getField("name").getStringValue();

            TurbineURIBroker uri = (TurbineURIBroker) getURIBrokerService().getURIBroker("appModule", rundata);
            uri.setTarget("hello.vm").addQueryData("name", app.getPalindrome(name));

            try {
                rundata.setRedirectLocation(uri.render());
            } catch (IOException e) {
                throw new WebxException(e);
            }
        }
    }

    public void setApp(App app) {
        this.app = app;
    }

}

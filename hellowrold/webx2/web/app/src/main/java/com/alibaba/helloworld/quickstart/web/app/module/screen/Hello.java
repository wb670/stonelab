package com.alibaba.helloworld.quickstart.web.app.module.screen;

import com.alibaba.service.template.TemplateContext;
import com.alibaba.turbine.module.screen.TemplateScreen;
import com.alibaba.turbine.service.rundata.RunData;
import com.alibaba.webx.WebxException;
import com.alibaba.helloworld.webx2.biz.app.App;

public class Hello extends TemplateScreen {

    private App app;

    @Override
    protected void execute(RunData rundata, TemplateContext context) throws WebxException {
        String name = rundata.getParameters().getString("name");
        context.put("name", app.getPalindrome(name));
    }

    public void setApp(App app) {
        this.app = app;
    }

}

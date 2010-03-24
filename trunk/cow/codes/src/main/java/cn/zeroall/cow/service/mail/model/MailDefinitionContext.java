package cn.zeroall.cow.service.mail.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Jul 2, 2009
 */
public class MailDefinitionContext {

    private String baseMailTemplate;
    private List<MailDefinition> mailDefinitions = new ArrayList<MailDefinition>();

    public String getBaseMailTemplate() {
        return baseMailTemplate;
    }

    public void setBaseMailTemplate(String baseMailTemplate) {
        this.baseMailTemplate = baseMailTemplate;
    }

    public List<MailDefinition> getMailDefinitions() {
        return mailDefinitions;
    }

    public void setMailDefinitions(List<MailDefinition> mailDefinitions) {
        this.mailDefinitions = mailDefinitions;
    }

}

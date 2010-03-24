package cn.zeroall.cow.service.mail.impl;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import cn.zeroall.cow.service.mail.exception.MailException;
import cn.zeroall.cow.service.mail.model.MailDefinition;
import cn.zeroall.cow.service.mail.model.MailDefinitionContext;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Jul 2, 2009
 */
public class MailDefinitionParser {

    public MailDefinitionContext parser(InputStream in) {
        if (in == null) {
            return null;
        }
        MailDefinitionContext ctx = new MailDefinitionContext();

        try {
            SAXReader reader = new SAXReader();
            Document doc = reader.read(in);
            Element root = doc.getRootElement();
            Element baseMailTemplateNode = root.element("baseMailTemplate");
            ctx.setBaseMailTemplate(baseMailTemplateNode.getText());

            List<Element> mailElements = root.element("mails").elements();
            for (Element e : mailElements) {
                MailDefinition mailDef = new MailDefinition();
                mailDef.setKey(e.attributeValue("key"));
                mailDef.setEncoding(e.attributeValue("encoding"));
                mailDef.setFrom(e.elementText("from"));
                mailDef.setSubject(e.elementText("subject"));
                mailDef.setTemplate(e.elementText("template"));
                List<Element> toElements = e.element("toList").elements();
                for (Element toE : toElements) {
                    mailDef.addTo(toE.getText());
                }
                ctx.getMailDefinitions().add(mailDef);
            }
        } catch (Exception e) {
            throw new MailException("mail definiton parser fail.", e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
        }
        return ctx;
    }

    public static void main(String[] args) throws Exception {
        String mail = "/home/maurice/workspace/dev/projects/cow/codes/src/main/resources/biz/mail.xml";
        MailDefinitionParser parser = new MailDefinitionParser();
        MailDefinitionContext ctx = parser.parser(new FileInputStream(mail));
        System.out.println(ctx.getBaseMailTemplate());
        List<MailDefinition> mailDefinitions = ctx.getMailDefinitions();
        for (MailDefinition def : mailDefinitions) {
            System.out.println(def);
        }
    }
}

package cn.zeroall.cow.service.mail;

import java.util.HashMap;
import java.util.Map;

import cn.zeroall.cow.BaseTestCase;
import cn.zeroall.cow.service.mail.impl.DefaultMailService;
import cn.zeroall.cow.service.mail.model.MailParam;
import cn.zeroall.cow.service.template.impl.VelocityTemplateService;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Jul 2, 2009
 */
public class DefaultMailServiceTest extends BaseTestCase {

    private static final String PROTOCOL = "smtp";
    private static final String SMTP_SERVER = "smtp.gmail.com";
    private static final String SMTP_PORT = "465";
    private static final String USER_NAME = "jones2083";
    private static final String PASSWORD = "hello123";

    private static final String MAIL_CONF = "classpath:biz/mail.xml";

    private DefaultMailService mailService = new DefaultMailService();

    @Override
    protected void setUp() throws Exception {
        mailService.setProtocol(PROTOCOL);
        mailService.setHost(SMTP_SERVER);
        mailService.setPort(SMTP_PORT);
        mailService.setUser(USER_NAME);
        mailService.setPassword(PASSWORD);
        mailService.setAuth("true");
        mailService.setSsl("true");
        mailService.setMailConf(MAIL_CONF);
        mailService.setTemplateService(new VelocityTemplateService());
        mailService.afterPropertiesSet();
    }

    public void testSend() {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("name", "stone");
        MailParam param = new MailParam(model);
        
        mailService.send("test", param);
    }
}

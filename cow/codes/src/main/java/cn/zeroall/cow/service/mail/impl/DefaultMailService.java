package cn.zeroall.cow.service.mail.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.Session;
import javax.net.ssl.SSLSocketFactory;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.SimpleEmail;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.ResourceUtils;

import cn.zeroall.cow.service.mail.MailService;
import cn.zeroall.cow.service.mail.exception.MailException;
import cn.zeroall.cow.service.mail.model.MailDefinition;
import cn.zeroall.cow.service.mail.model.MailDefinitionContext;
import cn.zeroall.cow.service.mail.model.MailParam;
import cn.zeroall.cow.service.template.TemplateService;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Jul 1, 2009
 */
public class DefaultMailService implements MailService, InitializingBean {

    private static final String TRUE = "true";

    private String protocol;
    private String host;
    private String port;
    private String user;
    private String password;
    private String auth = TRUE;
    private String ssl = TRUE;

    private Session session;

    private String mailConf;

    private MailDefinitionContext defCtx;
    private Map<String, MailDefinition> cache = new HashMap<String, MailDefinition>();
    private MailDefinitionParser parser = new MailDefinitionParser();

    private TemplateService templateService;

    // @Override
    // public void afterPropertiesSet() throws Exception {
    // // mail def
    // File file = ResourceUtils.getFile(mailConf);
    // defCtx = parser.parser(new FileInputStream(file));
    // for (MailDefinition def : defCtx.getMailDefinitions()) {
    // cache.put(def.getKey(), def);
    // }
    // // mail session
    // Properties properties = new Properties();
    // properties.put("mail.transport.protocol", protocol);
    // properties.put("mail.smtp.host", host);
    // properties.put("mail.smtp.port", port);
    // properties.put("mail.smtp.user", user);
    // properties.put("mail.smtp.password", password);
    // properties.put("mail.smtp.auth", auth);
    // if (isTrue(ssl)) {
    // properties.setProperty("mail.smtp.socketFactory.class",
    // SSLSocketFactory.class.getName());
    // }
    // session = Session.getDefaultInstance(properties);
    // }

    @Override
    public void afterPropertiesSet() throws Exception {

        Properties properties = new Properties();
        properties.put("mail.transport.protocol", protocol);
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.user", user);
        properties.put("mail.smtp.password", password);
        properties.put("mail.smtp.auth", auth);
        if (isTrue(ssl)) {
            properties.setProperty("mail.smtp.socketFactory.class", SSLSocketFactory.class
                    .getName());
        }
        session = Session.getDefaultInstance(properties); 
    }

    @Override
    public void send(String key, MailParam param) throws MailException {
        if (StringUtils.isBlank(key) || param == null) {
            throw new IllegalArgumentException("param is null.");
        }
        try {
            getEmail(key, param).send();
        } catch (EmailException e) {
            throw new MailException("send mail fail.", e);
        }
    }

    @Override
    public void send(MailDefinition def) throws MailException {
        if (def == null) {
            throw new IllegalArgumentException("param is null.");
        }
        try {
            getEmail(def).send();
        } catch (EmailException e) {
            throw new MailException("send mail fail.", e);
        }
    }

    private Email getEmail(String key, MailParam param) throws MailException {
        MailDefinition def = cache.get(key);
        if (def == null) {
            throw new MailException("mail definiton not found.");
        }
        Email email = def.isHtml() ? new HtmlEmail() : new SimpleEmail();
        email.setMailSession(session);
        try {
            // to
            if (CollectionUtils.isNotEmpty(param.getToList())) {
                for (String to : param.getToList()) {
                    email.addTo(to);
                }
            } else {
                for (String to : def.getToList()) {
                    email.addTo(to);
                }
            }
            // character
            email.setCharset(def.getEncoding());
            // from
            email.setFrom(def.getFrom());
            email.setSubject(def.getSubject());
            // template
            if (email instanceof HtmlEmail) {
                ((HtmlEmail) email).setHtmlMsg(getMessage(def.getTemplate(), def.getEncoding(),
                        param.getModel()));
            } else {
                email.setMsg(getMessage(def.getTemplate(), def.getEncoding(), param.getModel()));
            }
        } catch (EmailException e) {
            throw new MailException("mail param is illegal.", e);
        }

        return email;
    }

    private Email getEmail(MailDefinition def) throws MailException {

        Email email = new HtmlEmail();
        email.setMailSession(session);
        try {
            if (def == null)
                throw new MailException("def is null");
            else {

                for (String to : def.getToList()) {
                    email.addTo(to);
                }
                // character
                email.setCharset(def.getEncoding());
                // from
                email.setFrom(def.getFrom());
                email.setSubject(def.getSubject());
                ((HtmlEmail) email).setHtmlMsg(def.getContent());
            }

        } catch (EmailException e) {
            throw new MailException("def in error", e);
        }

        return email;
    }

    private String getMessage(String file, String encoding, Map<String, Object> model) {
         String location = defCtx.getBaseMailTemplate() + file;
//        String location = "E:\\trunk\\codes\\src\\main\\webapp\\templates\\mail\\mail.vm";
        String message = "FAIL";
        InputStreamReader reader = null;
        try {
            reader = new InputStreamReader(new FileInputStream(location));
            message = templateService.merge(IOUtils.toString(reader), model);
        } catch (Exception e) {
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                }
            }
        }
        System.out.println(message);
        return message;
    }

    private boolean isTrue(String isTrue) {
        return StringUtils.equals(TRUE, isTrue);
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String isAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public String isSsl() {
        return ssl;
    }

    public void setSsl(String ssl) {
        this.ssl = ssl;
    }

    public void setMailConf(String mailConf) {
        this.mailConf = mailConf;
    }

    public void setTemplateService(TemplateService templateService) {
        this.templateService = templateService;
    }

}

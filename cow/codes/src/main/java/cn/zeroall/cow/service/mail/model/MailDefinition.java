package cn.zeroall.cow.service.mail.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Jul 1, 2009
 */
public class MailDefinition {

    private static final boolean DEFAULT_HTML = true;
    private static final String DEFAULT_ENCODING = "UTF-8";

    private String key;// key
    private String from;// 发送者
    private List<String> toList = new ArrayList<String>();// 收件人列表
    private String subject;// 邮件标题
    private String template;// 邮件内容模板
    private boolean isHtml = DEFAULT_HTML;// 是否支持html格式
    private String encoding = DEFAULT_ENCODING;
    private String content;// 邮件主体
    
    
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void addTo(String to) {
        toList.add(to);
    }

    public List<String> getToList() {
        return toList;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public boolean isHtml() {
        return isHtml;
    }

    public void setHtml(boolean isHtml) {
        this.isHtml = isHtml;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        if (StringUtils.isNotBlank(encoding)) {
            this.encoding = encoding;
        }
    }

    @Override
    public String toString() {
        return new ReflectionToStringBuilder(this).toString();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}

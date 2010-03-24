package cn.zeroall.cow.service.template.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;
import org.springframework.beans.factory.InitializingBean;

import cn.zeroall.cow.service.template.TemplateService;

/**
 * 
 * @author <a href="mailto:stone2083@yahoo.cn">Maurice.J</a> Jul 2, 2009
 */
public class VelocityTemplateService implements TemplateService, InitializingBean {

    private static final Log log = LogFactory.getLog(VelocityTemplateService.class);

    private static final String DEFAULT_ENCODING = "UTF-8";

    private String encoding = DEFAULT_ENCODING;

    private VelocityEngine velocityEngine = new VelocityEngine();

    @Override
    public void afterPropertiesSet() throws Exception {
        try {
            velocityEngine.init();
        } catch (Exception e) {
            log.error("init velocityEngine fail.", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public String merge(String content, Map<String, Object> model) {
        StringWriter writer = new StringWriter();
        Context context = new VelocityContext(model);
        try {
            velocityEngine.evaluate(context, writer, "", content);
        } catch (Exception e) {
            log.error("merge fail.", e);
        }
        return writer.toString();
    }

    @Override
    public String merge(File file, Map<String, Object> model) {
        String result = null;
        try {
            result = merge(new FileInputStream(file), model);
        } catch (FileNotFoundException e) {
            log.error("merge fail.", e);
        }
        return result;
    }

    @Override
    public String merge(InputStream input, Map<String, Object> model) {
        String result = null;
        try {
            String content = IOUtils.toString(input, encoding);
            result = merge(content, model);
        } catch (IOException e) {
            log.error("merge fail.", e);
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                }
            }
        }
        return result;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

}

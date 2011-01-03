/*
 * Copyright 1999-2010 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.alibaba.stonelab.webxsample.sample.web.common.valve;

import static com.alibaba.citrus.turbine.util.TurbineUtil.getTurbineRunData;

import java.io.IOException;
import java.text.MessageFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.pipeline.PipelineContext;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.TurbineRunData;
import com.alibaba.citrus.turbine.TurbineRunDataInternal;
import com.alibaba.citrus.turbine.pipeline.valve.PerformScreenValve;
import com.alibaba.citrus.webx.WebxException;

/**
 * <pre>
 * Webx֧��Json�����
 * Ŀǰ֧�����������ʽ:
 * 1. 
 *  http://host:ip/resource?var=jsonVar => jsonVar={"id":"1","name":"name"}
 *  contentType: text/html
 * 
 * 2.
 *  http://host:ip/resource?callback=jsonCallback => jsonCallback({"id":"1","name":"name"})
 *  contentType: text/html
 *  
 * 3.
 *  http://host:ip/resource => {"id":"1","name":"name"}
 *  contentType: text/json
 *  
 * ʹ�÷�ʽ:
 *  1.pipeline.xml������
 *  <when>
 *      <pl-conditions:target-extension-condition extension="json" />
 *      <pl-valves:valve name="performJson" class="com.alibaba.stonelab.webxsample.sample.web.common.valve.PerformJsonValve" />
 *  </when>
 *  ���κ�*.json����,ʹ��PerformJsonVavleִ��
 *  
 *  2.��дһ��Screen Module:
 *  public class Json {
 *    public void execute(@Param("id") String id, @Param("name") String name, Context context) {
 *      Map<String, String> json = new HashMap<String, String>();
 *      json.put("id", id);
 *      json.put("name", name);
 *      context.put(PerformJsonValve.KEY_JSON_DATA, json);
 *    }
 *  }
 *  
 *  3.url����
 *  http://$host:$ip/$component/json.json?var=json&name=test
 *  http://$host:$ip/$component/json.json?callback=json&name=test
 *  http://$host:$ip/$component/json.json?name=test
 * 
 * 
 * TODO:Ŀǰ֧�ֱȽϼ�:
 * 
 *      1.���ڲ�����������,��û����json��ʽ�İ�ȫ����
 *      ����:ʵ��һ��SecurityPerfomJsonValve,��дprotected��������
 *      
 *      2.json�����л�ֻ֧��Jsonlib,����Ч�ʱȽϵ�,�ڸ�����Ҫ��ĳ�����,��Ҫ֧��Module�Զ����ʽ��
 * </pre>
 * 
 * @author Stone.J 2010-8-17 ����11:42:59
 */
public class PerformJsonValve extends PerformScreenValve {

    // TODO:����鵽һ��JSON��������,��Ҫ��Json Screen����
    public static final String  PARAM_VAR                = "var";        // ����:var
    public static final String  PARAM_CALLBACK           = "callback";   // ����:callback
    public static final String  KEY_JSON_DATA            = "__json__";   // json����key

    private static final String OUTPUT_TEMPLATE_VAR      = "var {0}={1}"; // var��ʽ��,���ģ��
    private static final String OUTPUT_TEMPLATE_CALLBACK = "{0}({1})";   // callback��ʽ��,���ģ��

    @Autowired
    private HttpServletRequest  request;                                 // ע�� http servlet request����
    @Autowired
    private HttpServletResponse response;                                // ע��http servlet response����

    @Override
    public void invoke(PipelineContext pipelineContext) throws Exception {
        // �õ�rundata����
        TurbineRunData rundata = getTurbineRunData(request);

        // ����ض����־��������ض�������Ҫ��ҳ�������
        if (!rundata.isRedirected()) {
            // ����http response content type
            setContentType(rundata);
            // ִ��Json Screen Module
            performScreenModule(rundata);
            // json�������
            output(rundata);
        }

        // ִ����һ��valve
        pipelineContext.invokeNext();
    }

    /**
     * <pre>
     * ����content type.
     * 
     * var��ʽ��callback��ʽ��,contenxt typeΪtext/html
     * json��ʽ��,contenxt typeΪtext/json
     * </pre>
     * 
     * @param rundata
     */
    protected void setContentType(TurbineRunData rundata) {
        JsonStyle style = getJsonStyle(rundata);
        if (style.equals(JsonStyle.JSON)) {
            rundata.getResponse().setContentType("text/json");
        } else {
            rundata.getResponse().setContentType("text/html");
        }
    }

    /**
     * <pre>
     * �����http outputstream��.
     * </pre>
     * 
     * @param rundata
     */
    protected void output(TurbineRunData rundata) {
        try {
            response.getWriter().println(outputFomat(rundata));
            response.getWriter().close();
        } catch (IOException e) {
            throw new WebxException("Failed to ouput json: ", e);
        }
    }

    /**
     * <pre>
     * ��ʽ��Json�������.
     * 
     * 1. 
     *  var��ʽ => jsonVar={"id":"1","name":"name"}
     * 2.
     *  callback��ʽ => jsonCallback({"id":"1","name":"name"})
     * 3.
     *  json��ʽ => {"id":"1","name":"name"}
     *  
     *  TODO:ע������İ�ȫУ��
     * </pre>
     * 
     * @param rundata
     * @return �õ�Json�������
     */
    protected String outputFomat(TurbineRunData rundata) {
        JsonStyle style = getJsonStyle(rundata);
        String output;
        if (style.equals(JsonStyle.JSON_VAR)) {
            String var = rundata.getParameters().getString(PARAM_VAR);
            output = MessageFormat.format(OUTPUT_TEMPLATE_VAR, var, jsonFormat(rundata));
        } else if (style.equals(JsonStyle.JSON_CALLBACK)) {
            String callback = rundata.getParameters().getString(PARAM_CALLBACK);
            output = MessageFormat.format(OUTPUT_TEMPLATE_CALLBACK, callback, jsonFormat(rundata));
        } else {
            output = jsonFormat(rundata);
        }
        return output;
    }

    /**
     * <pre>
     * Json���ݸ�ʽ��.
     * Ŀǰʹ����Jsonlib,ͨ���������json���л�
     * </pre>
     * 
     * @param rundata
     * @return Json���л����ַ���
     */
    protected String jsonFormat(TurbineRunData rundata) {
        Context ctx = ((TurbineRunDataInternal) rundata).getContext();
        Object data = ctx.get(KEY_JSON_DATA);
        return JSONObject.fromObject(data).toString();
    }

    /**
     * <pre>
     * ͨ������,�õ�Json��ʽ
     * </pre>
     * 
     * @see JsonStyle
     * @param rundata
     * @return Json��ʽ
     */
    protected JsonStyle getJsonStyle(TurbineRunData rundata) {
        String var = rundata.getParameters().getString(PARAM_VAR);
        String callback = rundata.getParameters().getString(PARAM_CALLBACK);
        if (StringUtils.isNotBlank(var)) {
            return JsonStyle.JSON_VAR;
        } else if (StringUtils.isNotBlank(callback)) {
            return JsonStyle.JSON_CALLBACK;
        } else {
            return JsonStyle.JSON;
        }
    }

    /**
     * <pre>
     * JSON��ʽ.
     * ����:
     *  1.JSON JSON��ʽ
     *  2.JSON_VAR ��������JSON��ʽ
     *  3.JSON_CALLBACK ���ص���JSON��ʽ
     * </pre>
     * 
     * @author Stone.J 2010-8-17 ����11:48:16
     */
    public static enum JsonStyle {
        /** JSON��ʽ */
        JSON,
        /** ��������JSON��ʽ */
        JSON_VAR,
        /** ���ص���JSON��ʽ */
        JSON_CALLBACK;
    }

}

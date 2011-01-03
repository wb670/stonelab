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
 * Webx支持Json的输出
 * 目前支持三种输出样式:
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
 * 使用方式:
 *  1.pipeline.xml中配置
 *  <when>
 *      <pl-conditions:target-extension-condition extension="json" />
 *      <pl-valves:valve name="performJson" class="com.alibaba.stonelab.webxsample.sample.web.common.valve.PerformJsonValve" />
 *  </when>
 *  将任何*.json请求,使用PerformJsonVavle执行
 *  
 *  2.编写一个Screen Module:
 *  public class Json {
 *    public void execute(@Param("id") String id, @Param("name") String name, Context context) {
 *      Map<String, String> json = new HashMap<String, String>();
 *      json.put("id", id);
 *      json.put("name", name);
 *      context.put(PerformJsonValve.KEY_JSON_DATA, json);
 *    }
 *  }
 *  
 *  3.url请求
 *  http://$host:$ip/$component/json.json?var=json&name=test
 *  http://$host:$ip/$component/json.json?callback=json&name=test
 *  http://$host:$ip/$component/json.json?name=test
 * 
 * 
 * TODO:目前支持比较简单:
 * 
 *      1.对于参数和输出结果,均没有做json格式的安全检验
 *      建议:实现一个SecurityPerfomJsonValve,复写protected方法即可
 *      
 *      2.json的序列化只支持Jsonlib,反射效率比较低,在高性能要求的场景下,需要支持Module自定义格式化
 * </pre>
 * 
 * @author Stone.J 2010-8-17 上午11:42:59
 */
public class PerformJsonValve extends PerformScreenValve {

    // TODO:建议抽到一个JSON常量类中,需要被Json Screen依赖
    public static final String  PARAM_VAR                = "var";        // 参数:var
    public static final String  PARAM_CALLBACK           = "callback";   // 参数:callback
    public static final String  KEY_JSON_DATA            = "__json__";   // json数据key

    private static final String OUTPUT_TEMPLATE_VAR      = "var {0}={1}"; // var样式下,输出模板
    private static final String OUTPUT_TEMPLATE_CALLBACK = "{0}({1})";   // callback样式下,输出模板

    @Autowired
    private HttpServletRequest  request;                                 // 注入 http servlet request对象
    @Autowired
    private HttpServletResponse response;                                // 注入http servlet response对象

    @Override
    public void invoke(PipelineContext pipelineContext) throws Exception {
        // 得到rundata对象
        TurbineRunData rundata = getTurbineRunData(request);

        // 检查重定向标志，如果是重定向，则不需要将页面输出。
        if (!rundata.isRedirected()) {
            // 设置http response content type
            setContentType(rundata);
            // 执行Json Screen Module
            performScreenModule(rundata);
            // json内容输出
            output(rundata);
        }

        // 执行下一个valve
        pipelineContext.invokeNext();
    }

    /**
     * <pre>
     * 设置content type.
     * 
     * var样式和callback样式下,contenxt type为text/html
     * json样式下,contenxt type为text/json
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
     * 输出到http outputstream中.
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
     * 格式化Json输出内容.
     * 
     * 1. 
     *  var样式 => jsonVar={"id":"1","name":"name"}
     * 2.
     *  callback样式 => jsonCallback({"id":"1","name":"name"})
     * 3.
     *  json样式 => {"id":"1","name":"name"}
     *  
     *  TODO:注意参数的安全校验
     * </pre>
     * 
     * @param rundata
     * @return 得到Json输出内容
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
     * Json内容格式化.
     * 目前使用了Jsonlib,通过反射进行json序列化
     * </pre>
     * 
     * @param rundata
     * @return Json序列化后字符串
     */
    protected String jsonFormat(TurbineRunData rundata) {
        Context ctx = ((TurbineRunDataInternal) rundata).getContext();
        Object data = ctx.get(KEY_JSON_DATA);
        return JSONObject.fromObject(data).toString();
    }

    /**
     * <pre>
     * 通过参数,得到Json样式
     * </pre>
     * 
     * @see JsonStyle
     * @param rundata
     * @return Json样式
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
     * JSON格式.
     * 包含:
     *  1.JSON JSON样式
     *  2.JSON_VAR 带变量的JSON样式
     *  3.JSON_CALLBACK 带回调的JSON样式
     * </pre>
     * 
     * @author Stone.J 2010-8-17 上午11:48:16
     */
    public static enum JsonStyle {
        /** JSON样式 */
        JSON,
        /** 带变量的JSON样式 */
        JSON_VAR,
        /** 带回调的JSON样式 */
        JSON_CALLBACK;
    }

}

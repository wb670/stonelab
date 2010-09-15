/*
 * Copyright 1999-2004 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.alibaba.stonelab.webxsample.sample.web.common.dataresolver;

import static com.alibaba.citrus.util.Assert.assertNotNull;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.dataresolver.DataResolver;
import com.alibaba.citrus.service.dataresolver.DataResolverContext;
import com.alibaba.citrus.service.dataresolver.DataResolverFactory;

/**
 * <pre>
 * Json���ݽ���������,���ڴ��� JsonResolver
 * ��http���������,��json���ݽ����ɶ�Ӧ�Ķ���
 * 
 * ����
 * url: 
 *  http://localhost:8081/json_in.htm?json={id=1,name="2"}&json1={id=3,name="4"}
 * 
 * Module����ͨ��@JSONע��ʹ��:
 *  public void execute(@Json(name = "json") JsonBean jsonBean, 
 *                      @Json(name = "json1") JsonBean jsonBean2,
 *                         Context context) {}
 * 
 * </pre>
 * 
 * @author Stone.J 2010-9-15 ����05:49:47
 */
public class JsonResolverFactory implements DataResolverFactory {

    @Autowired
    private HttpServletRequest request;

    @Override
    public DataResolver getDataResolver(DataResolverContext context) {
        Json json = context.getAnnotation(Json.class);
        if (json == null) {
            return null;
        }
        return new JsonResolver(context);
    }

    /**
     * <pre>
     * Json���ݽ�����
     * ����Jsonlib���н�������Ӧ������
     * </pre>
     * 
     * @author Stone.J 2010-9-15 ����08:38:51
     */
    public class JsonResolver implements DataResolver {

        private final DataResolverContext context;

        public JsonResolver(DataResolverContext context){
            this.context = assertNotNull(context, "data resolver context");
        }

        @Override
        public Object resolve() {
            Json json = context.getAnnotation(Json.class);
            if (json == null) {
                return null;
            }
            String jstr = request.getParameter(json.name());
            JSONObject jobj = JSONObject.fromObject(jstr);
            return JSONObject.toBean(jobj, context.getTypeInfo().getRawType());
        }

    }

}

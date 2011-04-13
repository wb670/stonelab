/*
 * Copyright 1999-2011 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.alibaba.stonelab.javalab.misc;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.CallbackFilter;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * @author <a href="mailto:li.jinl@alibaba-inc.com">Stone.J</a> 2011-1-26
 */
public class Misc {

    public static void main(String[] args) throws Exception {
        Model model = ModelFactory.create(Model.class);
        System.out.println(model.getName());
        System.out.println(model.getName());
        System.out.println(model.getName());
    }

    public static class ModelFactory {

        public static <T> T create(Class<T> modelClass) {
            // ....
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(modelClass);
            enhancer.setCallbacks(new Callback[] { new LazyloadInterceptor() });
            enhancer.setCallbackFilter(new LazyloadFilter());
            return (T) enhancer.create();
        }

    }

    public static class LazyloadInterceptor implements MethodInterceptor {

        @Override
        public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
            if (!method.getName().startsWith("get")) {
                return proxy.invokeSuper(obj, args);
            }
            Object ret = proxy.invokeSuper(obj, args);
            if (ret == null) {
                String fieldName = method.getName().substring(method.getName().indexOf("get") + 3);
                Method set = obj.getClass().getMethod("set" + fieldName, String.class);
                ret = "lazy load";
                set.invoke(obj, ret);
            }
            return ret;

        }
    }

    public static class LazyloadFilter implements CallbackFilter {

        @Override
        public int accept(Method method) {
            return 0;
        }

    }

    public static class Model {

        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Model [name=" + name + "]";
        }

    }

}

/*
 * Copyright 1999-2010 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.alibaba.stonelab.springmvc.web;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.alibaba.stonelab.springmvc.common.JsonView;

/**
 * @author <a href="mailto:li.jinl@alibaba-inc.com">Stone.J</a> 2010-12-13
 */
@Controller
public class HelloController extends MultiActionController {

    @RequestMapping
    public ModelAndView index(HttpServletRequest req, HttpServletResponse res) {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("name", "Stone.J");
        model.put("user", new User());
        return new ModelAndView("hello/index", model);
    }

    @RequestMapping
    public ModelAndView update(HttpServletRequest req, HttpServletResponse res, HttpSession session, User user)
                                                                                                               throws Exception {

        BindException errors = new BindException(user, "user");
        ValidationUtils.invokeValidator(UserValidator.USER_VALIDATOR, user, errors.getBindingResult());
        if (errors.hasErrors()) {
            return index(req, res).addAllObjects(errors.getModel());
        }

        Map<String, Object> ctx = new HashMap<String, Object>();
        ctx.put("user", user);
        return new ModelAndView("hello/update", ctx);
    }

    @RequestMapping
    public ModelAndView redirect(HttpServletRequest req, HttpServletResponse res) throws Exception {
        res.sendRedirect("https://www.cn.alibaba-inc.com");
        return null;
    }

    @RequestMapping
    public ModelAndView json(HttpServletRequest req, HttpServletResponse res) {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("name", "stone.j");
        model.put("msg", "hello");
        return new ModelAndView(JsonView.instance, model);
    }

    public static final class User implements Serializable {

        private static final long serialVersionUID = 1938685877118723740L;

        private String            name;
        private String            info;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        @Override
        public String toString() {
            return "User [name=" + name + ", info=" + info + "]";
        }

    }

    public static class UserValidator implements Validator {

        public static final UserValidator USER_VALIDATOR = new UserValidator();

        @Override
        public boolean supports(Class clazz) {
            return clazz.isAssignableFrom(User.class);
        }

        @Override
        public void validate(Object target, Errors errors) {
            ValidationUtils.rejectIfEmpty(errors, "name", "empty");
            ValidationUtils.rejectIfEmpty(errors, "info", "empty");
        }
    }
}

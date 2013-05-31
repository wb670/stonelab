/**
 * 
 */
package com.alibaba.stonelab.webxsample.sample.web.module.screen;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alibaba.stonelab.webxsample.sample.web.common.valve.PerformJsonValve;

/**
 * @author <a href="mailto:li.jinl@tmall.com>Stone.J</a>
 * 
 */
public class Session {

	@Autowired
	private HttpSession session;

	public void execute(@Param(name = "action") String action, Context context) {
		if ("save".equals(action)) {
			session.setAttribute("name", "stone2083");
			session.setAttribute("password", "123456");
			Map<String, String> json = new HashMap<String, String>();
			json.put("code", "Session Saved.");
			context.put(PerformJsonValve.KEY_JSON_DATA, json);
			return;
		}
		String name = (String) session.getAttribute("name");
		String password = (String) session.getAttribute("password");
		Map<String, String> json = new HashMap<String, String>();
		json.put("name", name);
		json.put("password", password);
		context.put(PerformJsonValve.KEY_JSON_DATA, json);
	}

}

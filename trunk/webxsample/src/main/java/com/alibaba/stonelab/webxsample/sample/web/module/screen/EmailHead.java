/**
 * 
 */
package com.alibaba.stonelab.webxsample.sample.web.module.screen;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.stonelab.webxsample.sample.web.common.valve.PerformJsonValve;

/**
 * @author <a href="mailto:li.jinl@tmall.com>Stone.J</a>
 * 
 */
public class EmailHead {

	@Autowired
	private HttpServletRequest request;

	public void execute(Context context) {
		System.out.println(request);
		Map<String, String> json = new HashMap<String, String>();
		json.put("screen", "EmailHead");
		json.put("email", request.getHeader("email"));
		context.put(PerformJsonValve.KEY_JSON_DATA, json);
	}

}

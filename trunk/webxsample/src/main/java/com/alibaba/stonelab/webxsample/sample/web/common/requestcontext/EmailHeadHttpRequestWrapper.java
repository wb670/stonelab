/**
 * 
 */
package com.alibaba.stonelab.webxsample.sample.web.common.requestcontext;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.citrus.service.requestcontext.RequestContext;
import com.alibaba.citrus.service.requestcontext.support.AbstractRequestWrapper;

/**
 * @author <a href="mailto:li.jinl@tmall.com>Stone.J</a>
 * 
 */
public class EmailHeadHttpRequestWrapper extends AbstractRequestWrapper {

	/**
	 * @param context
	 * @param request
	 */
	public EmailHeadHttpRequestWrapper(RequestContext context, HttpServletRequest request) {
		super(context, request);
	}

	@Override
	public String getHeader(String name) {
		if ("email".equals(name)) {
			return "li.jinl@alibaba-inc.com";
		}
		return super.getHeader(name);
	}

}

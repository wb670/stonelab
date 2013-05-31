/**
 * 
 */
package com.alibaba.stonelab.webxsample.sample.web.common.requestcontext;

import com.alibaba.citrus.service.requestcontext.RequestContext;
import com.alibaba.citrus.service.requestcontext.support.AbstractRequestContextWrapper;

/**
 * @author <a href="mailto:li.jinl@tmall.com>Stone.J</a>
 * 
 */
public class EmailHeadRequestContextImpl extends AbstractRequestContextWrapper {

	/**
	 * @param wrappedContext
	 */
	public EmailHeadRequestContextImpl(RequestContext wrappedContext) {
		super(wrappedContext);
		setRequest(new EmailHeadHttpRequestWrapper(wrappedContext, wrappedContext.getRequest()));
	}
	

}

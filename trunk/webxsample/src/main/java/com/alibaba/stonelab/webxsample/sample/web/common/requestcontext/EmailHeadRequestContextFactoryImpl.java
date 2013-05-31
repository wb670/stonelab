/**
 * 
 */
package com.alibaba.stonelab.webxsample.sample.web.common.requestcontext;

import com.alibaba.citrus.service.requestcontext.RequestContext;
import com.alibaba.citrus.service.requestcontext.support.AbstractRequestContextFactory;

/**
 * @author <a href="mailto:li.jinl@tmall.com>Stone.J</a>
 * 
 */
public class EmailHeadRequestContextFactoryImpl extends AbstractRequestContextFactory<RequestContext> {

	@Override
	public EmailHeadRequestContextImpl getRequestContextWrapper(RequestContext wrappedContext) {
		return new EmailHeadRequestContextImpl(wrappedContext);
	}

	@Override
	public String[] getFeatures() {
		return new String[] { "emailHeadRequest" };
	}

	@Override
	public FeatureOrder[] featureOrders() {
		return null;
	}

}

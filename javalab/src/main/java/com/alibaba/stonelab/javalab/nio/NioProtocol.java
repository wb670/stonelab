/**
 * 
 */
package com.alibaba.stonelab.javalab.nio;

import java.nio.ByteBuffer;

/**
 * @author stone
 * 
 */
public interface NioProtocol {

	static interface NioCallback {

		void process(NioRequest req);

	}

	static interface NioRequest {

		boolean isAsync();

		void setAsync(boolean isAsync);

		void setNioCallback(NioCallback cb);

		NioCallback getNioCallback();

		NioResponse getNioResponse();

		boolean isResponsed();

		void setResponsed(boolean responsed);

		void serialize(ByteBuffer buffer);

	}

	static interface NioResponse {

		void deserialize(ByteBuffer buffer);

	}

}

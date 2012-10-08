package com.alibaba.stonelab.javalab.nio;

import java.nio.ByteBuffer;

import com.alibaba.stonelab.javalab.nio.NioProtocol.NioCallback;
import com.alibaba.stonelab.javalab.nio.NioProtocol.NioRequest;
import com.alibaba.stonelab.javalab.nio.NioProtocol.NioResponse;

/**
 * 
 * NIO TCP client Sample.
 * 
 * @author stone
 * 
 */
public class NioClient {

	private static final String HOST = "127.0.0.1";
	private static final int PORT = 9999;

	public static void main(String[] args) throws Exception {
		NioSelectorManager manager = NioSelectorManager.getInstance();
		manager.start();

		NioConnection con = new NioConnection(HOST, PORT, manager);
		con.init();
		con.connect();

		NioCallback cb = new NioCallback() {

			@Override
			public void process(NioRequest req) {
			}
		};
		for (int i = 0; i < 10; i++) {
			PlainRequest req = new PlainRequest(String.valueOf(i));
			req.setAsync(true);
			req.setNioCallback(cb);
			con.submitRequest(req);
		}

		Thread.sleep(5000);

		con.close();
		manager.stop();
	}

	public static class PlainRequest implements NioRequest {

		private NioResponse resp = new PlainResponse();

		private boolean async;
		private NioCallback cb;
		private boolean responsed;

		private String msg;

		public PlainRequest(String msg) {
			this.msg = msg;
		}

		@Override
		public boolean isAsync() {
			return async;
		}

		@Override
		public void setAsync(boolean isAsync) {
			this.async = isAsync;
		}

		@Override
		public void setNioCallback(NioCallback cb) {
			this.cb = cb;
		}

		@Override
		public NioCallback getNioCallback() {
			return cb;
		}

		@Override
		public NioResponse getNioResponse() {
			return resp;
		}

		@Override
		public boolean isResponsed() {
			return responsed;
		}

		public void setResponsed(boolean responsed) {
			this.responsed = responsed;
		}

		@Override
		public void serialize(ByteBuffer buffer) {
			buffer.put(msg.getBytes());
		}

		public String getMsg() {
			return msg;
		}

		public void setMsg(String msg) {
			this.msg = msg;
		}

	}

	public static class PlainResponse implements NioResponse {

		private String msg;

		@Override
		public void deserialize(ByteBuffer buffer) {
			msg = new String(buffer.array(), buffer.position(), buffer.limit());
		}

		public String getMsg() {
			return msg;
		}

	}

}
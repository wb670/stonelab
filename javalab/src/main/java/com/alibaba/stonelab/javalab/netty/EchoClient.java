/**
 * 
 */
package com.alibaba.stonelab.javalab.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.BufType;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundMessageHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @author <a href="mailto:li.jinl@tmall.com>Stone.J</a>
 * 
 */
public class EchoClient {

	private String host;
	private int port;

	private Bootstrap b;
	private Channel ch;
	private String ctx;

	public static void main(String[] args) throws Exception {
		EchoClient client = new EchoClient("localhost", 8787);
		client.run();
		System.out.println(client.cmd("hello"));
		System.out.println("Done.");
		client.b.shutdown();
	}

	public EchoClient(String host, int port) {
		this.host = host;
		this.port = port;
	}

	public String cmd(String cmd) throws Exception {
		ch.write(cmd + "\r\n").sync().await();
		return ctx;
	}

	public void run() throws Exception {
		b = new Bootstrap();
		b.group(new NioEventLoopGroup());
		b.channel(NioSocketChannel.class);
		b.handler(new EchoClientInitializer(ctx));

		ch = b.connect(host, port).sync().channel();
	}

	public static class EchoClientInitializer extends ChannelInitializer<SocketChannel> {

		private static final StringDecoder DECODER = new StringDecoder();
		private static final StringEncoder ENCODER = new StringEncoder(BufType.BYTE);

		private EchoClientHandler handler;

		public EchoClientInitializer(String ctx) {
			this.handler = new EchoClientHandler(ctx);
		}

		@Override
		public void initChannel(SocketChannel ch) throws Exception {
			ChannelPipeline pipeline = ch.pipeline();

			// Add the text line codec combination first,
			pipeline.addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
			pipeline.addLast("decoder", DECODER);
			pipeline.addLast("encoder", ENCODER);

			// and then business logic.
			pipeline.addLast("handler", this.handler);
		}
	}

	public static class EchoClientHandler extends ChannelInboundMessageHandlerAdapter<String> {

		private String ctx;

		public EchoClientHandler(String ctx) {
			this.ctx = ctx;
		}

		@Override
		public void messageReceived(ChannelHandlerContext ctx, String msg) throws Exception {
			System.out.println(msg);
			this.ctx = msg;
		}

	}

}

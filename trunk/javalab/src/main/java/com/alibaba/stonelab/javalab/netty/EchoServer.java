/**
 * 
 */
package com.alibaba.stonelab.javalab.netty;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.codec.frame.LengthFieldBasedFrameDecoder;
import org.jboss.netty.handler.codec.frame.LengthFieldPrepender;

/**
 * @author <a href="mailto:li.jinl@tmall.com>Stone.J</a>
 * 
 */
public class EchoServer {
	private final int port;

	public static void main(String[] args) throws Exception {
		EchoServer echo = new EchoServer(8787);
		echo.run();
	}

	public EchoServer(int port) {
		this.port = port;
	}

	public void run() throws Exception {
		ServerBootstrap b = new ServerBootstrap(new NioServerSocketChannelFactory(Executors.newFixedThreadPool(10),

		Executors.newFixedThreadPool(10), 2));
		b.setPipelineFactory(new ChannelPipelineFactory() {

			@Override
			public ChannelPipeline getPipeline() throws Exception {
				return Channels.pipeline(new LengthFieldBasedFrameDecoder(10204, 0, 4), new EchoHandler(),
						new LengthFieldPrepender(4));
			}
		});

		b.bind(new InetSocketAddress(port));
	}

	public static class EchoHandler extends SimpleChannelHandler {

		@Override
		public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
			ChannelBuffer buf = (ChannelBuffer) e.getMessage();
			buf.readInt();
			e.getChannel().write(buf.readBytes(buf.readableBytes()));
		}

	}

}

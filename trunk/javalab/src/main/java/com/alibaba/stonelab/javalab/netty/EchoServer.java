/**
 * 
 */
package com.alibaba.stonelab.javalab.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundMessageHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.LineBasedFrameDecoder;

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
		// Configure the server.
		ServerBootstrap b = new ServerBootstrap();
		try {
			b.group(new NioEventLoopGroup(), new NioEventLoopGroup(1));
			b.channel(NioServerSocketChannel.class);
			b.option(ChannelOption.SO_BACKLOG, 100);
			b.childHandler(new EchoServerChannelInitializer());

			// Start the server.
			ChannelFuture f = b.bind(port).sync();

			// Wait until the server socket is closed.
			f.channel().closeFuture().sync();
		} finally {
			// Shut down all event loops to terminate all threads.
			b.shutdown();
		}
	}

	public static class EchoServerChannelInitializer extends ChannelInitializer<SocketChannel> {

		@Override
		protected void initChannel(SocketChannel ch) throws Exception {
			ch.pipeline().addLast(new LineBasedFrameDecoder(1024));
			ch.pipeline().addLast(new EchoServerHandler());
			ch.pipeline().addLast(new LengthFieldPrepender(4));
		}
	}

	@Sharable
	public static class EchoServerHandler extends ChannelInboundMessageHandlerAdapter<ByteBuf> {

		@Override
		public void messageReceived(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
			System.out.println(new String(msg.array()));
			ctx.nextOutboundByteBuffer().writeBytes(msg.array());
		}

		@Override
		public void endMessageReceived(ChannelHandlerContext ctx) throws Exception {
			ctx.flush();
		}

		@Override
		public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
			System.out.println("exception.");
			ctx.close();
		}
	}
}

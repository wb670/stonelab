/**
 * 
 */
package com.alibaba.stonelab.javalab.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

import com.alibaba.stonelab.javalab.nio.NioClient.PlainResponse;
import com.alibaba.stonelab.javalab.nio.NioProtocol.NioRequest;
import com.alibaba.stonelab.javalab.nio.NioProtocol.NioResponse;

/**
 * @author stone
 * 
 */
public class NioConnection {

	private static final int MAX_LENGTH = 1024;

	private String host;
	private int port;

	private SocketChannel sc;
	private SelectionKey sk;
	private NioSelectorManager manager;
	private ByteBuffer buffer = ByteBuffer.allocate(MAX_LENGTH);

	private BlockingQueue<NioRequest> sendingQueue = new LinkedBlockingDeque<NioRequest>();
	private BlockingQueue<NioRequest> pendingQueue = new LinkedBlockingDeque<NioRequest>();

	public NioConnection(String host, int port, NioSelectorManager manager) {
		this.host = host;
		this.port = port;
		this.manager = manager;
	}

	public void init() throws IOException {
		sc = SocketChannel.open();
		sc.configureBlocking(false);
		sc.socket().setTcpNoDelay(true);
	}

	public void connect() throws IOException {
		sc.connect(new InetSocketAddress(host, port));
		manager.register(this, SelectionKey.OP_CONNECT);
		sk.attach(this);
	}

	public void doConnected() throws IOException {
		if (sc.finishConnect()) {
			manager.interestOps(this, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
		}
	}

	public void close() throws IOException {
		if (sc != null & sc.isOpen()) {
			sc.socket().close();
			sc.close();
		}
		sendingQueue.clear();
		pendingQueue.clear();
	}

	public boolean isClosed() {
		return sc == null ? true : sc.isOpen();
	}

	public NioResponse submitRequest(NioRequest req) {
		sendingQueue.add(req);

		if (req.isAsync()) {
			return null;
		} else {
			synchronized (req) {
				while (true) {
					try {
						req.wait(100);
						if (req.isResponsed()) {
							return req.getNioResponse();
						}
					} catch (InterruptedException e) {
					}
				}
			}
		}
	}

	public void doSubmitRequest() throws IOException {
		if (sendingQueue.isEmpty()) {
			return;
		}
		try {
			NioRequest req = sendingQueue.take();
			pendingQueue.add(req);

			req.serialize(buffer);
			buffer.flip();
			sc.write(buffer);
			buffer.clear();
		} catch (InterruptedException e) {
		}
	}

	public void doResponse() throws IOException, InterruptedException {
		int rc = sc.read(buffer);
		System.out.print(rc + ":");
		if (rc < 0) {
			close();
			return;
		}
		buffer.flip();
		NioRequest req = pendingQueue.take();
		req.getNioResponse().deserialize(buffer);
		req.setResponsed(true);

		System.out.println("debug:" + ((PlainResponse) (req.getNioResponse())).getMsg());

		buffer.clear();

		if (req.isAsync()) {
			req.getNioCallback().process(req);
		} else {
			synchronized (req) {
				req.notifyAll();
			}
		}
	}

	public void doErrorResponse() {
		// TODO: do it later.
	}

	public SocketChannel getSocketChannel() {
		return sc;
	}

	public SelectionKey getSelectionKey() {
		return sk;
	}

	public void setSelectionKey(SelectionKey sk) {
		this.sk = sk;
	}

}
/**
 * 
 */
package com.alibaba.stonelab.javalab.nio;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Set;

/**
 * @author stone
 * 
 */
public class NioSelectorManager {

	private static final int DEFAULT_SELECT_TIMEOUT = 100;

	private Selector selector;
	private volatile boolean running;

	private static NioSelectorManager instance = new NioSelectorManager();

	public static NioSelectorManager getInstance() {
		return instance;
	}

	public void start() throws IOException {
		selector = Selector.open();
		running = true;

		new Thread() {
			public void run() {
				loop();
			};
		}.start();
	}

	public void stop() throws IOException {
		running = false;

		synchronized (this) {
			try {
				this.wait();
			} catch (InterruptedException ignore) {
			}
		}

		selector.close();
	}

	public void register(NioConnection con, int ops) throws IOException {
		SelectionKey sk = con.getSocketChannel().register(selector, ops);
		con.setSelectionKey(sk);
	}

	public void interestOps(NioConnection con, int ops) throws IOException {
		con.getSelectionKey().interestOps(ops);
	}

	protected void loop() {
		while (running) {
			doIO();
		}

		synchronized (this) {
			this.notifyAll();
		}
	}

	protected void doIO() {
		try {
			selector.select(DEFAULT_SELECT_TIMEOUT);
		} catch (IOException ignore) {
			return;
		}
		Set<SelectionKey> keys = selector.selectedKeys();
		for (SelectionKey k : keys) {
			NioConnection con = (NioConnection) k.attachment();
			try {
				if (k.isConnectable()) {
					con.doConnected();
				}
				if (k.isReadable()) {
					con.doResponse();
				}
				if (k.isWritable()) {
					con.doSubmitRequest();
				}
			} catch (Exception e) {
				con.doErrorResponse();
			}
		}
		keys.clear();
	}
}

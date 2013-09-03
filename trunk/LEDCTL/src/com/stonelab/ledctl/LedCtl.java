package com.stonelab.ledctl;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class LedCtl {

	public static final byte CTL_QUIT = 0x00;
	public static final byte CTL_ON1 = 0x01;
	public static final byte CTL_OFF1 = 0x02;
	public static final byte CTL_ON2 = 0x03;
	public static final byte CTL_OFF2 = 0x04;
	public static final byte CTL_ON3 = 0x05;
	public static final byte CTL_OFF3 = 0x06;
	public static final byte CTL_ON4 = 0x07;
	public static final byte CTL_OFF4 = 0x08;
	public static final byte CTL_ON = 0x09;
	public static final byte CTL_OFF = 0x0a;
	public static final byte CTL_SCENE = 0x0b;
	public static final byte CTL_STORE = 0x0c;

	private static final LedCtl INSTANCE = new LedCtl();

	private Socket socket;
	private OutputStream out;

	public static final LedCtl getLedCtl() {
		return INSTANCE;
	}

	public boolean isConnected() {
		return !(socket == null || !socket.isConnected());
	}

	public void connect(String gateway, String port) {
		if (isConnected()) {
			close();
		}
		try {
			socket = new Socket();
			socket.connect(new InetSocketAddress(gateway, Integer.valueOf(port)));
			out = socket.getOutputStream();
		} catch (Exception e) {
			throw new LedCtlException("connect fail.", e);
		}
	}

	public void close() {
		if (socket != null & !socket.isClosed()) {
			try {
				out.close();
				socket.close();
			} catch (IOException e) {
				throw new LedCtlException("close fail.", e);
			}
		}
	}

	public void ctl(byte cmd) {
		try {
			out.write(cmd);
			out.flush();
		} catch (Exception e) {
			throw new LedCtlException("ctl fail.", e);
		}
	}

	public static final class LedCtlException extends RuntimeException {

		private static final long serialVersionUID = 1L;

		public LedCtlException(String detailMessage, Throwable throwable) {
			super(detailMessage, throwable);
		}

		public LedCtlException(String detailMessage) {
			super(detailMessage);
		}

	}

}

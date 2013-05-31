package com.stonelab.ledctl;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class LedCtl {

	public static final String LINESEP = "\r\n";

	public static final String CTL_QUIT = "0";
	public static final String CTL_ON1 = "1";
	public static final String CTL_OFF1 = "2";
	public static final String CTL_ON2 = "3";
	public static final String CTL_OFF2 = "4";
	public static final String CTL_ON3 = "5";
	public static final String CTL_OFF3 = "6";
	public static final String CTL_ON4 = "7";
	public static final String CTL_OFF4 = "8";
	public static final String CTL_ON = "9";
	public static final String CTL_OFF = "10";
	public static final String CTL_SCENE = "11";
	public static final String CTL_STORE = "12";

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

	public void ctl(String cmd) {
		try {
			out.write((cmd + LINESEP).getBytes());
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

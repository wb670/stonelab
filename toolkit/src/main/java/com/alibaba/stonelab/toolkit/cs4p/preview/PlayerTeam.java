/**
 * 
 */
package com.alibaba.stonelab.toolkit.cs4p.preview;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @author <a href="mailto:li.jinl@tmall.com>Stone.J</a>
 * 
 */
public class PlayerTeam {

	private static final String HOST = "10.20.156.88";
	// private static final String HOST = "localhost";
	private static final int PORT = 9999;
	private static final String ENCODING = "UTF-8";
	private static final Random RANDOM = new Random();
	private static final int COUNT = 10;
	private static final String NAME_PREFIX = "fuckchenke";

	private static final List<String> PLAYERS = new ArrayList<String>();

	public static void main(String[] args) {
		for (int i = 0; i < COUNT; i++) {
			PLAYERS.add(NAME_PREFIX + i);
		}
		for (String name : PLAYERS) {
			new CPlayer(name);
		}
	}

	public static class CPlayer {

		private String name;
		private int x;
		private int y;
		private volatile AtomicBoolean live = new AtomicBoolean(false);
		private int nextx = 1;
		private int nexty = 1;

		private Socket socket;
		private OutputStream out;
		private BufferedReader in;

		public CPlayer(String name) {
			this.name = name;
			try {
				socket = new Socket();
				socket.connect(new InetSocketAddress(HOST, PORT));
				out = socket.getOutputStream();
				in = new BufferedReader(new InputStreamReader(socket.getInputStream(), ENCODING));
			} catch (IOException e) {
				close();
			}
			// add self
			add();
			final CPlayer p = this;
			// start run;
			new Thread() {
				public void run() {
					while (p.live.get()) {
						try {
							p.move();
						} catch (Exception e) {
							System.out.println(e);
						}
					}
				};
			}.start();
			new Thread() {
				public void run() {
					while (p.live.get()) {
						try {
							p.fire();
						} catch (Exception e) {
							System.out.println(e);
						}
					}
				};
			}.start();
		}

		public void add() {
			int x = RANDOM.nextInt(1024);
			int y = RANDOM.nextInt(1024);
			String resp = cmd(MessageFormat.format("padd {0} {1} {2}", this.name, String.valueOf(x), String.valueOf(y)));
			if (resp != null) {
				this.x = x;
				this.y = y;
				this.live.set(true);
			}
		}

		public void fire() {
			String resp = cmd("binfo");
			if (resp != null) {
				JSONObject info = JSONObject.fromObject(resp);
				JSONArray members = info.getJSONArray("members");
				for (Object m : members) {
					JSONObject member = (JSONObject) m;
					if (PLAYERS.contains(member.getJSONObject("info").getString("name"))) {
						continue;
					}
					int x = member.getJSONObject("point").getInt("x");
					int y = member.getJSONObject("point").getInt("y");
					resp = cmd(MessageFormat.format("pfire {0} {1}", String.valueOf(x), String.valueOf(y)));
					if (resp != null) {
						if (resp.length() != 2) {
							System.out.println("Fire OK: " + resp);
						}
					}
				}
			}
		}

		public void move() {
			if (this.x + nextx * 5 > 1024)
				this.nextx = -1;
			if (this.x + nextx * 5 < 0)
				this.nextx = 1;
			if (this.y + nexty * 5 > 1024)
				this.nexty = -1;
			if (this.y + nextx * 5 < 0) {
				this.nexty = 1;
			}
			int x = this.x + this.nextx * 5;
			int y = this.y + this.nexty * 5;
			String resp = cmd(MessageFormat.format("pmove {0} {1}", String.valueOf(x), String.valueOf(y)));
			if (resp != null) {
				this.x = x;
				this.y = y;
			}
		}

		public void abort() {
			this.live.set(false);
			close();
		}

		public void close() {
			if (socket != null && !socket.isClosed()) {
				try {
					if (in != null)
						in.close();
					if (out != null)
						out.close();
					socket.close();
				} catch (IOException e) {
					System.out.println("Close ERROR.");
				}
			}
		}

		private synchronized String cmd(String cmd) {
			try {
				if (this.socket.isClosed()) {
					return null;
				}
				out.write(cmd.getBytes());
				out.flush();
				String resp = in.readLine().trim();
				if (isFine(resp)) {
					return resp;
				} else {
					if (resp != null && resp.contains("玩完")) {
						System.out.println("DIE. name:" + this.name);
						abort();
					} else if (resp != null && resp.contains("漂")) {
						System.out.println(resp);
						// fix: get server's (x,y) with pinfo,then set to (this.x, this.y)
					} else if (resp != null && resp.contains("未开始")) {
						// do nothing.
					} else {
						System.out.println(MessageFormat.format("CMD ERROR. CMD is {0} \n RESP is {1}", cmd, resp));
					}
				}
			} catch (SocketException e) {
				abort();
			} catch (Exception e1) {
			}
			return null;
		}

		private boolean isFine(String resp) {
			return resp != null && (resp.startsWith("{") || resp.startsWith("["))
					&& (resp.endsWith("}") || resp.endsWith("]"));
		}
	}
}

package com.alibaba.stonelab.javalab.misc;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

public class ZkSample {

	public static void main(String[] args) throws Exception {
		zk();
	}

	static void zk() throws Exception {
		Watcher watcher = new Watcher() {

			@Override
			public void process(WatchedEvent event) {
				System.out.println("receive zk event. " + event.getType().name());
			}
		};

		ZooKeeper zk = new ZooKeeper("127.0.0.1:2181", 3000, watcher);

		zk.create("/abc/3", new byte[0], Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		zk.delete("/abc/3", 0);

		Thread.sleep(5000);
		zk.close();
	}

}

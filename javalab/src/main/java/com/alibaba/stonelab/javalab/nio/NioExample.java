/**
 * 
 */
package com.alibaba.stonelab.javalab.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author <a href="mailto:li.jinl@tmall.com>Stone.J</a>
 */
public class NioExample {

    public static final class SelectorManager implements Runnable {

        private static Selector selector;
        private WorkerManager   manager;

        public SelectorManager() throws IOException{
            selector = Selector.open();
        }

        public void run() {
            try {
                selector.select(100);

                Set<SelectionKey> keys = selector.keys();
                for (SelectionKey key : keys) {
                    if (key.isAcceptable()) {
                        SocketChannel sc = ((ServerSocketChannel) key.channel()).accept();
                        sc.configureBlocking(false);
                        sc.register(selector, SelectionKey.OP_READ);
                    }

                    if (key.isConnectable()) {
                        // 由於同步了，所以用不著這個方法。異步的話，實現這個方法，並且attach屬於自己的附件。
                        // key.attach();
                    }

                    if (key.isReadable()) {
                        SocketChannel sc = (SocketChannel) key.channel();
                        manager.submit(sc);
                    }

                }
            } catch (IOException e) {
                System.out.println("Selector Fail.");
            }
        }
    }

    public static final class WorkerManager implements Runnable {

        public Worker[]                            works = new Worker[10];
        private LinkedBlockingQueue<SocketChannel> queue = new LinkedBlockingQueue<SocketChannel>(1000);

        public void submit(SocketChannel sc) {
            queue.add(sc);
        }

        @Override
        public void run() {
            while (true) {
                SocketChannel sc = queue.poll();
                Worker work = new Worker();// get a unexcuted work from works
                work.submit(sc);
            }
        }

    }

    public static final class Worker implements Runnable {

        private SocketChannel          sc;

        private volatile AtomicBoolean stoped;
        private volatile AtomicBoolean excuted;

        @Override
        public void run() {
            while (stoped.get()) {
                while (excuted.get()) {
                    try {
                        sc.read(ByteBuffer.allocate(1024));// read length; then read bytes.
                        sc.write(ByteBuffer.allocate(1024));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    excuted.set(false);
                }
            }
        }

        public void submit(SocketChannel sc) {
            this.sc = sc;
            excuted.set(true);
        }

    }

}

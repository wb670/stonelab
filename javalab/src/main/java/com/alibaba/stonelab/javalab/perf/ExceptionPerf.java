/**
 * 
 */
package com.alibaba.stonelab.javalab.perf;

import java.util.concurrent.CountDownLatch;

/**
 * @author <a href="mailto:li.jinl@tmall.com>Stone.J</a>
 * 
 */
public class ExceptionPerf {

	private static final int LOOP = 10000000;
	private static final int NTHREADS = 50;

	public static void main(String[] args) throws Exception {
		for (int i = 0; i < LOOP / 100; i++) {
			new Tester(false, null);
			new Tester(true, null);
		}
		// general test
		new Thread("Gen") {
			@Override
			public void run() {

				CountDownLatch l1 = new CountDownLatch(NTHREADS);
				Tester t1 = new Tester(false, l1);

				long start = System.currentTimeMillis();
				for (int i = 0; i < NTHREADS; i++) {
					new Thread(t1).start();
				}
				try {
					l1.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("Gen Take Time: " + (System.currentTimeMillis() - start));
			}
		}.start();

		// opt test
		new Thread("Opt") {
			@Override
			public void run() {
				CountDownLatch l1 = new CountDownLatch(NTHREADS);
				Tester t1 = new Tester(true, l1);

				long start = System.currentTimeMillis();
				for (int i = 0; i < NTHREADS; i++) {
					new Thread(t1).start();
				}
				try {
					l1.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("Opt Take Time: " + (System.currentTimeMillis() - start));
			}
		}.start();
	}

	public static final class Tester implements Runnable {

		private boolean opt;
		private CountDownLatch latch;

		public Tester(boolean opt, CountDownLatch latch) {
			this.opt = opt;
			this.latch = latch;
		}

		@Override
		public void run() {
			for (int i = 0; i < LOOP; i++) {
				if (opt) {
					new OptException();
				} else {
					new GeneralException();
				}
			}
			if (latch != null) {
				latch.countDown();
			}
		}

	}

	public static final class GeneralException extends RuntimeException {

		private static final long serialVersionUID = 1L;

	}

	public static final class OptException extends RuntimeException {

		private static final long serialVersionUID = 1L;

		@Override
		public Throwable fillInStackTrace() {
			return this;
		}

	}

	public void asmThrow() {
		try {
			throw new RuntimeException();
		} catch (Exception e) {
		}
	}

}

package com.baskok.thriftsample.client;


public class RunPerformanceTest {

	private static final int CALLS_PER_THREAD = 1000;
	private static final int NUMBER_OF_THREADS = 500;

	public static void main(String[] args) throws Exception {
		long start = System.currentTimeMillis();
		Thread[] threads = startThreads();
		waitForThreadsToFinish(threads);
		printStatistics(start);
	}

	private static Thread[] startThreads() {
		Thread[] threads = new Thread[NUMBER_OF_THREADS];
		for (int i = 0; i < NUMBER_OF_THREADS; i++) {
			Thread thread = new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						ServiceClient client = new ServiceClient();
						for (int i = 0; i < CALLS_PER_THREAD; i++) {
							client.callService();
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			thread.start();
			threads[i] = thread;
		}
		return threads;
	}

	private static void waitForThreadsToFinish(Thread[] threads)
			throws InterruptedException {
		for (int i = 0; i < NUMBER_OF_THREADS; i++) {
			threads[i].join();
		}
	}

	private static void printStatistics(long start) {
		int totalNumberOfCalls = CALLS_PER_THREAD * NUMBER_OF_THREADS;
		long totalTimeMs = System.currentTimeMillis() - start;
		System.out.println("\n" + totalNumberOfCalls + " calls by " + NUMBER_OF_THREADS + " threads in " + totalTimeMs + "ms");
		System.out.println("which means we are doing " + 1000 * totalNumberOfCalls / totalTimeMs + " calls/sec");
	}

}

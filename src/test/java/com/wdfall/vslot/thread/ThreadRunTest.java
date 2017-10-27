package com.wdfall.vslot.thread;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ThreadRunTest {

	private static final int RUN_COUNT =   1000*1000 * 1000;

	public static void main(String[] args) throws Exception {
		new ThreadRunTest().testMemoryHashmap();
	}
	
	@Before
	public void setUp() {
	}
	
	
	@Test
	public void testMemoryHashmap() throws Exception {
		testItemMemoryHashmap(2, RUN_COUNT / 2);
		//testItemMemoryHashmap(1, RUN_COUNT);


		Assert.assertTrue(true);
	}
	
	
	private void testItemMemoryHashmap(int threadCount, int runCount) throws Exception {
		ThreadRun<CalcTaskMemoryHashmap> threadRun = new ThreadRun<CalcTaskMemoryHashmap>(CalcTaskMemoryHashmap.class, threadCount, runCount);
		threadRun.startWithThread();
	}
	
	
	/*
	 * CalcTaskMemory: SlotReelSymbolGenerator
	 * RUN_COUNT =   1000*1000 * 1000;
	 * 
2017-10-26 18:00:00 INFO  ThreadRun:41 -  >>> slot simulator start !!
2017-10-26 18:00:25 INFO  ThreadRun:66 -  >>> meanResult = 49.49837556 
2017-10-26 18:00:25 INFO  ThreadRun:67 -  >>> time duration = 24,807 ms
2017-10-26 18:00:25 INFO  ThreadRun:68 -  >>> slot simulator end !!
2017-10-26 18:00:25 INFO  ThreadRun:41 -  >>> slot simulator start !!
2017-10-26 18:01:00 INFO  ThreadRun:66 -  >>> meanResult = 49.499591169 
2017-10-26 18:01:00 INFO  ThreadRun:67 -  >>> time duration = 35,322 ms
2017-10-26 18:01:00 INFO  ThreadRun:68 -  >>> slot simulator end !! 
	 * 
	 */
	
	
	@Test
	public void testMemory() throws Exception {
		testItemMemory(1, RUN_COUNT);

		testItemMemory(2, RUN_COUNT / 2);

		Assert.assertTrue(true);
	}
	
	
	private void testItemMemory(int threadCount, int runCount) throws Exception {
		ThreadRun<CalcTaskMemory> threadRun = new ThreadRun<CalcTaskMemory>(CalcTaskMemory.class, threadCount, runCount);
		threadRun.startWithThread();
	}
	
	

	/*
	 * RUN_COUNT =   1000*1000 * 1000; 
	 * 
2017-10-26 14:31:52 INFO  ThreadRun:41 -  >>> slot simulator start !!
2017-10-26 14:32:07 INFO  ThreadRun:66 -  >>> meanResult = 49.499273732 
2017-10-26 14:32:07 INFO  ThreadRun:67 -  >>> time duration = 14,784 ms
2017-10-26 14:32:07 INFO  ThreadRun:68 -  >>> slot simulator end !!
2017-10-26 14:32:07 INFO  ThreadRun:41 -  >>> slot simulator start !!
2017-10-26 14:32:15 INFO  ThreadRun:66 -  >>> meanResult = 49.500759181999996 
2017-10-26 14:32:15 INFO  ThreadRun:67 -  >>> time duration = 8,375 ms
2017-10-26 14:32:15 INFO  ThreadRun:68 -  >>> slot simulator end !!
	 * 
	 */
	
	@Test
	public void testAll() throws Exception {
		testItem(1, RUN_COUNT);

		testItem(2, RUN_COUNT / 2);

		Assert.assertTrue(true);
	}

	private void testItem(int threadCount, int runCount) throws Exception {
		ThreadRun<CalcTask> threadRun = new ThreadRun<CalcTask>(CalcTask.class, threadCount, runCount);
		threadRun.startWithThread();
	}

}

package com.wdfall.vslot.thread;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ThreadRunTest {

	private static final int RUN_COUNT =   1000*1000 * 1000;

	
	@Before
	public void setUp() {
	}

	
	@Test
	public void testMemorySimple1() throws Exception {
		testItemGeneric(CalcTaskMemorySimple.class, 1, RUN_COUNT); 

		Assert.assertTrue(true);
	}

	@Test
	public void testMemorySimple2() throws Exception {
		testItemGeneric(CalcTaskMemorySimple.class, 2, RUN_COUNT / 2);

		Assert.assertTrue(true);
	}
	
	
	
	
	@Test
	public void testMemoryHashmap() throws Exception {
		testItemGeneric(CalcTaskMemoryHashmap.class, 1, RUN_COUNT);
		testItemGeneric(CalcTaskMemoryHashmap.class, 2, RUN_COUNT / 2);


		Assert.assertTrue(true);
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
	public void testMemory1() throws Exception {
		testItemGeneric(CalcTaskMemory.class, 1, RUN_COUNT);

		Assert.assertTrue(true);
	}
	
	@Test
	public void testMemory2() throws Exception {
		testItemGeneric(CalcTaskMemory.class, 2, RUN_COUNT / 2);
		
		Assert.assertTrue(true);
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
	public void testCalcTask1() throws Exception {
		testItemGeneric(CalcTask.class, 1, RUN_COUNT);

		Assert.assertTrue(true);
	}
	
	@Test
	public void testCalcTask2() throws Exception {
		testItemGeneric(CalcTask.class, 2, RUN_COUNT / 2);

		Assert.assertTrue(true);
	}
	
	
	private <T extends CalcTask> void testItemGeneric(Class<T> clazz, int threadCount, int runCount) throws Exception {
		ThreadRun<T> threadRun = new ThreadRun<T>(clazz, threadCount, runCount);
		threadRun.startWithThread();
	}

}

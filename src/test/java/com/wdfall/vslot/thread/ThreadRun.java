package com.wdfall.vslot.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.wdfall.vslot.utils.SlotUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public  class ThreadRun<T extends CalcTask> {
	
	/**
	 * main
	 */
	public static void main(String[] args) throws Exception {
		ThreadRun<CalcTask> threadRun = new ThreadRun<CalcTask>(CalcTask.class, 2, 1000*1000); 
		threadRun.startWithThread();
	}
	
	private int threadCount;
	private int runCount;
	private Class<T> clazz;
	
	public ThreadRun(Class<T> clazz, int threadCount, int runCount) {
		super();
		this.clazz = clazz;
		this.threadCount = threadCount;
		this.runCount = runCount;
	}

	/**
	 * simulator start
	 * @throws Exception
	 */
	public void startWithThread() throws Exception {
		log.info(" >>> slot simulator start !!");
		log.info(" >>> startWithThread !!");
		long startTime = System.currentTimeMillis();
		
		
		ExecutorService executorService = Executors.newFixedThreadPool(threadCount );
		List<Callable<Double>> taskList = new ArrayList<>();
		for(int i=0; i<threadCount; i++) {
			CalcTask task = clazz.newInstance();
			task.setRunCount(runCount); 
			taskList.add(task);
		}
		
		List<Future<Double>> resultList = executorService.invokeAll(taskList);
		double meanSum = 0;
		for(Future<Double> result : resultList) {
			double mean = result.get();
			meanSum = meanSum + mean;
		}
		double meanResult = meanSum / (double)resultList.size();
		
		executorService.shutdown();
		
		
		long endTime = System.currentTimeMillis();
		log.info(" >>> meanResult = {} ", meanResult); 
		log.info(" >>> time duration = {} ms", SlotUtils.getBigNumberFormat(endTime - startTime)); 
		log.info(" >>> slot simulator end !!");
	}
	
	
	
}

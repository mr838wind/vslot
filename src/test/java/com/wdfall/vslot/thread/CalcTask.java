package com.wdfall.vslot.thread;

import java.util.Random;
import java.util.concurrent.Callable;

import lombok.extern.slf4j.Slf4j;

/**
 * Task 
 * @author chhan
 *
 */
@Slf4j
public class CalcTask implements Callable<Double> {
	
	protected Random random = new Random();
	protected int runCount;
	
	public CalcTask() {
		super();
	}
	
	public void setRunCount(int runCount) {
		this.runCount = runCount;
	}
	
	@Override
	public Double call() throws Exception {
		log.info(" >>> CalcTask {} in Thread {} start ! ", this, Thread.currentThread().getId()); 
		
		doSomeMemoryProcess();
		
		double sum = 0;
		for(int i=0; i<runCount; i++) {
			int val = random.nextInt(100);
			sum = sum + val;
		}
		double result = sum / (double)runCount;
		
		
		log.info(" >>> CalcTask {} in Thread {} end ! ", this, Thread.currentThread().getId()); 
		
		return result;
	}

	
	public void doSomeMemoryProcess() {
		
	}
}

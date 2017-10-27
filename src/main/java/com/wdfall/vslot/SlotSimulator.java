package com.wdfall.vslot;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.wdfall.vslot.json.SlotGameSettingParam;
import com.wdfall.vslot.utils.FileUtil;
import com.wdfall.vslot.utils.SlotUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SlotSimulator {
	
	/**
	 * main
	 */
	public static void main(String[] args) throws Exception {
		String jsonFilePath = "slot_game_setting_param.json";
		int threadCount = 1;
		long gameRunCount = 1*1000*1000; // 1000 * 1000
		double payoutExpected = 0.0;
		//
		SlotSimulator slotSimulator = new SlotSimulator(jsonFilePath, threadCount, gameRunCount);
		slotSimulator.setPayoutExpected(payoutExpected); // for test
		slotSimulator.startWithThread();
	}

	// setting file path 
	private String jsonFilePath = "slot_game_setting_param.json";
	// 게임 진행 스레드 수
	private int threadCount = 1;
	// 게임 진행 횟수
	private long gameRunCount = 1*1000*1000;  // 1000 * 1000
	
	// ====================== test =======================
	private double payoutExpected = 0.0;
	
	public void setPayoutExpected(double payoutExpected) {
		this.payoutExpected = payoutExpected;
	}

	private double payoutReal;
	
	public double getPayoutReal() {
		return payoutReal;
	}

	// difference = payoutExpected - payoutReal
	public double getDifference() {
		return Math.abs(payoutExpected - payoutReal); 
	}
	// ====================== ]]test =======================
	
	public SlotSimulator(String jsonFilePath, int threadCount, long gameRunCount) {
		super();
		this.jsonFilePath = jsonFilePath;
		this.threadCount = threadCount;
		this.gameRunCount = gameRunCount;
	}

	/**
	 * simulator start
	 * @throws Exception
	 */
	public void startWithThread() throws Exception {
		log.info(" >>> slot simulator start !!");
		log.info(" >>> startWithThread !!");
		long startTime = System.currentTimeMillis();
		
		File jsonFile = FileUtil.getFileOnClasspath(jsonFilePath); 
		SlotGameSettingParam param = SlotGameSetting.readFromJson(jsonFile);
		
		ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
		List<SlotTask> taskList = new ArrayList<>();
		for(int i=0; i<threadCount; i++) {
			taskList.add(new SlotTask(param.copy(), gameRunCount));
		}
		
		List<Future<SlotGame>> resultList = executorService.invokeAll(taskList);
		long totalBet = 0;
		long totalWin = 0;
		long totalSpin = 0;
		long totalHit = 0;
		for(Future<SlotGame> result : resultList) {
			SlotGame game = result.get();
			totalBet += game.getTotalBet();
			totalWin += game.getTotalWin();
			totalSpin += game.getTotalSpin();
			totalHit += game.getTotalHit();
		}
		
		executorService.shutdown();
		
		log.info(" >>> totalBet : {}  ", totalBet);
		log.info(" >>> totalWin : {}  ", totalWin);
		log.info(" >>> totalSpin : {}  ", totalSpin);
		log.info(" >>> totalHit : {}  ", totalHit);
		
		payoutReal = (double)totalWin / totalBet * 100;
		String payoutPercentageShow = SlotUtils.getPercentFormat( payoutReal );
		String hitFrequencyShow = SlotUtils.getPercentFormat( (double)totalHit / totalSpin * 100 );
		log.info(" >>> threadCount : {} ", threadCount);
		log.info(" >>> gameRunCount : {} ", SlotUtils.getBigNumberFormat(gameRunCount));
		log.info(" >>> Payout Percentage : {} % ", payoutPercentageShow);
		log.info(" >>> Hit Frequency : {} % ", hitFrequencyShow);
		
		//
		if(payoutExpected > 0) {
			log.info("!!!! payoutExpected = {} ", payoutExpected);
			log.info("!!!! payoutReal = {} ", payoutReal);
			log.info("!!!! difference = {} ", getDifference()); 
		}
		
		long endTime = System.currentTimeMillis();
		log.info(" >>> time duration = {} ms", SlotUtils.getBigNumberFormat(endTime - startTime)); 
		log.info(" >>> slot simulator end !!");
	}
	
	/**
	 * Task 
	 * @author chhan
	 *
	 */
	@Slf4j
	private static class SlotTask implements Callable<SlotGame> {
		private SlotGameSettingParam param;
		private long gameRunCount;
		
		public SlotTask(SlotGameSettingParam param, long gameRunCount) {
			this.param = param;
			this.gameRunCount = gameRunCount;
		}
		
		@Override
		public SlotGame call() throws Exception {
			log.info(" >>> SlotTask {} in Thread {} start ! ", this, Thread.currentThread().getId()); 
			
			SlotGameSetting setting = new SlotGameSetting();
			//setting.init();
			setting.initFromParam(param); 
			setting.validate();
			
			SlotGame game = new SlotGame();
			game.init(setting);
			for(int i=0; i<gameRunCount; i++) {
				game.spin();
			}
			
			log.info(" >>> SlotTask {} in Thread {} end ! ", this, Thread.currentThread().getId()); 
			
			return game;
		}
	}
	
	
}

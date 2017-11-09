package com.wdfall.vslot;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.wdfall.vslot.excel.ExcelReaderSlot;
import com.wdfall.vslot.game.SlotGame;
import com.wdfall.vslot.json.SlotGameSettingParam;
import com.wdfall.vslot.utils.FileUtil;
import com.wdfall.vslot.utils.SlotUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SlotSimulator {
	
	// === 필수입력:
	private String filePath;
	private String excelSheetName = ExcelReaderSlot.SHEET_NAME_SLOT_INPUT;
	
	
	// === 선택입력:
	// 게임 진행 스레드 수
	private int threadCount = -1;
	// 게임 진행 횟수
	private long gameRunCount = -1;
	
	
	// === 변수
	private SlotGameSettingParam param;
	
	
	/**
	 * using default sheet name: "slot_input"
	 * @param filePath
	 */
	public SlotSimulator(String filePath) {
		this(filePath, ExcelReaderSlot.SHEET_NAME_SLOT_INPUT);
	}
	
	public SlotSimulator(String filePath, String excelSheetName) {
		super();
		this.filePath = filePath;
		this.excelSheetName = excelSheetName;
	}
	
	
	private void initParam() {
		File file = FileUtil.getFileOnClasspath(filePath);
		if(filePath.endsWith(".json")) {
			param = SlotGameSetting.readFromJson(file);
		} else if(filePath.endsWith(".xlsx") || filePath.endsWith(".xls")) {
			param = SlotGameSetting.readFromExcel(file, excelSheetName);
		}
		
		//
		if(threadCount != -1) {
			param.setThreadCount(threadCount);
		}
		if(gameRunCount != -1) {
			param.setGameRunCount(gameRunCount);
		}
	}

	/**
	 * simulator start
	 * @throws Exception
	 */
	public <T extends SlotGame> void startWithThread(Class<T> clazz) throws Exception {
		log.info(" >>> slot simulator start !!");
		log.info(" >>> startWithThread !!");
		long startTime = System.currentTimeMillis();
		
		initParam();
		
		ExecutorService executorService = Executors.newFixedThreadPool(param.getThreadCount());
		List<SlotTask<T>> taskList = new ArrayList<>();
		for(int i=0; i<param.getThreadCount(); i++) {
			taskList.add(new SlotTask<T>(clazz, param.copy()));
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
		log.info(" >>> threadCount : {} ", param.getThreadCount());
		log.info(" >>> gameRunCount : {} ", SlotUtils.getBigNumberFormat(param.getGameRunCount()));
		log.info(" >>> Payout Percentage : {} % ", payoutPercentageShow);
		log.info(" >>> Hit Frequency : {} % ", hitFrequencyShow);
		
		//
		log.info("!!!! payoutReal = {} ", payoutReal);
		if(payoutExpected > 0) {
			log.info("!!!! payoutExpected = {} ", payoutExpected);
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
	private  static class SlotTask<T extends SlotGame> implements Callable<SlotGame> { 
		private SlotGameSettingParam param;
		private Class<T> clazz;
		
		public SlotTask(Class<T> clazz, SlotGameSettingParam param) {
			this.clazz = clazz;
			this.param = param;
		}
		
		@Override
		public SlotGame call() throws Exception {
			log.info(" >>> SlotTask {} in Thread {} start ! ", this, Thread.currentThread().getId()); 
			
			SlotGameSetting setting = new SlotGameSetting();
			setting.initFromParam(param); 
			setting.validate();
			
			
			T game = clazz.newInstance();
			game.init(setting);
			for(int i=0; i<setting.getGameRunCount(); i++) {
				game.spin();
			}
			
			log.info(" >>> SlotTask {} in Thread {} end ! ", this, Thread.currentThread().getId()); 
			
			return game;
		}
	}
	
	
	// ====================== test =======================
	// === 선택입력: test용
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
	
	
	// get && set 
	public void setThreadCount(int threadCount) {
		this.threadCount = threadCount;
	}
	
	public void setGameRunCount(long gameRunCount) {
		this.gameRunCount = gameRunCount;
	}
	
	public void setExcelSheetName(String sheetName) {
		this.excelSheetName = sheetName;
	}
	
	
	
}

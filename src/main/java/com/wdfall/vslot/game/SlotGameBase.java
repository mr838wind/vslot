package com.wdfall.vslot.game;

import java.util.ArrayList;
import java.util.List;

import com.wdfall.vslot.SlotGameSetting;
import com.wdfall.vslot.pay_result.PayResultOne;
import com.wdfall.vslot.payout.PayoutTableRule;
import com.wdfall.vslot.random.SlotReelSymbolGenerator;
import com.wdfall.vslot.random.SlotReelSymbolGeneratorShuffle;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class SlotGameBase implements SlotGame {
	
	//===============================================
	// >>> input
	// 1. reel 구성
	// 2. symbol 구성
	// 3. win pattern
	// 4. payout table
	//
	// >>> output
	// 1. payout percentage = total_win / total_bet * 100
	// 2. hit frequency = total_hit / total_spin * 100 
	//===============================================

	// setting 
	protected SlotGameSetting setting;
	
	// 
	protected SlotReelSymbolGenerator slotReelSymbolGenerator;
	
	// >>> var 
	// reel show array
	protected String[][] reelShowArray;
	
	protected PayResultOne currentPayResult;
	
	// total
	protected long totalBet = 0;
	protected long totalWin = 0;
	protected long totalSpin = 0;
	protected long totalHit = 0;
	
	public SlotGameBase() {
		
	}
	
	
	
	/**
	 * 설정 초기화
	 */
	public void init(SlotGameSetting setting) {
		log.info(" >>> setting : {}", setting);
		this.setting = setting;
		
		log.info(" >>> symbolList : {}", setting.getSymbolList());
		
		// reel show array
		int reelCount = setting.getReelCount();
		reelShowArray = new String[reelCount][];
		for(int i=0; i<reelCount; i++) {
			int count = setting.getReelCountArray()[i];
			reelShowArray[i] = new String[count];
		}
		
		//
		this.slotReelSymbolGenerator = new SlotReelSymbolGeneratorShuffle(this.setting.getReelCountArray(), this.setting.getReelCompositionList());
	}
	
	
	
	/**
	 * 게임 한번 실행 
	 */
	public PayResultOne spin() {
		//
		int currentBet = setting.getBetPerLine() * setting.getLinePatternList().size();
		totalBet = totalBet + currentBet;
		totalSpin = totalSpin + 1;
		
		// 1. init reelShowArray and others
		initReelShowArray(reelShowArray);
		currentPayResult = new PayResultOne();
		
		// 2. generate current reelShowArray
		generateCurrentReelShowArray();
		
		//
		logReelShowArray();
		
		// 3. check result 
		checkCurrentResult();
		
		//
		long currentWin = currentPayResult.getCurrentPayResult();
		totalWin = totalWin + currentWin;
		if(currentWin > 0) {
			totalHit = totalHit + 1;
		}
		
		return currentPayResult;
	}
	
	
	
	protected void initReelShowArray(String[][] array) {
		for(int i=0; i<array.length; i++) {
			String[] inArray = array[i];
			for(int j=0; j<inArray.length; j++) {
				array[i][j] = "";
			}
		}
	}
	
	protected void generateCurrentReelShowArray() {
		reelShowArray = slotReelSymbolGenerator.generateReelShowArray();
	}

	protected void checkCurrentResult() {
		
		// win = scatterPay + nonScatterPay
		checkScatterPay();
		
		checkNonScatterPay();
		
		log.debug(" current win : {}", currentPayResult.getCurrentPayResult());
	}

	protected void checkNonScatterPay() {
		// add all pay from win patterns
		int lineNum = -1;
		for(List<Integer> pattern : setting.getLinePatternList()) {
			lineNum++;
			calcPayInPattern(pattern, lineNum);
		}
	}

	protected void checkScatterPay() {
		if(setting.isScatterExist()) {
			setting.getPayoutTableRuleScatter().calculate(reelShowArray, currentPayResult);
		}
	}
	

	protected void calcPayInPattern(List<Integer> pattern, int lineNum) {
		List<String> currentResult = getCurrentResult(pattern);
		for(PayoutTableRule rule : setting.getPayoutTableRuleList()) {
			rule.calculate(currentResult, currentPayResult, lineNum);
		}
	}

	protected List<String> getCurrentResult(List<Integer> pattern) {
		List<String> currentResult = new ArrayList<>();
		for(int reel=0; reel<setting.getReelCount(); reel++) {
			int symbolIndex = pattern.get(reel) - 1;
			String symbol = reelShowArray[reel][symbolIndex];
			currentResult.add(symbol);
		}
		
		log.debug("currentResult = {}", currentResult);
		return currentResult;
	}

	public double getPayoutPercentage() {
		log.debug("total_win = {}", totalWin);
		log.debug("total_bet = {}", totalBet);
		return ((double)totalWin / totalBet) * 100;
	}
	
	public double getHitFrequency() {
		log.debug("total_hit = {}", totalHit);
		log.debug("total_spin = {}", totalSpin);
		return ((double)totalHit / totalSpin) * 100;
	}
	
	protected void logReelShowArray() {
		//debug: show array 
		log.debug("=================================");
		log.debug("-----------------");
		StringBuffer logResult = new StringBuffer("reel show Array: " + System.lineSeparator());
		int lineCount = reelShowArray[0].length;
		for(int i=0; i<lineCount; i++) {
			for(int j=0; j<setting.getReelCount(); j++) {
				logResult.append(reelShowArray[j][i]).append("\t");
			}
			logResult.append(System.lineSeparator());
		}
		log.debug("{}", logResult); 
		log.debug("=================================");
	}

	// get && set
	
	public SlotReelSymbolGenerator getSlotReelSymbolGenerator() {
		return slotReelSymbolGenerator;
	}

	public void setSlotReelSymbolGenerator(SlotReelSymbolGenerator slotReelSymbolGenerator) {
		this.slotReelSymbolGenerator = slotReelSymbolGenerator;
	}
	
	public long getTotalWin() {
		return totalWin;
	}

	public long getTotalBet() {
		return totalBet;
	}

	public long getTotalSpin() {
		return totalSpin;
	}

	public long getTotalHit() {
		return totalHit;
	}
	
	
}

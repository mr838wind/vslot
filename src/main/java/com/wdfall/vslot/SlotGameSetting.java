package com.wdfall.vslot;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

import com.wdfall.vslot.excel.ExcelReaderSlot;
import com.wdfall.vslot.json.PayoutTableRuleParam;
import com.wdfall.vslot.json.SlotGameSettingParam;
import com.wdfall.vslot.payout.PayoutTableRule;
import com.wdfall.vslot.payout.PayoutTableRuleBonusSymbolStage1;
import com.wdfall.vslot.payout.PayoutTableRuleNormalSymbol;
import com.wdfall.vslot.payout.PayoutTableRuleScatter;
import com.wdfall.vslot.payout.PayoutTableRuleScatterSymbol;
import com.wdfall.vslot.payout.PayoutTableRuleWildSymbol;
import com.wdfall.vslot.utils.FileUtil;
import com.wdfall.vslot.utils.JsonBuilder;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * slot game setting
 * @author chhan
 *
 */
@Data
@Slf4j
public class SlotGameSetting {
	
	// 게임 진행 스레드 수
	private int threadCount;

	// 게임 진행 횟수
	private long gameRunCount;

	// ==== inputs ====
	// reel 총 개수 
	private int reelCount;
	// reel 당 개수
	private int[] reelCountArray;
		
	
	// payout table
	private List<PayoutTableRule> payoutTableRuleList;
	// payout scatter
	private PayoutTableRuleScatter payoutTableRuleScatter;
	// payout bonus
	private PayoutTableRuleScatter payoutTableRuleBonus;
	
	// 사용할 symbol 
	private List<String> normalSymbolList;
	// 사용할 기타 symbol
	private List<String> otherSymbolList;
	// 사용할 symbol 
	private List<String> symbolList;
	
	
	// reel symbol 구성
	private List<List<String>> reelCompositionList;
	// win pattern
	private List<List<Integer>> linePatternList;
	
	
	// ==== finals ====
	//line별 bet
	private final int betPerLine = 1; 
	
	
	public boolean isScatterExist() {
		return (payoutTableRuleScatter != null);
	}
	
	public boolean isBonusExist() {
		return (payoutTableRuleBonus != null);
	}
	
	
	public SlotGameSetting() {
		
	}
	
	public void validate() {
		boolean isNull = (threadCount <= 0) || (gameRunCount <= 0)
				|| (reelCount <= 0) || (reelCountArray == null || reelCountArray.length == 0)
				|| CollectionUtils.isEmpty(payoutTableRuleList)
				|| CollectionUtils.isEmpty(reelCompositionList)
				|| CollectionUtils.isEmpty(linePatternList)
				;
		if(isNull) {
			throw new IllegalArgumentException("validate null");
		}
		
		if(reelCountArray.length != reelCount) {
			throw new IllegalArgumentException("reelCountArray != reelCount");
		}
		if(reelCompositionList.size() != reelCount) {
			throw new IllegalArgumentException("reelCompositionList != reelCount");
		}
		int linePatternSize = linePatternList.get(0).size();
		if(linePatternSize != reelCount) {
			throw new IllegalArgumentException("linePatternSize != reelCount");
		}
	}

	public void initFromExcel(File excelFile, String sheetName) {
		SlotGameSettingParam param = readFromExcel(excelFile, sheetName);
		
		initFromParam(param);
	}
	
	public static SlotGameSettingParam readFromExcel(File excelFile, String sheetName) {
		ExcelReaderSlot excelReaderSlot = new ExcelReaderSlot();
		excelReaderSlot.processSheetData(excelFile, sheetName);
		SlotGameSettingParam settingParam = excelReaderSlot.getSlotGameSettingParam();
		return settingParam;
	}


	public void initFromJson(File jsonFile) {
		SlotGameSettingParam param = readFromJson(jsonFile); 
		
		initFromParam(param);
	}

	public static SlotGameSettingParam readFromJson(File jsonFile) {
		String json = FileUtil.readFromFile(jsonFile);
		SlotGameSettingParam param = JsonBuilder.jsonToObject(json, SlotGameSettingParam.class);
		log.debug("initFromJson : {}", json); 
		log.debug("param : {}", param);
		return param;
	}

	public void initFromParam(SlotGameSettingParam param) {
		// simulator setting
		threadCount = param.getThreadCount();
		gameRunCount = param.getGameRunCount();
		
		// >> 1. reel 구성
		// reel 총 개수
		reelCount = param.getReelCount();
		// reel 당 개수
		reelCountArray = param.getReelCountArray();
		
		// >> 사용할 symbol
		normalSymbolList = new ArrayList<>();
		otherSymbolList = new ArrayList<>();
		symbolList = new ArrayList<>();
		
		// >> 4. payout table
		// HA * 3 = 10; 4=100; 5=200;
		// MA * 3 = 5; 4=50; 5=100;
		payoutTableRuleList = new ArrayList<>();
		List<PayoutTableRuleParam> payoutTableRuleParamList = param.getPayoutTableRuleParamList();
		
		List<PayoutTableRuleParam> payoutTableRuleNormalParamList = new ArrayList<>();
		List<PayoutTableRuleParam> payoutTableRuleOtherParamList = new ArrayList<>();
		for(PayoutTableRuleParam ruleParam : payoutTableRuleParamList) {
			String symbol = ruleParam.getSymbol();
			if(PayoutTableRuleParam.SYMBOL_TYPE_NORMAL.equals(ruleParam.getSymbolType())) {
				payoutTableRuleNormalParamList.add(ruleParam);
				normalSymbolList.add(symbol);
			} else {
				payoutTableRuleOtherParamList.add(ruleParam);
				otherSymbolList.add(symbol);
			}
			symbolList.add(symbol);
		}
		
		// normal symbol
		List<PayoutTableRule> normalSymbolRuleList = new ArrayList<>();
		for(PayoutTableRuleParam ruleParam : payoutTableRuleNormalParamList) {
			String symbol = ruleParam.getSymbol();
			Map<Integer, Integer> rule = ruleParam.getRule();
			PayoutTableRule normalSymbolRule = new PayoutTableRuleNormalSymbol(symbol, rule);
			normalSymbolRuleList.add(normalSymbolRule);
			payoutTableRuleList.add(normalSymbolRule);
		}
		
		// other symbol
		for(PayoutTableRuleParam ruleParam : payoutTableRuleOtherParamList) {
			if(PayoutTableRuleParam.SYMBOL_TYPE_WILD.equals(ruleParam.getSymbolType())) {
				// wild 특수처리
				PayoutTableRule wildSymbolRule = new PayoutTableRuleWildSymbol(ruleParam.getSymbol(), ruleParam.getRule(), normalSymbolList, normalSymbolRuleList);
				payoutTableRuleList.add(wildSymbolRule);
			} else if(PayoutTableRuleParam.SYMBOL_TYPE_SCATTER.equals(ruleParam.getSymbolType())) {
				payoutTableRuleScatter = new PayoutTableRuleScatterSymbol(ruleParam.getSymbol(), ruleParam.getRule());
			} else if(PayoutTableRuleParam.SYMBOL_TYPE_BONUS.equals(ruleParam.getSymbolType())) {
				payoutTableRuleBonus = new PayoutTableRuleBonusSymbolStage1(ruleParam.getSymbol(), ruleParam.getRule());
			}
		}
		
		
		// >> 2. reel symbol 구성
		reelCompositionList = new ArrayList<List<String>>();
		List<Map<String, Integer>> reelCompositionParamList = param.getReelCompositionParamList();
		//reel1, ... reel5
		for(Map<String, Integer> symbolParam : reelCompositionParamList) {
			reelCompositionList.add(generateReelComposition(symbolParam));
		}
		
		// >> 3. line pattern
		linePatternList = param.getLinePatternList(); 
		
	}
	
	private List<String> generateReelComposition(Map<String, Integer> symbolParam) {
		List<String> reelComposition = new ArrayList<>();
		
		for(Map.Entry<String, Integer> param : symbolParam.entrySet()) {
			Integer symbolCount = param.getValue();
			for(int i=0; i<symbolCount; i++) {
				String symbolName = param.getKey();
				reelComposition.add(symbolName);
			}
		}
		return reelComposition;
	}
	
}

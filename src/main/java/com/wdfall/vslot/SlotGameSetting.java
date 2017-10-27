package com.wdfall.vslot;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.wdfall.vslot.json.SlotGameSettingParam;
import com.wdfall.vslot.json.SlotGameSettingParam.PayoutTableRuleParam;
import com.wdfall.vslot.payout.PayoutTableRule;
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

	// reel 총 개수 
	private int reelCount;
	// reel 당 개수
	private int[] reelCountArray;
	//line별 bet
	private int betPerLine; 
		
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
	// payout table
	private List<PayoutTableRule> payoutTableRuleList;
	// payout scatter
	private PayoutTableRuleScatter payoutTableRuleScatter;
	
	public boolean isScatterExist() {
		return (payoutTableRuleScatter != null);
	}
	
	
	public SlotGameSetting() {
		
	}
	
	public void validate() {
		//TODO ...
	}

	public void initFromExcel() {
		//TODO ...
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
		// >> 1. reel 구성
		// reel 총 개수
		reelCount = param.getReelCount();
		// reel 당 개수
		reelCountArray = param.getReelCountArray();
		
		//bet
		betPerLine = param.getBetPerLine();
		
		// >> 사용할 symbol
		normalSymbolList = param.getNormalSymbolList();
		
		otherSymbolList = param.getOtherSymbolList();
		
		generateSymbolList();
		
		// >> 2. reel symbol 구성
		reelCompositionList = new ArrayList<List<String>>();
		List<Map<String, Integer>> reelCompositionParamList = param.getReelCompositionParamList();
		//reel1, ... reel5
		for(Map<String, Integer> symbolParam : reelCompositionParamList) {
			reelCompositionList.add(generateReelComposition(symbolParam));
		}
		
		// >> 3. line pattern
		linePatternList = param.getLinePatternList(); 
		
		// >> 4. payout table
		// HA * 3 = 10; 4=100; 5=200;
		// MA * 3 = 5; 4=50; 5=100;
		payoutTableRuleList = new ArrayList<>();
		List<PayoutTableRuleParam> payoutTableRuleParamList = param.getPayoutTableRuleParamList();
		
		List<PayoutTableRuleParam> payoutTableRuleNormalParamList = new ArrayList<>();
		List<PayoutTableRuleParam> payoutTableRuleOtherParamList = new ArrayList<>();
		for(PayoutTableRuleParam ruleParam : payoutTableRuleParamList) {
			if(PayoutTableRuleParam.SYMBOL_TYPE_NORMAL.equals(ruleParam.getSymbolType())) {
				payoutTableRuleNormalParamList.add(ruleParam);
			} else {
				payoutTableRuleOtherParamList.add(ruleParam);
			}
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
			}
		}
	}
	
	private void generateSymbolList() {
		symbolList = new ArrayList<>();
		symbolList.addAll(normalSymbolList);
		symbolList.addAll(otherSymbolList);
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

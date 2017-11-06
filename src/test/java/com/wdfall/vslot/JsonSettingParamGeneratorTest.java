package com.wdfall.vslot;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.junit.Assert;
import org.junit.Test;

import com.wdfall.vslot.json.SlotGameSettingParam;
import com.wdfall.vslot.json.SlotGameSettingParam.PayoutTableRuleParam;
import com.wdfall.vslot.utils.FileUtil;
import com.wdfall.vslot.utils.JsonBuilder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonSettingParamGeneratorTest {
	private static File PARAM_FILE = FileUtil.getFileOnClasspath("slot_game_setting_param.json");

	public static void main(String[] args) {
		new JsonSettingParamGeneratorTest().writeSettingParamJson();
		new JsonSettingParamGeneratorTest().readSettingParamJson(); 
	}
	
	public void writeSettingParamJson() {
		SlotGameSettingParam param = new SlotGameSettingParam();
		param.setReelCount(3);
		param.setReelCountArray(new int[]{3,3,3});
		
		List<PayoutTableRuleParam> payoutTableRuleNormalParamList = new ArrayList<>();

		PayoutTableRuleParam rpSS = new PayoutTableRuleParam();
		rpSS.setSymbol("SS");
		rpSS.setSymbolType(PayoutTableRuleParam.SYMBOL_TYPE_SCATTER);
		Map<Integer, Integer> countPayMapSS = new HashMap<>();
		countPayMapSS.put(3, 200);
		rpSS.setRule(countPayMapSS);
		payoutTableRuleNormalParamList.add(rpSS);
		
		PayoutTableRuleParam rp4 = new PayoutTableRuleParam();
		rp4.setSymbol("WD");
		rp4.setSymbolType(PayoutTableRuleParam.SYMBOL_TYPE_WILD);
		Map<Integer, Integer> countPayMapWD = new HashMap<>();
		countPayMapWD.put(3, 100);
		rp4.setRule(countPayMapWD);
		payoutTableRuleNormalParamList.add(rp4);
		
		PayoutTableRuleParam rp1 = new PayoutTableRuleParam();
		rp1.setSymbol("N1");
		rp1.setSymbolType(PayoutTableRuleParam.SYMBOL_TYPE_NORMAL); 
		Map<Integer, Integer> countPayMapHA = new HashMap<>();
		countPayMapHA.put(3, 5);
		rp1.setRule(countPayMapHA);
		payoutTableRuleNormalParamList.add(rp1);
		
		PayoutTableRuleParam rp2 = new PayoutTableRuleParam();
		rp2.setSymbol("N2");
		rp2.setSymbolType(PayoutTableRuleParam.SYMBOL_TYPE_NORMAL);
		Map<Integer, Integer> countPayMapMA = new HashMap<>();
		countPayMapMA.put(3, 10);
		rp2.setRule(countPayMapMA);
		payoutTableRuleNormalParamList.add(rp2);
		
		PayoutTableRuleParam rp3 = new PayoutTableRuleParam();
		rp3.setSymbol("N3");
		rp3.setSymbolType(PayoutTableRuleParam.SYMBOL_TYPE_NORMAL);
		Map<Integer, Integer> countPayMapMB = new HashMap<>();
		countPayMapMB.put(3, 20);
		rp3.setRule(countPayMapMB);
		payoutTableRuleNormalParamList.add(rp3);
		
		param.setPayoutTableRuleParamList(payoutTableRuleNormalParamList); 
		
		//
		Map<String, Integer> symbolParam = new HashMap<>();
		symbolParam.put("SS", 1);
		symbolParam.put("WD", 1);
		symbolParam.put("N1", 10);
		symbolParam.put("N2", 10);
		symbolParam.put("N3", 10);
		List<Map<String, Integer>> reelCompositionParamList = new ArrayList<>();
		reelCompositionParamList.add(symbolParam);
		reelCompositionParamList.add(symbolParam);
		reelCompositionParamList.add(symbolParam);
		reelCompositionParamList.add(symbolParam);
		reelCompositionParamList.add(symbolParam);
		param.setReelCompositionParamList(reelCompositionParamList);
		
		List<List<Integer>> winPatternList = new ArrayList<>();
		winPatternList.add(Arrays.asList(1,1,1));
		param.setLinePatternList(winPatternList);
		
		String json = JsonBuilder.objectToJson(param);
		log.debug("json = \r\n {}", json);
		FileUtil.writeToFile(PARAM_FILE, json);
	}
	
	public void readSettingParamJson() {
		String json = FileUtil.readFromFile(PARAM_FILE);
		SlotGameSettingParam param = JsonBuilder.jsonToObject(json, SlotGameSettingParam.class);
		log.debug("param = {}", param); 
		log.debug("getReelCount = {}", param.getReelCount()); 
	}
	

	@Test
	public void testObjectToJson() {
		SlotGameSettingParam param = new SlotGameSettingParam();
		param.setReelCount(6);
		String json = JsonBuilder.objectToJson(param);
		log.debug("json = {}", json);
		//"reelCount" : 6
		Pattern p = Pattern.compile(".*\"reelCount\"\\s:\\s6.*", Pattern.DOTALL);
		Assert.assertTrue(p.matcher(json).matches());
	}
	
	@Test
	public void testJsonToObject() {
		String json = "{\"reelCount\":6}";
		SlotGameSettingParam param = JsonBuilder.jsonToObject(json, SlotGameSettingParam.class);
		log.debug("param = {}", param);
		Assert.assertTrue(param.getReelCount() == 6); 
	}
	
}

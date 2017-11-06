package com.wdfall.vslot.excel.parse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.StringUtils;

import com.wdfall.vslot.excel.ExcelUtils;
import com.wdfall.vslot.json.SlotGameSettingParam;
import com.wdfall.vslot.json.SlotGameSettingParam.PayoutTableRuleParam;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ParseDataItemPayoutTableRuleParam extends ParseDataItemTemplate {
	
	private SlotGameSettingParam slotGameSettingParam;

	public ParseDataItemPayoutTableRuleParam(List<List<String>> excelData, String inputSymbol, SlotGameSettingParam slotGameSettingParam) {
		super(excelData, inputSymbol);
		this.slotGameSettingParam = slotGameSettingParam;
	}

	@Override
	protected void trimLocation() {
		
	}

	@Override
	protected void handleExcelData() {
		
		List<PayoutTableRuleParam> payoutTableRuleParamList = new ArrayList<>();
		
		List<String> symbolIdList = parsedDataList.get(0); //ID
		List<String> symbolTypeList = parsedDataList.get(1); //type
		
		//symbol
		for(int i=0; i<symbolIdList.size(); i++) {
			String symbolId = symbolIdList.get(i);
			String symbolType = symbolTypeList.get(i);
			
			PayoutTableRuleParam ruleParam = generatePayoutTableRuleParam(i, symbolId, symbolType);
			
			payoutTableRuleParamList.add(ruleParam);
		}
		
		
		slotGameSettingParam.setPayoutTableRuleParamList(payoutTableRuleParamList );
		
		log.info("payoutTableRuleParamList = {}", payoutTableRuleParamList); 
	}

	private PayoutTableRuleParam generatePayoutTableRuleParam(int i, String symbolId, String symbolType) {
		//
		PayoutTableRuleParam ruleParam = new PayoutTableRuleParam();
		ruleParam.setSymbol(symbolId);
		ruleParam.setSymbolType(symbolType);
		// rule
		Map<Integer, Integer> countPayMap = new HashMap<>();
		for(int j=2; j<headerLine.size(); j++) {
			int count = ExcelUtils.parseInt(headerLine.get(j));
			String dataString = parsedDataList.get(j).get(i);
			if( ! ExcelUtils.isEmptyOrZero(dataString)) {
				int pay = ExcelUtils.parseInt(dataString);
				countPayMap.put(count, pay);
			}
		}
		ruleParam.setRule(countPayMap);
		return ruleParam;
	}
	
	
}

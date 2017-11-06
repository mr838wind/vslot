package com.wdfall.vslot.excel.parse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wdfall.vslot.json.SlotGameSettingParam;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ParseDataItemReelCompositionParam extends ParseDataItemTemplate {
	
	private SlotGameSettingParam slotGameSettingParam;

	public ParseDataItemReelCompositionParam(List<List<String>> excelData, SlotGameSettingParam slotGameSettingParam) {
		super(excelData, "reelCompositionParamList");
		this.slotGameSettingParam = slotGameSettingParam;
	}

	@Override
	protected void trimLocation() {
		// 합계 remove
		loc.setRowIndexEnd(loc.getRowIndexEnd() - 1);
	}

	@Override
	protected void handleExcelData() {
		
		List<Map<String, Integer>> reelCompositionParamList = new ArrayList<>(); 
		
		// 릴1,릴2...	
		List<String> symbolIdList = parsedDataList.get(0); //ID
		for(int offset=1; offset<headerLine.size(); offset++) {
			List<String> reelList = parsedDataList.get(offset);
			
			Map<String, Integer> reelComp = generateReelCompositionMap(symbolIdList, reelList);
			reelCompositionParamList.add(reelComp);
		}
		
		slotGameSettingParam.setReelCompositionParamList(reelCompositionParamList);
		
		log.info("reelCompositionParamList = {}", reelCompositionParamList);
		
	}
	
	private Map<String, Integer> generateReelCompositionMap(List<String> symbolIdList, List<String> reelList) {
		Map<String, Integer> reelComp = new HashMap<>();
		for(int i=0; i<symbolIdList.size(); i++) {
			String symbolID = symbolIdList.get(i);
			String countString = reelList.get(i);
			Integer count = (int)(double)Double.valueOf(countString);
			reelComp.put(symbolID, count);
		}
		return reelComp;
	}
	
}

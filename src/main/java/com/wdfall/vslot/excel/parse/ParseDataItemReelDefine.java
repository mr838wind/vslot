package com.wdfall.vslot.excel.parse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wdfall.vslot.json.SlotGameSettingParam;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ParseDataItemReelDefine extends ParseDataItemTemplate {
	
	private SlotGameSettingParam slotGameSettingParam;

	public ParseDataItemReelDefine(List<List<String>> excelData, String inputSymbol, SlotGameSettingParam slotGameSettingParam) {
		super(excelData, inputSymbol);
		this.slotGameSettingParam = slotGameSettingParam;
	}

	@Override
	protected void trimLocation() {
		// 심볼,합계 remove
		loc.setCellIndexStart(loc.getCellIndexStart() + 1);
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
		
		log.info("{}", reelCompositionParamList);
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

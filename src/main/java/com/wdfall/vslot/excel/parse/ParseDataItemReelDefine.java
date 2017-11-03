package com.wdfall.vslot.excel.parse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wdfall.vslot.json.SlotGameSettingParam;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ParseDataItemReelDefine extends ParseDataItemTemplate {
	
	SlotGameSettingParam slotGameSettingParam;

	public ParseDataItemReelDefine(List<List<String>> excelData, SlotGameSettingParam slotGameSettingParam) {
		super(excelData, "reel-define");
		this.slotGameSettingParam = slotGameSettingParam;
	}
	
	@Override
	protected void trimLocation() {
		loc.setCellIndexStart(loc.getCellIndexStart() + 1);
		loc.setRowIndexEnd(loc.getRowIndexEnd() - 1);
	}

	@Override
	protected void handleExcelDataMap() {
		List<Map<String, Integer>> reelCompositionParamList = new ArrayList<>();
		
		List<String> symbolIdList = excelDataMap.get("ID");
		
		// ID빼고 차례대로: 릴1,릴2...
		for(int i=1; i<headerList.size(); i++) {
			String header = headerList.get(i);
			List<String> reelList = excelDataMap.get(header); 
			Map<String, Integer> reelComp = generateReelCompositionMap(symbolIdList, reelList);
			reelCompositionParamList.add(reelComp);
		}
		
		slotGameSettingParam.setReelCompositionParamList(reelCompositionParamList );
		
		log.info("reelCompositionParamList = {}", reelCompositionParamList);
	}

	private Map<String, Integer> generateReelCompositionMap(List<String> symbolIdList, List<String> reel1List) {
		Map<String, Integer> reelComp = new HashMap<>();
		for(int i=0; i<symbolIdList.size(); i++) {
			String symbolID = symbolIdList.get(i);
			String countString = reel1List.get(i);
			Integer count = (int)(double)Double.valueOf(countString);
			reelComp.put(symbolID, count);
		}
		return reelComp;
	}

	
}

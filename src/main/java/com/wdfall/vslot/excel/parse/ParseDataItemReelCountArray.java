package com.wdfall.vslot.excel.parse;

import java.util.ArrayList;
import java.util.List;

import com.wdfall.vslot.excel.ExcelUtils;
import com.wdfall.vslot.json.SlotGameSettingParam;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ParseDataItemReelCountArray extends ParseDataItemTemplate {
	
	private SlotGameSettingParam slotGameSettingParam;

	public ParseDataItemReelCountArray(List<List<String>> excelData, String inputSymbol, SlotGameSettingParam slotGameSettingParam) {
		super(excelData, inputSymbol);
		this.slotGameSettingParam = slotGameSettingParam;
	}

	@Override
	protected void trimLocation() {
	}

	@Override
	protected void handleExcelData() {
		
		List<String> reelCountStr = parsedDataList.get(0);
		int[] reelCountArray = new int[reelCountStr.size()];
		for(int i=0; i<reelCountStr.size(); i++) {
			String data = reelCountStr.get(i);
			int reelCount = ExcelUtils.parseInt(data);
			reelCountArray[i] = reelCount;
		}
		
		slotGameSettingParam.setReelCountArray(reelCountArray); 
		
		log.info("reelCountArray = {}", reelCountArray); 
	}
	
}

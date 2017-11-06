package com.wdfall.vslot.excel.parse;

import java.util.List;

import com.wdfall.vslot.excel.ExcelUtils;
import com.wdfall.vslot.json.SlotGameSettingParam;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ParseDataItemReelCount extends ParseDataItemTemplate {
	
	private SlotGameSettingParam slotGameSettingParam;

	public ParseDataItemReelCount(List<List<String>> excelData, SlotGameSettingParam slotGameSettingParam) {
		super(excelData, "reelCount");
		this.slotGameSettingParam = slotGameSettingParam;
	}

	@Override
	protected void trimLocation() {
	}

	@Override
	protected void handleExcelData() {
		
		List<String> reelCountStr = parsedDataList.get(0);
		int reelCount = ExcelUtils.parseInt(reelCountStr.get(0));
		
		slotGameSettingParam.setReelCount(reelCount);
		
		log.info("reelCount = {}", reelCount); 
	}
	
}

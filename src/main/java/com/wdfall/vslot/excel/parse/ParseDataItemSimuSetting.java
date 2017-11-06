package com.wdfall.vslot.excel.parse;

import java.util.List;

import com.wdfall.vslot.excel.ExcelUtils;
import com.wdfall.vslot.json.SlotGameSettingParam;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ParseDataItemSimuSetting extends ParseDataItemTemplate {
	
	private SlotGameSettingParam slotGameSettingParam;

	public ParseDataItemSimuSetting(List<List<String>> excelData, SlotGameSettingParam slotGameSettingParam) {
		super(excelData, "simuSetting");
		this.slotGameSettingParam = slotGameSettingParam;
	}

	@Override
	protected void trimLocation() {
	}

	@Override
	protected void handleExcelData() {
		
		List<String> threadCountStr = parsedDataList.get(0);
		int threadCount = ExcelUtils.parseInt(threadCountStr.get(0));
		slotGameSettingParam.setThreadCount(threadCount);
		
		// 
		List<String> gameRunCountStr = parsedDataList.get(1);
		int gameRunCount = ExcelUtils.parseInt(gameRunCountStr.get(0));
		slotGameSettingParam.setGameRunCount(gameRunCount);
		
		log.info("threadCount = {}", threadCount); 
		log.info("gameRunCount = {}", gameRunCount); 
	}
	
}

package com.wdfall.vslot.excel.parse;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ParseDataItemDummDefine extends ParseDataItemTemplate {

	public ParseDataItemDummDefine(List<List<String>> excelData) {
		super(excelData, "dumm-define");
	}
	
	@Override
	protected void trimLocation() {
		
	}

	@Override
	protected void handleExcelDataMap() {
		log.info(">>excelData = {}", excelData);
		log.info(">>excelDataMap = {}", excelDataMap);
		log.info("reelList1 = {}", excelDataMap.get("릴1") );
		log.info("reelList2 = {}", excelDataMap.get("릴2"));
		
	}

	
}

package com.wdfall.vslot.excel.parse.test;

import java.util.List;

import com.wdfall.vslot.excel.parse.ParseDataItemTemplate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ParseDataItemDummDefine extends ParseDataItemTemplate {

	private List<String> reelList1;
	
	public List<String> getReelList1() {
		return reelList1;
	}

	public ParseDataItemDummDefine(List<List<String>> excelData, String inputSymbol) {
		super(excelData, inputSymbol);
	}

	@Override
	protected void trimLocation() {
		
	}

	@Override
	protected void handleExcelData() {
		reelList1 = parsedDataList.get(0);
		
		log.info("reelList1 = {}", reelList1);
	}

}

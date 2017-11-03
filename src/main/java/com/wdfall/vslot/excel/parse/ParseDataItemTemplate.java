package com.wdfall.vslot.excel.parse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wdfall.vslot.excel.ExcelUtils;
import com.wdfall.vslot.excel.ExcelUtils.ParamItemLocation;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class ParseDataItemTemplate {
	
	public ParseDataItemTemplate(List<List<String>> excelData, String inputSymbol) {
		super();
		this.excelData = excelData;
		this.inputSymbol = inputSymbol;
	}
	
	// input
	protected List<List<String>> excelData;
	protected String inputSymbol;
	
	// var
	protected ParamItemLocation loc;
	protected ParamItemLocation dataLoc;
	
	protected List<String> headerList;
	
	protected Map<String, List<String>> excelDataMap;
	
	
	public final void parseVertical() {
		// 1.1 loc
		loc = ExcelUtils.findParamItem(excelData, inputSymbol);
		log.info("loc = {}", loc);
		trimLocation();
		
		// 1.2 dataLoc
		dataLoc = loc.getDataLocationDefault();
		log.info("dataLoc = {}", dataLoc);
		
		headerList = ExcelUtils.readExcelDataHorizontal(excelData, loc, loc.getRowIndexStart());
		log.info("headerList = {}", headerList); 
		
		excelDataMap = new HashMap<>();
		for(int cellIndex= dataLoc.getCellIndexStart(); cellIndex<dataLoc.getCellIndexEnd(); cellIndex++ ) {
			List<String> dataList = ExcelUtils.readExcelDataVertical(excelData, dataLoc, cellIndex);
			String key = headerList.get(cellIndex - dataLoc.getCellIndexStart());
			excelDataMap.put(key, dataList);
		}
		
		handleExcelDataMap();
		
	}

	protected abstract void trimLocation();
	protected abstract void handleExcelDataMap();
	
}

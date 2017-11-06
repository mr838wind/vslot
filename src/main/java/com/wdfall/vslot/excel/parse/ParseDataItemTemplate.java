package com.wdfall.vslot.excel.parse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wdfall.vslot.excel.ExcelUtils;
import com.wdfall.vslot.excel.ExcelUtils.ParamItemLocation;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class ParseDataItemTemplate {

	// input
	protected List<List<String>> excelData;
	protected String inputSymbol;
	
	// var
	protected ParamItemLocation loc;
	protected ParamItemLocation dataLoc;
	//
	protected List<String> headerLine;
	protected List<List<String>> parsedDataList;
	
	public ParseDataItemTemplate(List<List<String>> excelData, String inputSymbol) {
		super();
		this.excelData = excelData;
		this.inputSymbol = inputSymbol;
	}

	public final void parseVertical() {
		
		// 1.1 loc
		loc = ExcelUtils.findParamItem(excelData, inputSymbol);
		log.debug("loc = {}", loc);
		
		trimLocation();
		
		// 1.2 dataLoc
		dataLoc = loc.getDataLocationDefault();
		log.debug("dataLoc = {}", dataLoc);
		
		// 2. headerLine
		headerLine = ExcelUtils.readExcelDataHorizontal(excelData, loc, loc.getRowIndexStart());
		log.info("headerLine = {}", headerLine); 
		
		// 3. data
		parsedDataList = new ArrayList<List<String>>();
		for(int offset=0; offset<headerLine.size(); offset++) {
			int cellIndex = dataLoc.getCellIndexStart() + offset;
			List<String> value = ExcelUtils.readExcelDataVertical(excelData, dataLoc, cellIndex);
			parsedDataList.add(value);
		}
		log.debug("parsedDataList = {}", parsedDataList);
		
		// 4. handle
		handleExcelData();
	}

	protected abstract void trimLocation();
	
	protected abstract void handleExcelData();
	
}

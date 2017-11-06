package com.wdfall.vslot.excel.parse;

import java.util.ArrayList;
import java.util.List;

import com.wdfall.vslot.excel.ExcelUtils;
import com.wdfall.vslot.json.SlotGameSettingParam;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ParseDataItemLinePattern extends ParseDataItemTemplate {
	
	private SlotGameSettingParam slotGameSettingParam;

	public ParseDataItemLinePattern(List<List<String>> excelData, SlotGameSettingParam slotGameSettingParam) {
		super(excelData, "linePattern");
		this.slotGameSettingParam = slotGameSettingParam;
	}

	@Override
	protected void trimLocation() {
	}

	@Override
	protected void handleExcelData() {
		
		// get all pattern 
		List<String> linePatternStrAll = new ArrayList<>();
		for(int i=0; i<headerLine.size(); i++) {
			List<String> item = parsedDataList.get(i);
			linePatternStrAll.addAll(item);
		}
		
		// parse :  "1,1,1,1,1"
		List<List<Integer>> linePatternList = new ArrayList<>();
		for(int i=0; i<linePatternStrAll.size(); i++) {
			String patternStr = linePatternStrAll.get(i);
			
			if(! ExcelUtils.isEmpty(patternStr)) {
				List<Integer> pattern = generateLinePattern(patternStr);
				linePatternList.add(pattern);
			}
		}
		
		slotGameSettingParam.setLinePatternList(linePatternList);
		
		log.info("linePatternList = {}", linePatternList); 
	}

	private List<Integer> generateLinePattern(String patternStr) {
		String[] patternStrSep = patternStr.trim().split(",");
		List<Integer> pattern = new ArrayList<>();
		for(String item : patternStrSep) {
			int value = ExcelUtils.parseInt(item);
			pattern.add(value);
		}
		return pattern;
	}
	
}

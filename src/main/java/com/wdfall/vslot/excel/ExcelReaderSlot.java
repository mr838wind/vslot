package com.wdfall.vslot.excel;

import java.io.File;
import java.util.List;

import com.wdfall.vslot.excel.parse.ParseDataItemLinePattern;
import com.wdfall.vslot.excel.parse.ParseDataItemPayoutTableRuleParam;
import com.wdfall.vslot.excel.parse.ParseDataItemReelCompositionParam;
import com.wdfall.vslot.excel.parse.ParseDataItemReelCount;
import com.wdfall.vslot.excel.parse.ParseDataItemReelCountArray;
import com.wdfall.vslot.excel.parse.ParseDataItemSimuSetting;
import com.wdfall.vslot.json.SlotGameSettingParam;
import com.wdfall.vslot.utils.FileUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExcelReaderSlot extends ExcelReader {

	public static final String SLOT_INPUT_SHEET_NAME = "slot_input"; 
	private SlotGameSettingParam slotGameSettingParam;
	
	public ExcelReaderSlot() {
		
	}
	
	@Override
	protected void parseData(List<List<String>> excelData) {
		log.info("parseData start !!");
		slotGameSettingParam = new SlotGameSettingParam();
		
		//
		new ParseDataItemSimuSetting(excelData, slotGameSettingParam).parseVertical();
		
		new ParseDataItemReelCount(excelData, slotGameSettingParam).parseVertical();
		
		new ParseDataItemReelCountArray(excelData, slotGameSettingParam).parseVertical();
		
		new ParseDataItemPayoutTableRuleParam(excelData, slotGameSettingParam).parseVertical();
		
		new ParseDataItemReelCompositionParam(excelData, slotGameSettingParam).parseVertical();
		
		new ParseDataItemLinePattern(excelData, slotGameSettingParam).parseVertical();
		
		log.info("parseData end !!");
	}
	
	
	public SlotGameSettingParam getSlotGameSettingParam() {
		return slotGameSettingParam;
	}

	public static void main(String[] args) throws Exception { 
		File excelUploadFile = FileUtil.getFileOnClasspath("vslot_input_main.xlsx");
		ExcelReaderSlot excelReaderSlot = new ExcelReaderSlot();
		excelReaderSlot.processSheetData(excelUploadFile, SLOT_INPUT_SHEET_NAME);
		SlotGameSettingParam settingParam = excelReaderSlot.getSlotGameSettingParam();
		
		log.info("settingParam = {}", settingParam); 
	}

}

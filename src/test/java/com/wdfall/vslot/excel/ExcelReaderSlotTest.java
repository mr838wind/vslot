package com.wdfall.vslot.excel;

import java.io.File;

import com.wdfall.vslot.json.SlotGameSettingParam;
import com.wdfall.vslot.utils.FileUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExcelReaderSlotTest {

	public static void main(String[] args) throws Exception { 
		File excelUploadFile = FileUtil.getFileOnClasspath("vslot_input.xlsx");
		ExcelReaderSlot excelReaderSlot = new ExcelReaderSlot();
		excelReaderSlot.processSheetData(excelUploadFile, ExcelReader.SHEET_0);
		SlotGameSettingParam settingParam = excelReaderSlot.getSlotGameSettingParam();
		
		log.info("settingParam = {}", settingParam); 
	}
	
	
	
	
}

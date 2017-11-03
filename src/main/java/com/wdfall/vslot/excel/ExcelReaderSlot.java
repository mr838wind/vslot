package com.wdfall.vslot.excel;

import java.io.File;
import java.util.List;

import com.wdfall.vslot.excel.parse.ParseDataItemDummDefine;
import com.wdfall.vslot.excel.parse.ParseDataItemReelDefine;
import com.wdfall.vslot.json.SlotGameSettingParam;
import com.wdfall.vslot.utils.FileUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExcelReaderSlot extends ExcelReader {

	private SlotGameSettingParam slotGameSettingParam;
	
	public ExcelReaderSlot() {
		
	}
	
	@Override
	protected void parseData(List<List<String>> excelData) {
		log.debug("parseData !!");
		slotGameSettingParam = new SlotGameSettingParam();
		
		//1.  {{reel-define}}
		//new ParseDataItemReelDefine(excelData, slotGameSettingParam).parseVertical();
		
		//2.  {{dumm-define}}
		new ParseDataItemDummDefine(excelData).parseVertical();
		
	}

	public SlotGameSettingParam getSlotGameSettingParam() {
		return slotGameSettingParam;
	}
	
	public static void main(String[] args) throws Exception { 
		File excelUploadFile = FileUtil.getFileOnClasspath("vslot_input.xlsx");
		ExcelReaderSlot excelReaderSlot = new ExcelReaderSlot();
		excelReaderSlot.processSheetData(excelUploadFile, ExcelReader.SHEET_0);
		SlotGameSettingParam settingParam = excelReaderSlot.getSlotGameSettingParam();
		
		log.info("settingParam = {}", settingParam); 
	}

}

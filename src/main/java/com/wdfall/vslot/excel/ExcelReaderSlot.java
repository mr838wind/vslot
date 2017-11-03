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
	private List<String> reelList1;
	
	public ExcelReaderSlot() {
		
	}
	
	@Override
	protected void parseData(List<List<String>> excelData) {
		log.debug("parseData !!");
		slotGameSettingParam = new SlotGameSettingParam();
		
		//1.  {{reel-define}}
		ParseDataItemReelDefine reelDefine = new ParseDataItemReelDefine(excelData, "reel-define", slotGameSettingParam);
		reelDefine.parseVertical();
		
		//2.  {{dumm-define}}
		ParseDataItemDummDefine dummDefine = new ParseDataItemDummDefine(excelData, "dumm-define");
		dummDefine.parseVertical();
		reelList1 = dummDefine.getReelList1();
	}
	
	
	public SlotGameSettingParam getSlotGameSettingParam() {
		return slotGameSettingParam;
	}

	public List<String> getReelList1() {
		return reelList1;
	}
	
	
	public static void main(String[] args) throws Exception { 
		File excelUploadFile = FileUtil.getFileOnClasspath("vslot_input.xlsx");
		ExcelReaderSlot excelReaderSlot = new ExcelReaderSlot();
		excelReaderSlot.processSheetData(excelUploadFile, ExcelReader.SHEET_0);
		SlotGameSettingParam settingParam = excelReaderSlot.getSlotGameSettingParam();
		
		log.info("settingParam = {}", settingParam); 
	}

}

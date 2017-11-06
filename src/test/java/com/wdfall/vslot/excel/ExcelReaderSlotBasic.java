package com.wdfall.vslot.excel;

import java.util.List;

import com.wdfall.vslot.excel.parse.test.ParseDataItemDummDefine;
import com.wdfall.vslot.excel.parse.test.ParseDataItemReelDefine;
import com.wdfall.vslot.json.SlotGameSettingParam;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExcelReaderSlotBasic extends ExcelReader {

	private SlotGameSettingParam slotGameSettingParam;
	private List<String> reelList1;
	
	public ExcelReaderSlotBasic() {
		
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
	

}

package com.wdfall.vslot.excel;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wdfall.vslot.json.SlotGameSettingParam;
import com.wdfall.vslot.utils.CloneUtils;
import com.wdfall.vslot.utils.FileUtil;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExcelReaderSlot extends ExcelReader {

	private SlotGameSettingParam slotGameSettingParam;
	
	@Data
	private static class ParamItemLocation {
		//include start , not include end 
		private int rowIndexStart = -1;
		private int rowIndexEnd = -1;
		private int cellIndexStart = -1;
		private int cellIndexEnd = -1;
		
		//
		public ParamItemLocation copy() {
			return CloneUtils.deepCopyByLib(this);
		}
	}
	
	public ExcelReaderSlot() {
		
	}
	
	@Override
	protected void parseData(List<List<String>> excelData) {
		log.info("parseData !!");
		slotGameSettingParam = new SlotGameSettingParam();
		
		//1.  {{reel-define}}
		// 1.1 find start - end
		parseDataItemReelDefine(excelData);
		
		//2.  {{dumm-define}}
		//......
		
	}

	private void parseDataItemReelDefine(List<List<String>> excelData) {
		List<Map<String, Integer>> reelCompositionParamList = new ArrayList<>();
		
		ParamItemLocation loc = findParamItem(excelData, "reel-define");
		
		log.info("loc = {}", loc);
		// 심볼 remove
		// 합계 remove
		ParamItemLocation realLoc = loc.copy();
		realLoc.setCellIndexStart(loc.getCellIndexStart() + 1);
		realLoc.setRowIndexEnd(loc.getRowIndexEnd() - 1);

		
		int rowDataStart = realLoc.getRowIndexStart() + 1;
		int rowDataEnd = realLoc.getRowIndexEnd();
		
		// 1. sysmbolId list
		int ciID = realLoc.getCellIndexStart();
		List<String> symbolIdList = readExcelDataVertical(excelData, ciID, rowDataStart, rowDataEnd);
		
		// 릴1, 릴2
		for(int ciReel=realLoc.getCellIndexStart() + 1; ciReel<realLoc.getCellIndexEnd(); ciReel++) {
			List<String> reel1List = readExcelDataVertical(excelData, ciReel, rowDataStart, rowDataEnd);
			
			Map<String, Integer> reelComp = new HashMap<>();
			for(int i=0; i<symbolIdList.size(); i++) {
				String symbolID = symbolIdList.get(i);
				String countString = reel1List.get(i);
				Integer count = (int)(double)Double.valueOf(countString);
				reelComp.put(symbolID, count);
			}
			reelCompositionParamList.add(reelComp);
		}
		
		slotGameSettingParam.setReelCompositionParamList(reelCompositionParamList );
	}
	
	private List<String> readExcelDataVertical(List<List<String>> excelData, int cellIndex, int rowIndexStart, int rowIndexEnd) {
		List<String> dataList = new ArrayList<>();
		for(int i=rowIndexStart; i<rowIndexEnd; i++) {
			String data = excelData.get(i).get(cellIndex);
			dataList.add(data);
		}
		return dataList;
	}
	
	private List<String> readExcelDataHorizontal(List<List<String>> excelData, int rowIndex, int cellIndexStart, int cellIndexEnd) {
		List<String> dataList = new ArrayList<>();
		for(int i=cellIndexStart; i<cellIndexEnd; i++) {
			String data = excelData.get(rowIndex).get(i);
			dataList.add(data);
		}
		return dataList;
	}

	private ParamItemLocation findParamItem(List<List<String>> excelData, String paramString) {
		ParamItemLocation result = new ParamItemLocation();
		String paramReelDefineStart = String.format("{{%s}}", paramString);
		String paramReelDefineEnd = String.format("{{//%s}}", paramString);
		for(int rowIndex=0; rowIndex<excelData.size(); rowIndex++) {
			List<String> rowData = excelData.get(rowIndex);
			
			for(int cellIndex=0; cellIndex<rowData.size(); cellIndex++) {
				String cellData = rowData.get(cellIndex);
				
				if(paramReelDefineStart.equalsIgnoreCase(cellData)) {
					result.setRowIndexStart(rowIndex + 1);
					result.setCellIndexStart(cellIndex);
				}
				
				findParamItemSetCellIndexEnd(result, rowIndex, rowData, cellIndex, cellData);
				
				if(paramReelDefineEnd.equalsIgnoreCase(cellData)) {
					result.setRowIndexEnd(rowIndex);
					break;
				}
				
			}
		}
		return result;
	}

	private void findParamItemSetCellIndexEnd(ParamItemLocation result, int rowIndex, List<String> rowData,
			int cellIndex, String cellData) {
		//find data header row last cell index 
		int itemDataRow = result.getRowIndexStart();
		if(itemDataRow != -1 && rowIndex == itemDataRow) {
			log.info("({}, {}), cellData = {}", rowIndex, cellIndex, cellData);
			
			if(result.getCellIndexEnd() != -1) { // if not find cellIndexEnd
				boolean isFirstNonNullCell = (cellData == null) || (cellIndex == rowData.size() - 1);
				if(isFirstNonNullCell ) {
					result.setCellIndexEnd(cellIndex + 1);
				}
			}
		}
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

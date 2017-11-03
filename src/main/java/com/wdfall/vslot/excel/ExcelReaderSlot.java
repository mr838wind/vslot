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
	
	public ExcelReaderSlot() {
		
	}
	
	@Override
	protected void parseData(List<List<String>> excelData) {
		log.debug("parseData !!");
		slotGameSettingParam = new SlotGameSettingParam();
		
		//1.  {{reel-define}}
		// 1.1 find start - end
		parseDataItemReelDefine(excelData);
		
		//2.  {{dumm-define}}
		parseDataItemDummDefine(excelData);
		
	}

	private void parseDataItemDummDefine(List<List<String>> excelData) {
		
		// 1.1 loc
		ParamItemLocation loc = findParamItem(excelData, "dumm-define");
		
		// 1.2 dataLoc
		ParamItemLocation dataLoc = loc.getDataLocationDefault();
		log.debug("dataLoc = {}", dataLoc);
		
		// 3. 릴1, 릴2 ...
		int reel1Index = dataLoc.getCellIndexStart();
		List<String> reelList1 = readExcelDataVertical(excelData, dataLoc, reel1Index);
		
		int reel2Index = dataLoc.getCellIndexStart() + 1;
		List<String> reelList2 = readExcelDataVertical(excelData, dataLoc, reel2Index);
		
		log.info("reelList1 = {}", reelList1);
		log.info("reelList2 = {}", reelList2);
	}

	private void parseDataItemReelDefine(List<List<String>> excelData) {
		List<Map<String, Integer>> reelCompositionParamList = new ArrayList<>();
		
		// 1.1 loc
		ParamItemLocation loc = findParamItem(excelData, "reel-define");
		
		// 1.2 trimedLoc: 심볼,합계 remove
		ParamItemLocation trimedLoc = loc.copy();
		trimedLoc.setCellIndexStart(loc.getCellIndexStart() + 1);
		trimedLoc.setRowIndexEnd(loc.getRowIndexEnd() - 1);

		// 1.3 dataLoc
		ParamItemLocation dataLoc = trimedLoc.getDataLocationDefault();
		log.debug("dataLoc = {}", dataLoc);
		
		// 2. sysmbolId list
		int ciID = dataLoc.getCellIndexStart();
		List<String> symbolIdList = readExcelDataVertical(excelData, dataLoc, ciID);
		
		// 3. 릴1, 릴2 ...
		for(int ciReel=dataLoc.getCellIndexStart() + 1; ciReel<dataLoc.getCellIndexEnd(); ciReel++) {
			List<String> reelList = readExcelDataVertical(excelData, dataLoc, ciReel);
			
			Map<String, Integer> reelComp = generateReelCompositionMap(symbolIdList, reelList);
			reelCompositionParamList.add(reelComp);
		}
		
		log.info("{}", reelCompositionParamList); 
		
		slotGameSettingParam.setReelCompositionParamList(reelCompositionParamList );
	}

	
	
	private Map<String, Integer> generateReelCompositionMap(List<String> symbolIdList, List<String> reel1List) {
		Map<String, Integer> reelComp = new HashMap<>();
		for(int i=0; i<symbolIdList.size(); i++) {
			String symbolID = symbolIdList.get(i);
			String countString = reel1List.get(i);
			Integer count = (int)(double)Double.valueOf(countString);
			reelComp.put(symbolID, count);
		}
		return reelComp;
	}
	
	private List<String> readExcelDataVertical(List<List<String>> excelData, ParamItemLocation dataLoc, int cellIndex) {
		int rowIndexStart = dataLoc.getRowIndexStart();
		int rowIndexEnd = dataLoc.getRowIndexEnd();
		List<String> dataList = new ArrayList<>();
		for(int i=rowIndexStart; i<rowIndexEnd; i++) {
			String data = excelData.get(i).get(cellIndex);
			dataList.add(data);
		}
		return dataList;
	}
	
	private List<String> readExcelDataHorizontal(List<List<String>> excelData, ParamItemLocation dataLoc, int rowIndex) {
		int cellIndexStart = dataLoc.getCellIndexStart();
		int cellIndexEnd = dataLoc.getCellIndexEnd();
		List<String> dataList = new ArrayList<>();
		for(int i=cellIndexStart; i<cellIndexEnd; i++) {
			String data = excelData.get(rowIndex).get(i);
			dataList.add(data);
		}
		return dataList;
	}

	/**
	 * <<header포함 데이터 영역 조회>>
	 * 
	 * header영역은 첫번째 row
	 * start포함, end 배제 [row start,end),[cell start,end) 
	 * row start는 {{inputSymbol}} next row 부터
	 * row end는 {{inputSymbol}} 까지
	 * cell start는 {{inputSymbol}}이 있는 cell 부터
	 * cell end는 header영역에서 첫번째 null인 cell 까지 
	 * @param excelData
	 * @param inputSymbol
	 * @return
	 */
	private ParamItemLocation findParamItem(List<List<String>> excelData, String inputSymbol) {
		ParamItemLocation result = new ParamItemLocation();
		String paramReelDefineStart = String.format("{{%s}}", inputSymbol);
		String paramReelDefineEnd = String.format("{{//%s}}", inputSymbol);
		for(int rowIndex=0; rowIndex<excelData.size(); rowIndex++) {
			List<String> rowData = excelData.get(rowIndex);
			
			for(int cellIndex=0; cellIndex<rowData.size(); cellIndex++) {
				String cellData = rowData.get(cellIndex);
				
				// 
				if(paramReelDefineStart.equalsIgnoreCase(cellData)) {
					result.setRowIndexStart(rowIndex + 1);
					result.setCellIndexStart(cellIndex);
				}
				
				//
				if(paramReelDefineEnd.equalsIgnoreCase(cellData)) {
					result.setRowIndexEnd(rowIndex);
					break;
				}
				
			}
		}
		
		//
		findParamItemSetCellIndexEnd(excelData, result);
		
		return result;
	}

	private void findParamItemSetCellIndexEnd(List<List<String>> excelData, ParamItemLocation result) {
		List<String> headerList = excelData.get(result.getRowIndexStart());
		int lastCellOffset = headerList.size();
		for(int i=0; i<headerList.size(); i++) {
			String item = headerList.get(i);
			if(item == null) {
				lastCellOffset = i;
				break;
			}
		}
		int lastCellIndex = lastCellOffset + result.getCellIndexStart();
		result.setCellIndexEnd(lastCellIndex);
	}

	public SlotGameSettingParam getSlotGameSettingParam() {
		return slotGameSettingParam;
	}
	
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
		
		/**
		 * data location default
		 * 1) 첫번째행은 헤더
		 * 2) 나머지는 data
		 * @return
		 */
		// data location 
		// 1) 첫번째행은 헤더
		// 2) 나머지는 data
		public ParamItemLocation getDataLocationDefault() {
			ParamItemLocation dataLoc = CloneUtils.deepCopyByLib(this);
			dataLoc.setRowIndexStart(dataLoc.getRowIndexStart() + 1);
			return dataLoc;
		}
		
	}
	
	public static void main(String[] args) throws Exception { 
		File excelUploadFile = FileUtil.getFileOnClasspath("vslot_input.xlsx");
		ExcelReaderSlot excelReaderSlot = new ExcelReaderSlot();
		excelReaderSlot.processSheetData(excelUploadFile, ExcelReader.SHEET_0);
		SlotGameSettingParam settingParam = excelReaderSlot.getSlotGameSettingParam();
		
		log.info("settingParam = {}", settingParam); 
	}

}

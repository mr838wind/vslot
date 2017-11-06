package com.wdfall.vslot.excel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.poi.ss.formula.functions.T;

import com.wdfall.vslot.utils.CloneUtils;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExcelUtils {

	@Data
	public static class ParamItemLocation {
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
	
	
	public static int parseInt(String data) {
		data = data.replaceAll(",", ""); //숫자표현식중","를 제거
		return (int)(double)Double.valueOf(data);
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
	public static ParamItemLocation findParamItem(List<List<String>> excelData, String inputSymbol) {
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
		
		log.debug("findParamItem = {}", result);
		
		return result;
	}

	public static void findParamItemSetCellIndexEnd(List<List<String>> excelData, ParamItemLocation result) {
		List<String> headerList = excelData.get(result.getRowIndexStart());
		
		int firstNonNullCellIndex = -1;
		int cellStart = result.getCellIndexStart();
		int cellEnd = headerList.size();
		for(int cellIndex=cellStart; cellIndex<cellEnd; cellIndex++) {
			String item = headerList.get(cellIndex);
			if(item == null) {
				firstNonNullCellIndex = cellIndex;
				break;
			}
		}
		if(firstNonNullCellIndex == -1) {
			firstNonNullCellIndex = cellEnd;
		}
		
		result.setCellIndexEnd(firstNonNullCellIndex);
	}
	
	public static List<String> readExcelDataVertical(List<List<String>> excelData, ParamItemLocation dataLoc, int cellIndex) {
		int rowIndexStart = dataLoc.getRowIndexStart();
		int rowIndexEnd = dataLoc.getRowIndexEnd();
		List<String> dataList = new ArrayList<>();
		for(int i=rowIndexStart; i<rowIndexEnd; i++) {
			String data = excelData.get(i).get(cellIndex);
			dataList.add(data);
		}
		return dataList;
	}
	
	public static List<String> readExcelDataHorizontal(List<List<String>> excelData, ParamItemLocation dataLoc, int rowIndex) {
		int cellIndexStart = dataLoc.getCellIndexStart();
		int cellIndexEnd = dataLoc.getCellIndexEnd();
		List<String> dataList = new ArrayList<>();
		for(int i=cellIndexStart; i<cellIndexEnd; i++) {
			String data = excelData.get(rowIndex).get(i);
			dataList.add(data);
		}
		return dataList;
	}
	
	public static boolean isEmpty(String s) {
		return ((s == null) || "".equals(s));
	}
	
	public static boolean isEmptyOrZero(String s) {
		boolean result = false;
		if(isEmpty(s)) {
			result = true;
		} else {
			int i = parseInt(s);
			if(i == 0) {
				result = true;
			}
		}
		return result;
	}
	
}

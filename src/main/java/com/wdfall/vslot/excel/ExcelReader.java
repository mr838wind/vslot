package com.wdfall.vslot.excel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class ExcelReader {
	
	public static final int SHEET_0 = 0;
	
	private Workbook wb;
	
	@Data
	private static class SheetInput {
		private boolean byIndex;
		private int sheetIndex;
		private String sheetName;
	}

	public final void processSheetData(File excelUploadFile, String sheetName) {
		try {
			SheetInput sheetInput = new SheetInput();
			sheetInput.setByIndex(false);
			sheetInput.setSheetName(sheetName); 
			processSheetDataMain(excelUploadFile, sheetInput);
		} catch (Exception e) {
			log.error("",e); 
			throw new RuntimeException("excel reader exception", e);
		}
	}
	
	/**
	 * process sheet and return data
	 * @param excelUploadFile
	 * @param sheetIndex
	 * @return
	 * @throws Exception
	 */
	public final void processSheetData(File excelUploadFile, int sheetIndex) throws Exception {
		SheetInput sheetInput = new SheetInput();
		sheetInput.setByIndex(true);
		sheetInput.setSheetIndex(sheetIndex);
		processSheetDataMain(excelUploadFile, sheetInput);
	}
	
	
	public final void processSheetDataMain(File excelUploadFile, SheetInput sheetInput) throws Exception {
    	log.info("excel process start !!");
		
		if (excelUploadFile == null || !excelUploadFile.exists()) {
			throw new IllegalArgumentException("file not exist.");
		}
		
		List<List<String>> excelData = null;
		wb = null;
		
		try {
			wb = WorkbookFactory.create(excelUploadFile); //엑셀 파일 오픈
			
			Sheet sheet = null;
			if(sheetInput.isByIndex()) {
				sheet = wb.getSheetAt(sheetInput.getSheetIndex());
			} else {
				sheet = wb.getSheet(sheetInput.getSheetName());
			}
			
			excelData = readSheetData(sheet);
			
			parseData(excelData);
			
		} finally {
			closeWorkbook(wb);
		}
		
		log.info("excel process end !!");
	}

	/**
	 * data 처리
	 * @param excelData
	 */
	protected abstract void parseData(List<List<String>> excelData);

	
	
	private List<List<String>> readSheetData(Sheet sheet) {
		List<List<String>> excelData = new ArrayList<>();	
		
		for( Row row : sheet) {
			List<String> rowData = new ArrayList<>();
			int maxColIndex = row.getLastCellNum();
			
			for(int cellIndex=0; cellIndex<maxColIndex; cellIndex++) {
				Cell cell = row.getCell(cellIndex);
				String cellValue = readCellValue(cell);
        		rowData.add(cellValue);
			}
			
        	excelData.add(rowData);
        }
        
		logData(excelData);
        return excelData;
	}
	
	/**
	 * 셀의 타입 따라 받아서 구분지어 받되 한 cell을 하나의 스트링으로 저장
	 */
	private String readCellValue(Cell cell) {
		String str = null;
		
		if(cell == null) {
			return null;
		}
		
		// formula 계산된 값을 read 함
		FormulaEvaluator evaluator = wb.getCreationHelper().createFormulaEvaluator();
		evaluator.evaluateInCell(cell);
		
		// 
		switch ( cell.getCellTypeEnum() ) {
		    case STRING:
		        str = cell.getStringCellValue();
		        break;

		    case NUMERIC :
		        str = String.valueOf(cell.getNumericCellValue());
		        break;
		        
		    case BOOLEAN :
		        str = String.valueOf(cell.getBooleanCellValue());
		        break;

		     // CELL_TYPE_FORMULA will never happen when using FormulaEvaluator
		    case FORMULA :
		        //str = String.valueOf(cell.getCellFormula());
		        break;

		    default:
		        str = null;
		}
		
		return str;
	}
	
	private void closeWorkbook(Workbook wb) throws IOException {
		if(wb != null) {
			wb.close();
		}
	}
	
	private void logData(List<List<String>> excelData) {
		log.debug(">>>> excelData.size = {}", excelData.size()); 
		for(List<String> item : excelData) {
			log.debug("{}", item);
		}
	}
    
}

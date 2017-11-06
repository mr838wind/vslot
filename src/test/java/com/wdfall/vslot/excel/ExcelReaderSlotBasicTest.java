package com.wdfall.vslot.excel;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.wdfall.vslot.json.SlotGameSettingParam;
import com.wdfall.vslot.utils.FileUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExcelReaderSlotBasicTest {

	@Before
	public void setUp() {
		
	}
	
	@Test
	public void testExcelReader() throws Exception {
		File excelUploadFile = FileUtil.getFileOnClasspath("vslot_input_test.xlsx");
		ExcelReaderSlotBasic excelReaderSlot = new ExcelReaderSlotBasic();
		excelReaderSlot.processSheetData(excelUploadFile, ExcelReader.SHEET_0);
		SlotGameSettingParam settingParam = excelReaderSlot.getSlotGameSettingParam();
		
		log.info("settingParam = {}", settingParam);
		
		//0-[{MM=4, SC=2, A=2, Q=3, SH=4, W=1, J=2, G1=3, K=3, G2=4, G5=4}, 
		//1-{MM=2, SC=1, A=3, Q=3, SH=3, W=1, J=3, G1=4, K=3, G2=4, G5=5}, 
		//2-{MM=3, SC=1, A=2, Q=3, SH=4, W=1, J=3, G1=4, K=3, G2=4, G5=4}, 
		//3-{MM=3, SC=1, A=2, Q=2, SH=3, W=1, J=3, G1=4, K=4, G2=5, G5=4}, 
		//4-{MM=3, SC=1, A=3, Q=3, SH=4, W=1, J=4, G1=3, K=4, G2=3, G5=3}]
		List<Map<String, Integer>> result1 = settingParam.getReelCompositionParamList();
		Assert.assertTrue(result1.get(0).get("MM") == 4);
		Assert.assertTrue(result1.get(0).get("SC") == 2);
		//
		Assert.assertTrue(result1.get(1).get("A") == 3);
		Assert.assertTrue(result1.get(2).get("SH") == 4);
		Assert.assertTrue(result1.get(3).get("J") == 3);
		Assert.assertTrue(result1.get(4).get("G5") == 3);
		
		//reelList1 = [1.0, 2.0, 2.0, 3.0, 3.0, 4.0, 4.0, 3.0, 4.0, 4.0]
		List<String> reelList1 = excelReaderSlot.getReelList1();
		Assert.assertTrue((int)(double)Double.valueOf(reelList1.get(0)) == 1);
		Assert.assertTrue((int)(double)Double.valueOf(reelList1.get(7)) == 3);
		Assert.assertTrue((int)(double)Double.valueOf(reelList1.get(9)) == 4);
		
	}
	
	
	
}

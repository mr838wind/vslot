package com.wdfall.vslot;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import com.wdfall.vslot.game.SlotGameRegular;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SlotSimulatorTestExcel {
	
	private static final String EXCEL_FILE_PATH = "vslot_input_main.xlsx";
	
	/**
	 * log4j level ---> INFO
	 */
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testIntegrationBasic() throws Exception {
		// using the same setting with slot_game_setting_param_10_basic.json
		commonTest(EXCEL_FILE_PATH, "slot_input_3v3", 145.0805, 1);
	}

	private void commonTest(String excelFilePath, String sheetName, double payoutExpected, double maxDiff) throws Exception {
		SlotSimulator slotSimulator = new SlotSimulator(excelFilePath, sheetName);
		slotSimulator.setPayoutExpected(payoutExpected);
		slotSimulator.startWithThread(SlotGameRegular.class);
		
		Assert.assertTrue( slotSimulator.getDifference() < maxDiff );
	}
	
	
}

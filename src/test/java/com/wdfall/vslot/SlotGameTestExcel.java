package com.wdfall.vslot;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SlotGameTestExcel {
	
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
		commonTest(EXCEL_FILE_PATH, 145.0805, 1);
	}

	private void commonTest(String excelFilePath, double payoutExpected, double maxDiff) throws Exception {
		SlotSimulator slotSimulator = new SlotSimulator(excelFilePath);
		slotSimulator.setPayoutExpected(payoutExpected);
		slotSimulator.startWithThread();
		
		Assert.assertTrue( slotSimulator.getDifference() < maxDiff );
	}
	
	
}

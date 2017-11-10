package com.wdfall.vslot;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import com.wdfall.vslot.game.SlotGameStage1;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SlotSimulatorTestStage1 {
	
private static final String EXCEL_FILE_PATH = "vslot_input_main.xlsx";
	
	/**
	 * log4j level ---> INFO
	 */
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testStage1() throws Exception {
		SlotSimulator slotSimulator = new SlotSimulator(EXCEL_FILE_PATH, "slot_input_stage1");
		//slotSimulator.setPayoutExpected(payoutExpected);
		slotSimulator.startWithThread(SlotGameStage1.class);
		
		//Assert.assertTrue( slotSimulator.getDifference() < maxDiff );
	}
	
	
}

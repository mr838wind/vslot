package com.wdfall.vslot;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import com.wdfall.vslot.game.SlotGameRegular;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SlotSimulatorTestStage1 {
	
	private static final String JSON_FILE_PATH = "slot_game_setting_param_stage1.json";
	
	/**
	 * log4j level ---> INFO
	 */
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testIntegrationBasic() throws Exception {
		commonTest(JSON_FILE_PATH, 145.0805, 2);
	}

	private void commonTest(String jsonFilePath, double payoutExpected, double maxDiff) throws Exception {
		SlotSimulator slotSimulator = new SlotSimulator(jsonFilePath);
		slotSimulator.setPayoutExpected(payoutExpected);
		slotSimulator.startWithThread(SlotGameRegular.class);
		
		Assert.assertTrue( slotSimulator.getDifference() < maxDiff );
	}
	
	
}

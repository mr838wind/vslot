package com.wdfall.vslot;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SlotGameTestIntegration {
	
	private static final String JSON_FILE_PATH_BASIC = "slot_game_setting_param_10_basic.json";
	private static final String JSON_FILE_PATH_PPT = "slot_game_setting_param_20_ppt.json";
	
	@InjectMocks
	@Spy
	SlotGameRegular game;
	
	/**
	 * log4j level ---> INFO
	 */
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testIntegrationBasic() throws Exception {
		commonTest(JSON_FILE_PATH_BASIC, 145.0805, 1);
	}

	@Test
	public void testIntegrationPpt() throws Exception {
		commonTest(JSON_FILE_PATH_PPT, 92.46, 10);
	}
	
	
	private void commonTest(String jsonFilePath, double payoutExpected, double maxDiff) throws Exception {
		SlotSimulator slotSimulator = new SlotSimulator(jsonFilePath);
		slotSimulator.setPayoutExpected(payoutExpected);
		slotSimulator.startWithThread(SlotGameRegular.class);
		
		Assert.assertTrue( slotSimulator.getDifference() < maxDiff );
	}
	
	
}

package com.wdfall.vslot.compare_with_excel;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SlotGameTestLogicCompareWithExcelTestDolphin extends SlotGameTestLogicCompareWithExcel {
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}
	
	/**
	 * log4j level ---> INFO
	 */
	
	/**
	 * <<<< dolphin test >>>>
	 * count: 	pay out,  		time
	 * m: 		95.1414, 		14,337 ms (14s)
	 * m10: 	95.60404 , 		127,305 ms (2min)
	 * m100: 	95.712947, 		1,262,467 ms (21min)
	 */
	@Test
	public void testIntegrationDolphin() throws Exception {
		testSlot(JSON_FILE_PATH_DOLPHIN, EXPECTED_DOLPHIN, THREAD_COUNT_ONE, SPIN_COUNT_PER_GAME_MILLION);
		//testSlot(JSON_FILE_PATH_DOLPHIN, EXPECTED_DOLPHIN, THREAD_COUNT_ONE, SPIN_COUNT_PER_GAME_MILLION_10);
		//testSlot(JSON_FILE_PATH_DOLPHIN, EXPECTED_DOLPHIN, THREAD_COUNT_ONE, SPIN_COUNT_PER_GAME_MILLION_100);
		//testSlot(JSON_FILE_PATH_DOLPHIN, EXPECTED_DOLPHIN, THREAD_COUNT_ONE, SPIN_COUNT_PER_GAME_MILLION_1000);
	}
	
	
}

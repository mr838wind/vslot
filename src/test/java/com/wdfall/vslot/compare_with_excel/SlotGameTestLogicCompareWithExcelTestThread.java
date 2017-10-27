package com.wdfall.vslot.compare_with_excel;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SlotGameTestLogicCompareWithExcelTestThread extends SlotGameTestLogicCompareWithExcel {
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}
	
	/**
	 * log4j level ---> INFO
	 */
	
	
	/* =========================  thread test ============================= */
	
	/**
	 * 1 thread is the fast.
	 * <<<< thread test >>>>
	 * payoutExpected = 158.8439 
	 * all run count = SPIN_COUNT_PER_GAME_MILLION_10
	 * threadCount: pay out, difference, time
	 * 1: 		158.8834, 	0.039500000000003865,	29,271 ms
	 * 2: 		158.94315, 	0.09925000000001205,	30,967 ms 
	 * 4: 		158.7614, 	0.08249999999998181,	40,861 ms
	 * 10: 		158.2428, 	0.6011000000000024,		52,511 ms
	 */
	@Test
	public void testIntegrationScatterByThread() throws Exception {
		// 경우의 수: 32,768
		testSlot(JSON_FILE_PATH_SCATTER, EXPECTED_SCATTER, THREAD_COUNT_ONE, SPIN_COUNT_PER_GAME_MILLION_10);
		testSlot(JSON_FILE_PATH_SCATTER, EXPECTED_SCATTER, 2, SPIN_COUNT_PER_GAME_MILLION_10 / 2);
		testSlot(JSON_FILE_PATH_SCATTER, EXPECTED_SCATTER, 4, SPIN_COUNT_PER_GAME_MILLION_10 / 4);
		testSlot(JSON_FILE_PATH_SCATTER, EXPECTED_SCATTER, 10, SPIN_COUNT_PER_GAME_MILLION_10 / 10);
	}
	
	
	
}

package com.wdfall.vslot.compare_with_excel;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SlotGameTestLogicCompareWithExcelTestDifference extends SlotGameTestLogicCompareWithExcel {
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}
	
	/**
	 * log4j level ---> INFO
	 */
	
	
	/* =========================  difference test ============================= */
	
	/**
	 * <<<< difference test >>>>
	 * payoutExpected = 158.8439 
	 * count: pay out, difference, time, runCount/경우의 수
	 * m: 		159.657, 	0.8131000000000199,		3,555 ms, 				30
	 * m10: 	158.8816, 	0.037700000000000955,	27,556 ms, 				300
	 * m100: 	158.799895, 0.04400499999999852,	266,275 ms -- 4min,		3000
	 * m1000: 	158.85422, 	0.010320000000007212,	2,663,074 ms -- 40min,	30000
	 * t4-m1000: 	158.81821300000001 , 	0.02568699999997648,	7,050,986 ms -- 117min
	 */
	@Test
	public void testIntegrationScatterBySpinCount() throws Exception {
		// 경우의 수: 32,768
		testSlot(JSON_FILE_PATH_SCATTER, EXPECTED_SCATTER, THREAD_COUNT_ONE, SPIN_COUNT_PER_GAME_MILLION);
		//testSlot(JSON_FILE_PATH_SCATTER, EXPECTED_SCATTER, THREAD_COUNT_ONE, SPIN_COUNT_PER_GAME_MILLION_10);
		//testSlot(JSON_FILE_PATH_SCATTER, EXPECTED_SCATTER, THREAD_COUNT_ONE, SPIN_COUNT_PER_GAME_MILLION_100);
		//testSlot(JSON_FILE_PATH_SCATTER, EXPECTED_SCATTER, THREAD_COUNT_ONE, SPIN_COUNT_PER_GAME_MILLION_1000);
		//testSlot(JSON_FILE_PATH_SCATTER, EXPECTED_SCATTER, THREAD_COUNT_ONE, SPIN_COUNT_PER_GAME_MILLION_1000_10);
		//testSlot(JSON_FILE_PATH_SCATTER, EXPECTED_SCATTER, 4, SPIN_COUNT_PER_GAME_MILLION_1000/4);
	}
	
	
	
}

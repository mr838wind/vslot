package com.wdfall.vslot;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SlotGameTestLogicCompareWithExcel {
	
	private static final double EXPECTED_SIMPLE = 129.6296;
	private static final double EXPECTED_WILD = 156.5909;
	private static final double EXPECTED_SCATTER = 158.8439;
	private static final double EXPECTED_3V4 = 504.2076;
	/*
	 * 10*1000*1000 일때 1% 차이안에 있음. ---- (실행속도 좀 늦음)
	 * 1000*1000 도 1~2%내에 있음.---- (실행속도 빠름)
	 */
	private static final int THREAD_COUNT_ONE = 1;
	private static final int SPIN_COUNT_PER_GAME_MILLION = 1*1000*1000;
	private static final int SPIN_COUNT_PER_GAME_MILLION_10 = 10*1000*1000;
	private static final int SPIN_COUNT_PER_GAME_MILLION_100 = 100*1000*1000;
	private static final int SPIN_COUNT_PER_GAME_MILLION_1000 = 1000*1000*1000;
	private static final int SPIN_COUNT_PER_GAME_MILLION_1000_10 = 10*1000*1000*1000;
	//
	private static final int VALUE_MAX_DIFFERENCE = 1;
	/*
	 * 
	 */
	private static final String JSON_FILE_PATH_SIMPLE = "param/slot_game_setting_param_10_1_simple.json";
	private static final String JSON_FILE_PATH_WILD = "param/slot_game_setting_param_10_2_add_wild.json";
	// 경우의 수: 32,768
	private static final String JSON_FILE_PATH_SCATTER = "param/slot_game_setting_param_10_3_add_scatter.json";
	// 경우의 수 : 1,048,576
	private static final String JSON_FILE_PATH_3V4 = "param/slot_game_setting_param_11_3v4.json";
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}
	
	/**
	 * log4j level ---> INFO
	 */
	
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
	
	
	/**
	 * <<<< difference test >>>>
	 * payoutExpected = 158.8439 
	 * count: pay out, difference, time
	 * m: 		159.657, 	0.8131000000000199,		3,555 ms
	 * m10: 	158.8816, 	0.037700000000000955,	27,556 ms 
	 * m100: 	158.799895, 0.04400499999999852,	266,275 ms -- 4min
	 * m1000: 	158.85422, 	0.010320000000007212,	2,663,074 ms -- 40min
	 * t4-m1000: 	158.81821300000001 , 	0.02568699999997648,	7,050,986 ms -- 117min
	 */
	@Test
	public void testIntegrationScatterBySpinCount() throws Exception {
		// 경우의 수: 32,768
		//testSlot(JSON_FILE_PATH_SCATTER, EXPECTED_SCATTER, THREAD_COUNT_ONE, SPIN_COUNT_PER_GAME_MILLION);
		//testSlot(JSON_FILE_PATH_SCATTER, EXPECTED_SCATTER, THREAD_COUNT_ONE, SPIN_COUNT_PER_GAME_MILLION_10);
		//testSlot(JSON_FILE_PATH_SCATTER, EXPECTED_SCATTER, THREAD_COUNT_ONE, SPIN_COUNT_PER_GAME_MILLION_100);
		//testSlot(JSON_FILE_PATH_SCATTER, EXPECTED_SCATTER, THREAD_COUNT_ONE, SPIN_COUNT_PER_GAME_MILLION_1000);
		//testSlot(JSON_FILE_PATH_SCATTER, EXPECTED_SCATTER, THREAD_COUNT_ONE, SPIN_COUNT_PER_GAME_MILLION_1000_10);
		//testSlot(JSON_FILE_PATH_SCATTER, EXPECTED_SCATTER, 4, SPIN_COUNT_PER_GAME_MILLION_1000/4);
	}
	
	
	/*
	 * 3*3
	 */
	@Test
	public void testIntegrationSimple() throws Exception {
		testSlot(JSON_FILE_PATH_SIMPLE, EXPECTED_SIMPLE, THREAD_COUNT_ONE, SPIN_COUNT_PER_GAME_MILLION);
	}
	
	@Test
	public void testIntegrationWild() throws Exception {
		testSlot(JSON_FILE_PATH_WILD, EXPECTED_WILD, THREAD_COUNT_ONE, SPIN_COUNT_PER_GAME_MILLION);
	}
	
	@Test
	public void testIntegrationScatter() throws Exception {
		// 경우의 수: 32,768
		testSlot(JSON_FILE_PATH_SCATTER, EXPECTED_SCATTER, THREAD_COUNT_ONE, SPIN_COUNT_PER_GAME_MILLION);
	}
	
	/*
	 * 3*4
	 */
	@Test
	public void testIntegration3V4() throws Exception {
		// 경우의 수 : 1,048,576
		testSlot(JSON_FILE_PATH_3V4, EXPECTED_3V4, THREAD_COUNT_ONE, SPIN_COUNT_PER_GAME_MILLION_100);
	}
	
	
	/*
	 * 
	 */
	private void testSlot(String JSON_FILE_PATH, double EXPECTED, int THREAD_COUNT, int SPIN_COUNT_PER_GAME) {
		try {
			SlotSimulator slotSimulator = new SlotSimulator(JSON_FILE_PATH, THREAD_COUNT, SPIN_COUNT_PER_GAME);
			slotSimulator.setPayoutExpected(EXPECTED);
			slotSimulator.startWithThread();
			Assert.assertTrue( slotSimulator.getDifference() < VALUE_MAX_DIFFERENCE); 
		} catch (Exception e) {
			log.error("",e); 
		}
		
	}
	
}

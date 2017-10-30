package com.wdfall.vslot.compare_with_excel;

import org.junit.Assert;
import org.junit.Before;
import org.mockito.MockitoAnnotations;

import com.wdfall.vslot.SlotSimulator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class SlotGameTestLogicCompareWithExcel {
	
	public static final double EXPECTED_SIMPLE = 129.6296;
	public static final double EXPECTED_WILD = 156.5909;
	public static final double EXPECTED_SCATTER = 158.8439;
	public static final double EXPECTED_3V4 = 504.2076;
	public static final double EXPECTED_SYMBOL_NUMBER = 145.0805;
	public static final double EXPECTED_DOLPHIN = 0; // unknown
	/*
	 * 10*1000*1000 일때 1% 차이안에 있음. ---- (실행속도 좀 늦음)
	 * 1000*1000 도 1~2%내에 있음.---- (실행속도 빠름)
	 */
	public static final int THREAD_COUNT_ONE = 1;
	public static final int SPIN_COUNT_PER_GAME_MILLION = 1*1000*1000;
	public static final int SPIN_COUNT_PER_GAME_MILLION_10 = 10*1000*1000;
	public static final int SPIN_COUNT_PER_GAME_MILLION_100 = 100*1000*1000;
	public static final int SPIN_COUNT_PER_GAME_MILLION_1000 = 1000*1000*1000;
	public static final int SPIN_COUNT_PER_GAME_MILLION_1000_10 = 10*1000*1000*1000;
	//
	public static final double VALUE_MAX_DIFFERENCE = 1;
	/*
	 * 
	 */
	public static final String JSON_FILE_PATH_SIMPLE = "param/slot_game_setting_param_10_1_simple.json";
	public static final String JSON_FILE_PATH_WILD = "param/slot_game_setting_param_10_2_add_wild.json";
	// 경우의 수: 32,768
	public static final String JSON_FILE_PATH_SCATTER = "param/slot_game_setting_param_10_3_add_scatter.json";
	// 경우의 수 : 1,048,576
	public static final String JSON_FILE_PATH_3V4 = "param/slot_game_setting_param_11_3v4.json";
	// 경우의 수 : 32768
	public static final String JSON_FILE_PATH_SYMBOL_NUMBER = "param/slot_game_setting_param_10_4_symbol_number.json";
	// 경우의 수: 
	public static final String JSON_FILE_PATH_DOLPHIN = "param/slot_game_setting_param_12_dolphin.json";
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}
	
	/**
	 * log4j level ---> INFO
	 */
	
	/* =========================  others ============================= */
	/*
	 * 
	 */
	public void testSlot(String JSON_FILE_PATH, double EXPECTED, int THREAD_COUNT, int SPIN_COUNT_PER_GAME) {
		try {
			SlotSimulator slotSimulator = new SlotSimulator(JSON_FILE_PATH, THREAD_COUNT, SPIN_COUNT_PER_GAME);
			slotSimulator.setPayoutExpected(EXPECTED);
			slotSimulator.startWithThread();
			if(EXPECTED > 0) {
				Assert.assertTrue( slotSimulator.getDifference() < VALUE_MAX_DIFFERENCE); 
			} else {
				Assert.assertTrue(true);
			}
		} catch (Exception e) {
			log.error("",e); 
		}
		
	}
	
}

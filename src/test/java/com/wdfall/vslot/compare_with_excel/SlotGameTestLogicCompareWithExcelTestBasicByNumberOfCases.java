package com.wdfall.vslot.compare_with_excel;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SlotGameTestLogicCompareWithExcelTestBasicByNumberOfCases extends SlotGameTestLogicCompareWithExcel {
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}
	
	private static final int numberOfCases3v3= 32768;
	private static final int numberOfCases3v4= 1048576;
	//=========
	private int RUN_COUNT_3V3 = numberOfCases3v3 * 10;
	private int RUN_COUNT_3V4 = numberOfCases3v4 * 300;
			
	
	
	/**
	 * log4j level ---> INFO
	 */
	
	
	/* =========================  basic test ============================= */
	/*
	 * 3*3
	 */
	@Test
	public void testIntegrationSimple() throws Exception {
		testSlot(JSON_FILE_PATH_SIMPLE, EXPECTED_SIMPLE, THREAD_COUNT_ONE, RUN_COUNT_3V3);
	}
	
	@Test
	public void testIntegrationWild() throws Exception {
		testSlot(JSON_FILE_PATH_WILD, EXPECTED_WILD, THREAD_COUNT_ONE, RUN_COUNT_3V3);
	}
	
	@Test
	public void testIntegrationScatter() throws Exception {
		// 경우의 수: 32,768
		testSlot(JSON_FILE_PATH_SCATTER, EXPECTED_SCATTER, THREAD_COUNT_ONE, RUN_COUNT_3V3);
	}
	
	/*
	 * 
	 */
	@Test
	public void testIntegrationSymbolNumber() throws Exception {
		// 경우의 수: 32,768
		testSlot(JSON_FILE_PATH_SYMBOL_NUMBER, EXPECTED_SYMBOL_NUMBER, THREAD_COUNT_ONE, RUN_COUNT_3V3);
	}
	
	
	/*
	 * 3*4
	 */
	@Test
	public void testIntegration3V4() throws Exception {
		// 경우의 수 : 1,048,576
		testSlot(JSON_FILE_PATH_3V4, EXPECTED_3V4, THREAD_COUNT_ONE, RUN_COUNT_3V4);
	}
	
	
	
}

package com.wdfall.vslot.payout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.wdfall.vslot.pay_result.PayResultOne;

public class PayoutTableRuleTest {
	
	private static final int LINE_NUM_0 = 0;

	private static final int HA_4_PAY = 100;

	
	@Before
	public void setUp() {
		
	}

	@Test
	public void testNormalSymbol() {
		Map<Integer, Integer> countPayMapHA = new HashMap<>();
		countPayMapHA.put(3, 10);
		countPayMapHA.put(4, HA_4_PAY);
		countPayMapHA.put(5, 200);
		PayoutTableRule rule = new PayoutTableRuleNormalSymbol("HA", countPayMapHA);
		
		
		PayResultOne currentPayResult = new PayResultOne();
		rule.calculate(Arrays.asList("HA", "HA","HA", "HA","MA"), currentPayResult, LINE_NUM_0);
		long pay1 = currentPayResult.getLinePay(LINE_NUM_0);
		Assert.assertTrue((pay1 == HA_4_PAY));
		
		currentPayResult = new PayResultOne();
		rule.calculate(Arrays.asList("MA", "MA","MA", "MA","MA"), currentPayResult, LINE_NUM_0);
		long pay2 = currentPayResult.getLinePay(LINE_NUM_0);
		Assert.assertTrue((pay2 == 0));
	}
	
	@Test
	public void testWildSymbol() {
		List<PayoutTableRule> payoutTableRuleList = new ArrayList<>();
		Map<Integer, Integer> countPayMapHA = new HashMap<>();
		countPayMapHA.put(3, 10);
		countPayMapHA.put(4, HA_4_PAY);
		countPayMapHA.put(5, 200);
		PayoutTableRuleNormalSymbol normalSymbolRuleHA = new PayoutTableRuleNormalSymbol("HA", countPayMapHA);
		payoutTableRuleList.add(normalSymbolRuleHA);
		
		Map<Integer, Integer> countPayMapMA = new HashMap<>();
		countPayMapMA.put(3, 5);
		countPayMapMA.put(4, 50);
		countPayMapMA.put(5, 100);
		PayoutTableRuleNormalSymbol normalSymbolRuleMA = new PayoutTableRuleNormalSymbol("MA", countPayMapMA);
		payoutTableRuleList.add(normalSymbolRuleMA);
		
		List<PayoutTableRule> normalSymbolRuleList = new ArrayList<>();
		normalSymbolRuleList.add(normalSymbolRuleHA);
		normalSymbolRuleList.add(normalSymbolRuleMA);
		
		//
		List<String> normalSymbolList = Arrays.asList("HA","MA"); 
		// WD
		Map<Integer, Integer> countPayMapWD = new HashMap<>();
		int WD_3_PAY = 50;
		countPayMapWD.put(3, WD_3_PAY);
		countPayMapWD.put(4, 1000);
		countPayMapWD.put(5, 5000);
		PayoutTableRule rule = new PayoutTableRuleWildSymbol("WD", countPayMapWD, normalSymbolList , normalSymbolRuleList);
		
		PayResultOne currentPayResult = new PayResultOne();
		
		rule.calculate(Arrays.asList("HA", "HA","HA", "HA","MA"), currentPayResult, LINE_NUM_0);
		long pay1 = currentPayResult.getLinePay(LINE_NUM_0);
		Assert.assertTrue((pay1 == 0));
		
		currentPayResult = new PayResultOne();
		rule.calculate(Arrays.asList("WD", "HA","HA", "HA","MA"), currentPayResult, LINE_NUM_0);
		long pay2 = currentPayResult.getLinePay(LINE_NUM_0);
		Assert.assertTrue((pay2 == HA_4_PAY));
	
		currentPayResult = new PayResultOne();
		rule.calculate(Arrays.asList("WD", "WD","WD", "HA","MA"), currentPayResult, LINE_NUM_0);
		long pay3 = currentPayResult.getLinePay(LINE_NUM_0);
		Assert.assertTrue((pay3 == Math.max(WD_3_PAY, HA_4_PAY)));
	}
	
	@Test
	public void testScatterSymbol() {
		int PAY_4 = 400;
		Map<Integer, Integer> countPayMap = new HashMap<>();
		countPayMap.put(3, 300);
		countPayMap.put(4, PAY_4);
		countPayMap.put(5, 500);
		PayoutTableRuleScatter rule = new PayoutTableRuleScatterSymbol("SS", countPayMap);
		
		String[][] reelShowArrayScatter2 = new String[][] {
			new String[]{"HA","MA","MB"},
			new String[]{"HA","MA","MB"},
			new String[]{"SS","MA","MB"},
			new String[]{"MA","SS","MB"},
			new String[]{"MB","MA","MB"}
		};
		
		String[][] reelShowArrayScatter4 = new String[][] {
			new String[]{"HA","MA","SS"},
			new String[]{"HA","MA","SS"},
			new String[]{"SS","MA","MB"},
			new String[]{"MA","SS","MB"},
			new String[]{"MB","MA","MB"}
		};
		
		PayResultOne currentPayResult = new PayResultOne();
		
		rule.calculate(reelShowArrayScatter2, currentPayResult );
		long pay1 = currentPayResult.getScatterPay();
		Assert.assertTrue((pay1 == 0));
		
		currentPayResult = new PayResultOne();
		rule.calculate(reelShowArrayScatter4, currentPayResult);
		long pay2 = currentPayResult.getScatterPay();
		Assert.assertTrue((pay2 == PAY_4));
	}
	
}

package com.wdfall.vslot.payout;

import java.util.Map;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PayoutTableRuleScatterSymbol implements PayoutTableRuleScatter {

	private String symbol;
	private Map<Integer,Integer> countPayMap;

	public PayoutTableRuleScatterSymbol(String symbol, Map<Integer, Integer> countPayMap) {
		super();
		this.symbol = symbol;
		this.countPayMap = countPayMap;
	}
	
	@Override
	public int calculate(final String[][] reelShowArray) {
		int pay = 0;
		
		// count all scatter in show 
		int matchCount = 0;
		for(int i=0; i<reelShowArray.length; i++) {
			String[] array = reelShowArray[i];
			for(int j=0; j<array.length; j++) {
				String item = array[j];
				if(symbol.equals(item)) {
					matchCount++;
				}
			}
		}
		
		if(countPayMap.containsKey(matchCount)) {
			pay = countPayMap.get(matchCount);
			log.debug(" <<win scatter>> symbol = {}, count = {}, pay = {}", symbol, matchCount, pay);
		}
		
		return pay;
	}
	
}
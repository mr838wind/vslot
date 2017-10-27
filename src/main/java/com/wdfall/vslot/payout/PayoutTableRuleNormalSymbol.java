package com.wdfall.vslot.payout;

import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PayoutTableRuleNormalSymbol implements PayoutTableRule {

	private String symbol;
	private Map<Integer,Integer> countPayMap;

	public PayoutTableRuleNormalSymbol(String symbol, Map<Integer, Integer> countPayMap) {
		super();
		this.symbol = symbol;
		this.countPayMap = countPayMap;
	}
	
	@Override
	public int calculate(final List<String> currentResult) {
		int pay = 0;
		// leftmost to rightmost match result
		int matchCount = 0;
		for(int i=0; i<currentResult.size(); i++) {
			if(symbol.equals(currentResult.get(i))) {
				matchCount++;
			} else {
				break;
			}
		}
		
		if(countPayMap.containsKey(matchCount)) {
			pay = countPayMap.get(matchCount);
			log.debug(" <<win>> symbol = {}, count = {}, pay = {}", symbol, matchCount, pay);
		}
		return pay;
	}
	
}
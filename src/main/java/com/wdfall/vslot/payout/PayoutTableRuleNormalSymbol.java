package com.wdfall.vslot.payout;

import java.util.List;
import java.util.Map;

import com.wdfall.vslot.pay_result.PayResultItem;
import com.wdfall.vslot.pay_result.PayResultOne;
import com.wdfall.vslot.utils.SlotUtils;

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
	public void calculate(final List<String> currentResult, PayResultOne currentPayResult, int lineNum ) {
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
			
			//
			PayResultItem normalItem = new PayResultItem();
			normalItem.setName(SlotUtils.getPayResultItemName(symbol, matchCount));
			normalItem.setCount(1);
			normalItem.setPay(pay);
			currentPayResult.setLineResult(lineNum, normalItem);
			
		}
		
	}
	
}
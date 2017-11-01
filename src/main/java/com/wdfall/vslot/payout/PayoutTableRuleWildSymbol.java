package com.wdfall.vslot.payout;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.wdfall.vslot.pay_result.PayResultItem;
import com.wdfall.vslot.pay_result.PayResultOne;
import com.wdfall.vslot.utils.PermutationsWithRepeat;
import com.wdfall.vslot.utils.SlotUtils;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PayoutTableRuleWildSymbol implements PayoutTableRule {

	private String wildSymbol = "WD";
	List<PayoutTableRule> normalSymbolRuleList;
	List<String> normalSymbolList;
	private Map<Integer,Integer> countPayMap;
	
	// callback 값 임시 저장
	@Data
	public static class CallbackTmpResult {
		private int tmpPay;
	}
	
	
	public PayoutTableRuleWildSymbol(String wildSymbol, Map<Integer,Integer> countPayMap, List<String> normalSymbolList, List<PayoutTableRule> normalSymbolRuleList) {
		super();
		this.wildSymbol = wildSymbol;
		this.countPayMap = countPayMap;
		this.normalSymbolList = normalSymbolList;
		this.normalSymbolRuleList = normalSymbolRuleList;
		log.debug("normalSymbolList = {}", normalSymbolList);
		log.debug("normalSymbolRuleList = {}", normalSymbolRuleList.size());
	}

	@Override
	public void calculate(final List<String> currentResult, PayResultOne currentPayResult, int lineNum) {
		if(!currentResult.contains(wildSymbol)) {
			return;
		}
		
		// 복잡한 방법: wild를 normal symbol로 바꿔서 비교
		int wildCount = 0;
		List<Integer> wildSymbolIndexList = new ArrayList<>();
		for(int i=0; i<currentResult.size(); i++) {
			String resultSymbol = currentResult.get(i);
			if(wildSymbol.equals(resultSymbol)) {
				wildSymbolIndexList.add(i);
				wildCount++;
			}
		}
		log.debug("wildSymbolIndexList = {}", wildSymbolIndexList);
		log.debug("wildCount = {}", wildCount);
		List<String> tmpResult = new ArrayList<>(currentResult);
		
		// <<check 1>>:  wild symbol leftmost to rightmost match result  
		checkWildSymbolLeftmostToRightmost(currentResult, currentPayResult, lineNum);
		
		// <<check 2>>: wild symbol replace to normal symbol one by one
		// 1. list all: wd,wd,wd --> (HA,HA,HA), (HA,HA,MA) ...
		//CallbackTmpResult callPay = new CallbackTmpResult();
		PermutationsWithRepeat.permute(normalSymbolList, wildCount, new PermutationsWithRepeat.PermuteCallback<String>() {

			@Override
			public void handle(final List<String> snapshot) {
				// 2. replace symbol and calculate pay 
				for(int i=0; i<wildSymbolIndexList.size(); i++) {
					int wildSymbolIndex = wildSymbolIndexList.get(i);
					String replaceSymbol = snapshot.get(i);
					tmpResult.set(wildSymbolIndex, replaceSymbol);
					//
					calculateByNormalSymbolRule(tmpResult, currentPayResult, lineNum);
				}
			}
			
		});
	}

	private int checkWildSymbolLeftmostToRightmost(final List<String> currentResult, PayResultOne currentPayResult, int lineNum) {
		int pay = 0;
		// leftmost to rightmost match result
		int matchCount = 0;
		for(int i=0; i<currentResult.size(); i++) {
			if(wildSymbol.equals(currentResult.get(i))) {
				matchCount++;
			} else {
				break;
			}
		}
		
		if(countPayMap.containsKey(matchCount)) {
			pay = countPayMap.get(matchCount);
			log.debug(" <<win>> symbol = {}, count = {}, pay = {}", wildSymbol, matchCount, pay);
			
			//
			PayResultItem wildItem = new PayResultItem();
			wildItem.setName(SlotUtils.getPayResultItemName(wildSymbol, matchCount));
			wildItem.setCount(1);
			wildItem.setPay(pay);
			currentPayResult.setLineResult(lineNum, wildItem);
			
		}
		return pay;
	}

	private void calculateByNormalSymbolRule(List<String> tmpResult, PayResultOne currentPayResult, int lineNum) {
		for(PayoutTableRule rule : normalSymbolRuleList) {
			rule.calculate(tmpResult, currentPayResult, lineNum);
		}
	}
	
}
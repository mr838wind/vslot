package com.wdfall.vslot.payout;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.wdfall.vslot.pay_result.PayResultItem;
import com.wdfall.vslot.pay_result.PayResultOne;
import com.wdfall.vslot.utils.SlotUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PayoutTableRuleBonusSymbolStage1 implements PayoutTableRuleScatter {

	private String symbol;
	private Map<Integer,Integer> countPayMap;
	
	private final int MIN_MATCH_COUNT = 3;

	public PayoutTableRuleBonusSymbolStage1(String symbol, Map<Integer, Integer> countPayMap) {
		super();
		this.symbol = symbol;
		this.countPayMap = countPayMap;
	}
	
	@Override
	public void calculate(final String[][] reelShowArray, PayResultOne currentPayResult ) {
		long pay = 0;
		
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
		
		if(matchCount >= MIN_MATCH_COUNT) {
			
			pay = runBonusGame(currentPayResult.getCurrentBet());
			
			log.debug(" <<win scatter>> symbol = {}, count = {}, pay = {}", symbol, matchCount, pay);
			
			//
			PayResultItem bonus = new PayResultItem();
			bonus.setName(SlotUtils.getPayResultItemName(symbol, matchCount));
			bonus.setCount(1);
			bonus.setPay(pay);
			currentPayResult.setBonus(bonus);
			
		}
		
	}
	
	private long runBonusGame(long currentBet) {
		long pay = 0;
		long multipleValue = 0;
		
		// game:
		Random bonusRandom = new Random(); 
		// 1. random: 인어--0, 나가--1
		int playerIndex = bonusRandom.nextInt(2);
		// 2. random 배수
		int[] multipleWheel = new int[]{2,2,4,4,8,8,20,20};
		while( true ) {
			int choose = bonusRandom.nextInt(multipleWheel.length);
			multipleValue = multipleValue + multipleWheel[choose];
			log.debug("multipleValue={}",multipleValue);
			if(choose % 2 != playerIndex) {
				break;
			}
		}
		
		pay = currentBet * multipleValue ;
		log.debug("multipleValue = {}", multipleValue); 
		log.debug("pay = {}", pay); 
		
		return pay;
	}

	
	public static void main(String[] args) {
		new PayoutTableRuleBonusSymbolStage1("SS", new HashMap<>()).runBonusGame(1);
	}
	
	


}
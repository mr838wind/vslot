package com.wdfall.vslot.game;

import lombok.extern.slf4j.Slf4j;

/**
 * stage 1 slot game
 * -------------
 * bonus symbol:
 * free spin: 
 * nudging wild
 * -------------
 * symbol type: normal, bonus, scatter-freespin, wild-nudging
 * -------------
 * 
 */
@Slf4j
public class SlotGameStage1 extends SlotGameBase {
	
	public SlotGameStage1() {
		
	}
	
	// bonus, scatter-freespin
	@Override
	protected void checkScatterPay() {
		
		if(setting.isBonusExist()) {
			setting.getPayoutTableRuleBonus().calculate(reelShowArray, currentPayResult);
		}
		
		
	}
	
	
	
}

package com.wdfall.vslot;

import com.wdfall.vslot.pay_result.PayResultOne;

/**
 * SlotGame interface
 */
public interface SlotGame {
	
	public void init(SlotGameSetting setting);
	
	public PayResultOne spin();
	
	public double getPayoutPercentage();
	
	public double getHitFrequency();
	
	public long getTotalWin();

	public long getTotalBet();

	public long getTotalSpin();

	public long getTotalHit();
	
}

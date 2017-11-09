package com.wdfall.vslot.game;

import com.wdfall.vslot.SlotGameSetting;
import com.wdfall.vslot.pay_result.PayResultOne;

/**
 * SlotGame interface
 */
public interface SlotGame {
	
	/**
	 * 설정 초기화
	 */
	public void init(SlotGameSetting setting);
	
	/**
	 * 게임 한번 실행 
	 */
	public PayResultOne spin();
	
	
	public double getPayoutPercentage();
	
	public double getHitFrequency();
	
	public long getTotalWin();

	public long getTotalBet();

	public long getTotalSpin();

	public long getTotalHit();
	
}

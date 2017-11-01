package com.wdfall.vslot.payout;

import com.wdfall.vslot.pay_result.PayResultOne;

public interface PayoutTableRuleScatter {
	void calculate(final String[][] reelShowArray, PayResultOne currentPayResult);
}

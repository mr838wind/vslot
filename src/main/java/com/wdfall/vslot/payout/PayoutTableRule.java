package com.wdfall.vslot.payout;

import java.util.List;

import com.wdfall.vslot.pay_result.PayResultOne;

public interface PayoutTableRule {
	void calculate(final List<String> currentResult, PayResultOne currentPayResult, int lineNum);
}

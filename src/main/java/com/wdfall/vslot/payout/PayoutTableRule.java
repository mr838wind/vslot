package com.wdfall.vslot.payout;

import java.util.List;

public interface PayoutTableRule {
	int calculate(final List<String> currentResult);
}

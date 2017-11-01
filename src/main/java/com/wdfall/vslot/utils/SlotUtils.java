package com.wdfall.vslot.utils;

import java.text.DecimalFormat;
import java.text.Format;

public class SlotUtils {

	private static Format percentFormat = new DecimalFormat("#0.00");
	private static Format bigNumberFormat = new DecimalFormat("###,###");
	
	public static String getPercentFormat(double data) {
		return percentFormat.format(data);
	}
	
	public static String getBigNumberFormat(double data) {
		return bigNumberFormat.format(data);
	}
	
	/**
	 * pay result를 명명 규칙
	 * @param symbol
	 * @param matchCount
	 * @return
	 */
	public static String getPayResultItemName(String symbol, int matchCount) {
		return symbol + "_" + matchCount;
	}
	
	
}

package com.wdfall.vslot.json;

import java.util.Map;

import lombok.Data;

@Data
public class PayoutTableRuleParam {
	private String symbol;
	private String symbolType;
	private Map<Integer, Integer> rule;

	public static final String SYMBOL_TYPE_NORMAL = "N";
	public static final String SYMBOL_TYPE_WILD = "W";
	public static final String SYMBOL_TYPE_SCATTER = "S";
	public static final String SYMBOL_TYPE_BONUS = "B";
}

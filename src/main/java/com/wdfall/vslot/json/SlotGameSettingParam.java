package com.wdfall.vslot.json;

import java.util.List;
import java.util.Map;

import com.wdfall.vslot.utils.CloneUtils;

import lombok.Data;

/**
 * @author chhan 2017. 10. 18.
 *
 */
@Data
public class SlotGameSettingParam {

	// reel 총 개수
	private int reelCount;
	// reel 당 개수
	private int[] reelCountArray;
	// line별 bet
	private int betPerLine;

	// 사용할 symbol
	private List<String> normalSymbolList;
	// 사용할 기타 symbol
	private List<String> otherSymbolList;

	// reel symbol 구성
	private List<Map<String, Integer>> reelCompositionParamList;

	// line pattern
	private List<List<Integer>> linePatternList;
	// payout table
	private List<PayoutTableRuleParam> payoutTableRuleParamList;

	// copy
	public SlotGameSettingParam copy() {
		return CloneUtils.deepCopyByJson(this, SlotGameSettingParam.class);
	}

	/**
	 * @author chhan
	 */
	@Data
	public static class PayoutTableRuleParam {
		private String symbol;
		private String symbolType;
		private Map<Integer, Integer> rule;

		public static final String SYMBOL_TYPE_NORMAL = "N";
		public static final String SYMBOL_TYPE_WILD = "W";
		public static final String SYMBOL_TYPE_SCATTER = "S";
	}

}

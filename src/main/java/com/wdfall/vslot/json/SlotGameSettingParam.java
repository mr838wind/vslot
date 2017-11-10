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
	
	// 게임 진행 스레드 수
	private int threadCount = 1;

	// 게임 진행 횟수
	private long gameRunCount = 1000*1000;
	
	//===========================================================
	// reel 총 개수
	private int reelCount;
	// reel 당 개수
	private int[] reelCountArray;

	// payout table
	private List<PayoutTableRuleParam> payoutTableRuleParamList;
	
	// reel symbol 구성
	private List<Map<String, Integer>> reelCompositionParamList;

	// line pattern
	private List<List<Integer>> linePatternList;
	
	//===========================================================
	
	// copy
	public SlotGameSettingParam copy() {
		return CloneUtils.deepCopyByJson(this, SlotGameSettingParam.class);
	}


}

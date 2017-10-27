package com.wdfall.vslot.random;

import java.util.Collections;
import java.util.List;

public class SlotReelSymbolGeneratorShuffle extends SlotReelSymbolGeneratorBase {

	public SlotReelSymbolGeneratorShuffle(int[] reelCountArray, List<List<String>> reelCompositionList) {
		super(reelCountArray, reelCompositionList);
	}
	
	@Override
	public String[] generateReel(int reelIndex) {
		// select 3 symbol from n symbols
		// method 1: shuffle and get first 3
		List<String> reelComp1 = reelCompositionList.get(reelIndex);
		Collections.shuffle(reelComp1);
		
		int reelShowLength = reelCountArray[reelIndex];
		String[] symbolsToShow = new String[reelShowLength];
		for(int i=0; i<reelShowLength; i++) {
			symbolsToShow[i] = reelComp1.get(i);
		}
		return symbolsToShow;
	}
	
}

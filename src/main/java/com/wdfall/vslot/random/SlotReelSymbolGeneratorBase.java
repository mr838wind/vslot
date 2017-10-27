package com.wdfall.vslot.random;

import java.util.List;

import com.wdfall.vslot.utils.CloneUtils;

public abstract class SlotReelSymbolGeneratorBase implements SlotReelSymbolGenerator {

	List<List<String>> reelCompositionList;
	int[] reelCountArray;
	
	public SlotReelSymbolGeneratorBase(int[] reelCountArray, List<List<String>> reelCompositionList) {
		this.reelCompositionList = CloneUtils.deepCopyByLib(reelCompositionList);  
		this.reelCountArray = reelCountArray;
	}
	
	public abstract String[] generateReel(int reelIndex);

	@Override
	public String[][] generateReelShowArray() {
		int reelCount = reelCountArray.length;
		String[][] result = new String[reelCount][];
		for(int i=0; i<reelCount; i++) {
			result[i] = generateReel(i);
		}
		return result;
	}
	
	
}

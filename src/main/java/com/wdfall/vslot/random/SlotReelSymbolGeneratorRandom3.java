package com.wdfall.vslot.random;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SlotReelSymbolGeneratorRandom3 extends SlotReelSymbolGeneratorBase {

	Random random = new Random();
	
	public SlotReelSymbolGeneratorRandom3(int[] reelCountArray, List<List<String>> reelCompositionList) {
		super(reelCountArray, reelCompositionList);
	}
	
	@Override
	public String[] generateReel(int reelIndex) {
		// select 3 symbol from n symbols
		// method : get 3 random numbers
		List<String> reelComp1 = reelCompositionList.get(reelIndex);
		
		List<Integer> indexList = generateRandomNumberList(reelComp1);
		
		int reelShowLength = reelCountArray[reelIndex];
		String[] symbolsToShow = new String[reelShowLength];
		for(int i=0; i<reelShowLength; i++) {
			int index = indexList.get(i);
			symbolsToShow[i] = reelComp1.get(index);
		}
		return symbolsToShow;
	}

	private List<Integer> generateRandomNumberList(List<String> reelComp1) {
		List<Integer> indexList = new ArrayList<>();
		while(true) {
			int index = random.nextInt(reelComp1.size());
			if(!indexList.contains(index)) {
				indexList.add(index);
				if(indexList.size() >= 3) {
					break;
				}
			}
		}
		return indexList;
	}
	
}

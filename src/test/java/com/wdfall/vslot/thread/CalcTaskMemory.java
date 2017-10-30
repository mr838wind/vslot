package com.wdfall.vslot.thread;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.wdfall.vslot.random.SlotReelSymbolGenerator;
import com.wdfall.vslot.random.SlotReelSymbolGeneratorShuffle;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * Task 
 * @author chhan
 *
 */
@Slf4j
public class CalcTaskMemory extends CalcTask {
	
	SlotReelSymbolGenerator generator;
	
	public CalcTaskMemory() {
		super();
		int[] reelCountArray = new int[]{3,3,3,3,3};
		List<List<String>> reelCompositionList = new ArrayList<List<String>>();
		List<Map<String, Integer>> reelCompositionParamList = new ArrayList<>();
		Map<String, Integer> map1 = new HashMap<>();
		map1.put("N1", 10);
		map1.put("N2", 20);
		map1.put("N3", 30);
		reelCompositionParamList.add(map1);
		reelCompositionParamList.add(map1);
		reelCompositionParamList.add(map1);
		reelCompositionParamList.add(map1);
		reelCompositionParamList.add(map1);
		
		//reel1, ... reel5
		for(Map<String, Integer> symbolParam : reelCompositionParamList) {
			reelCompositionList.add(generateReelComposition(symbolParam));
		}
		generator = new SlotReelSymbolGeneratorShuffle(reelCountArray, reelCompositionList);
	}
	
	
	private List<String> generateReelComposition(Map<String, Integer> symbolParam) {
		List<String> reelComposition = new ArrayList<>();
		
		for(Map.Entry<String, Integer> param : symbolParam.entrySet()) {
			Integer symbolCount = param.getValue();
			for(int i=0; i<symbolCount; i++) {
				String symbolName = param.getKey();
				reelComposition.add(symbolName);
			}
		}
		return reelComposition;
	}
	
	@Data
	private static class SomeObj {
		private String[][] stringArray;
	}
	
	@Override
	public void doSomeMemoryProcess() {
		super.doSomeMemoryProcess();
		
		List<SomeObj> list = new ArrayList<>();
		for(int i=0; i<1000; i++) {
			String[][] stringArray = generator.generateReelShowArray();
			SomeObj obj = new SomeObj();
			obj.setStringArray(stringArray); 
			list.add(obj);
		}
		
	}
	
}

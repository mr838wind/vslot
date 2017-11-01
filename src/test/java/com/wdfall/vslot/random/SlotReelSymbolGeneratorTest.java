package com.wdfall.vslot.random;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.wdfall.vslot.utils.PermutationsWithRepeat;
import com.wdfall.vslot.utils.PermutationsWithRepeat.PermuteCallback;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SlotReelSymbolGeneratorTest {
	
	final int reelSymbolCount = 3;
	int[] reelCountArray = new int[]{reelSymbolCount};
	Map<String, Integer> reelCompMap;
	
	@Before
	public void setUp() {
		reelCompMap = new HashMap<>();
		reelCompMap.put("A", 3);
		reelCompMap.put("B", 3);
		reelCompMap.put("C", 3);
	}
	
	@Test
	public void testReel1Shuffle() {
		List<List<String>> reelCompositionList = generateReelCompositionList(reelCompMap);
		SlotReelSymbolGeneratorBase gen = new SlotReelSymbolGeneratorShuffle(reelCountArray, reelCompositionList);
		
		commonTest(gen);
		
		Assert.assertTrue((true));
	}
	
	@Test
	public void testReel1Random3() {
		List<List<String>> reelCompositionList = generateReelCompositionList(reelCompMap);
		SlotReelSymbolGeneratorBase gen = new SlotReelSymbolGeneratorRandom3(reelCountArray, reelCompositionList);
		
		commonTest(gen);
		
		Assert.assertTrue((true));
	}
	
	
	

	
	
	
	
	// =========================================================
	
	private void commonTest(SlotReelSymbolGeneratorBase gen) {
		Map<String, Integer> permutationCountMap = getPermutationCountMap();
		int permuSize = permutationCountMap.entrySet().size();
		
		int genCount = permuSize * 1000;
		for(int i=0; i<genCount; i++) {
			String[] reel = gen.generateReel(0);
			
			String key = StringUtils.join(reel, "");
			addCount(permutationCountMap, key);
		}
		
		// result 
		log.debug("===================================================");
		for(Map.Entry<String,Integer> entry : permutationCountMap.entrySet()) {
			log.debug("{} = {}", entry.getKey(), entry.getValue());
		}
		List<Integer> rsList = new ArrayList<>(permutationCountMap.values());
		log.info(" 편차 = {}", ArraySummary.stdDev(ArraySummary.convert(rsList)));
		log.debug("===================================================");
	}

	
	

	private Map<String, Integer> getPermutationCountMap() {
		List<List<String>> allPermutationList = getAllPermutationList(Arrays.asList("A","B","C"));
		log.debug("allPermutationList.size() = {}",allPermutationList.size());
		Map<String,Integer> permutationCountMap = new TreeMap<>(); 
		for(List<String> permu : allPermutationList) {
			String key = StringUtils.join(permu,"");
			permutationCountMap.put(key, 0);
		}
		return permutationCountMap;
	}

	private void addCount(Map<String, Integer> permutationCountMap, String key) {
		int count = permutationCountMap.get(key) + 1;
		permutationCountMap.put(key, count);
	}

	private List<List<String>> getAllPermutationList(List<String> symbolList) {
		List<List<String>> allPermutationList = new ArrayList<>();
		List<String> itemList = new ArrayList<>(symbolList);
		int spaces = reelSymbolCount;
		PermutationsWithRepeat.permute(itemList , spaces , new PermuteCallback<String>() {
			@Override
			public void handle(List<String> snapshot) {
				ArrayList<String> permutation = new ArrayList<>(snapshot);
				allPermutationList.add(permutation);
			}
		});
		return allPermutationList;
	}

	private List<List<String>> generateReelCompositionList(Map<String, Integer> rcp) {
		List<List<String>> reelCompositionList = new ArrayList<List<String>>();
		List<Map<String, Integer>> reelCompositionParamList = new ArrayList<>();
		reelCompositionParamList.add(rcp);
		
		//reel1, ... reel5
		for(Map<String, Integer> symbolParam : reelCompositionParamList) {
			reelCompositionList.add(generateReelComposition(symbolParam));
		}
		return reelCompositionList;
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
	
}

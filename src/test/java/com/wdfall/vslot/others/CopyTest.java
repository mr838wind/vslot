package com.wdfall.vslot.others;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.wdfall.vslot.json.SlotGameSettingParam;
import com.wdfall.vslot.utils.CloneUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CopyTest {

	@Test
	public void test() {
		SlotGameSettingParam param1 = new SlotGameSettingParam();
		param1.setReelCount(5);
		List<String> nsList = new ArrayList<>(Arrays.asList("HA","MA"));
		param1.setNormalSymbolList(nsList);
		
		SlotGameSettingParam param2 = param1.copy();
		param2.setReelCount(6);
		param2.getNormalSymbolList().set(0, "WD");
		
		log.debug("{}",param1);
		log.debug("{}",param2);
		
		Assert.assertTrue(param1.getReelCount() == 5);
		Assert.assertTrue(!param2.getNormalSymbolList().get(0).equals(param1.getNormalSymbolList().get(0)));
	}
	
	@Test
	public void testDeepCopy() {
		
		SlotGameSettingParam param1 = new SlotGameSettingParam();
		param1.setReelCount(5);
		List<String> nsList = new ArrayList<>(Arrays.asList("HA","MA"));
		param1.setNormalSymbolList(nsList);
		
		SlotGameSettingParam param2 = CloneUtils.deepCopyByLib(param1);
		param2.setReelCount(6);
		param2.getNormalSymbolList().set(0, "WD");
		
		log.debug("{}",param1);
		log.debug("{}",param2);
		
		Assert.assertTrue(param1.getReelCount() == 5);
		Assert.assertTrue(!param2.getNormalSymbolList().get(0).equals(param1.getNormalSymbolList().get(0)));
	}
}

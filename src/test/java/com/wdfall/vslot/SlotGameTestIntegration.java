package com.wdfall.vslot;

import java.io.File;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import com.wdfall.vslot.json.SlotGameSettingParam;
import com.wdfall.vslot.utils.FileUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SlotGameTestIntegration {
	
	private static final int RUN_COUNT = 1000*1000;
	private static final int VALUE_MAX_DIFFERENCE = 10;
	private static final String JSON_FILE_PATH_BASIC = "slot_game_setting_param_10_basic.json";
	private static final String JSON_FILE_PATH_PPT = "slot_game_setting_param_20_ppt.json";
	
	@InjectMocks
	@Spy
	SlotGame game;
	
	/**
	 * log4j level ---> INFO
	 */
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testIntegrationBasic() throws Exception {
		readSettingFrom(JSON_FILE_PATH_BASIC);
		
		commonTest(158.8439941);
	}

	@Test
	public void testIntegrationPpt() throws Exception {
		readSettingFrom(JSON_FILE_PATH_PPT);
		
		commonTest(92.46);
		
	}

	
	private void readSettingFrom(String path) {
		File jsonFile = FileUtil.getFileOnClasspath(path); 
		SlotGameSettingParam param = SlotGameSetting.readFromJson(jsonFile);
		SlotGameSetting setting = new SlotGameSetting();
		setting.initFromParam(param); 
		setting.validate();
		
		game.init(setting);
	}
	
	
	private void commonTest(double expected_value_excel) {
		for(int i=0; i<RUN_COUNT; i++) {
			game.spin();
		}
		
		log.info(" !!!! <<game.getPayoutPercentage>> {}", game.getPayoutPercentage());  
		double difference = Math.abs(game.getPayoutPercentage() - expected_value_excel);
		
		Assert.assertTrue( difference < VALUE_MAX_DIFFERENCE );
	}
	
	
}

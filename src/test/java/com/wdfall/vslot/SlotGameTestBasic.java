package com.wdfall.vslot;

import static org.mockito.Mockito.doReturn;

import java.io.File;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import com.wdfall.vslot.json.SlotGameSettingParam;
import com.wdfall.vslot.pay_result.PayResultOne;
import com.wdfall.vslot.random.SlotReelSymbolGenerator;
import com.wdfall.vslot.utils.FileUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SlotGameTestBasic {
	
	private static final int SCATTER_3_PAY = 200;

	private static final int N1_3_PAY = 5;

	private static final String JSON_FILE_PATH = "slot_game_setting_param_10_basic.json";
	
	@InjectMocks
	@Spy
	SlotGameRegular game;
	
	@Mock
	SlotReelSymbolGenerator slotReelSymbolGenerator; 
	
	/**
	 * test pattern: 1,1,1
	 */
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		SlotGameSetting setting = new SlotGameSetting();
		// 
		File jsonFile = FileUtil.getFileOnClasspath(JSON_FILE_PATH); 
		SlotGameSettingParam param = SlotGameSetting.readFromJson(jsonFile);
		setting.initFromParam(param); 
		setting.validate();
		
		game.init(setting);
		game.setSlotReelSymbolGenerator(slotReelSymbolGenerator);
	}

	/*
	 * test all
	 */
	@Test
	public void testAll() throws Exception {
		testNormalWin();
		testNormalFail();
		testWildWin();
		testWildFail();
		testScatterWin();
		testScatterFail();
		
		Assert.assertTrue( game.getTotalWin() == (N1_3_PAY * 2 + SCATTER_3_PAY) );
	}
	
	
	/*
	 * test basic
	 */
	@Test
	public void testNormalWin() throws Exception {
		doReturn(new String[][]{
			new String[] {"N1","N2","N3"},
			new String[] {"N1","N3","N2"},
			new String[] {"N1","N3","N3"},
		}).when(slotReelSymbolGenerator).generateReelShowArray();
		
		PayResultOne currentResult = game.spin();
		long win = currentResult.getCurrentPayResult();
		
		Assert.assertTrue( win == N1_3_PAY  );
	}
	
	@Test
	public void testNormalFail() throws Exception {
		doReturn(new String[][]{
			new String[] {"N1","N2","N3"},
			new String[] {"N1","N3","N2"},
			new String[] {"N2","N3","N3"},
		}).when(slotReelSymbolGenerator).generateReelShowArray();
		
		PayResultOne currentResult = game.spin();
		long win = currentResult.getCurrentPayResult();
		
		Assert.assertTrue( win == 0  );
	}
	
	@Test
	public void testWildWin() throws Exception {
		doReturn(new String[][]{
			new String[] {"N1","N2","N3"},
			new String[] {"WD","N3","N2"},
			new String[] {"N1","N3","N3"},
		}).when(slotReelSymbolGenerator).generateReelShowArray();
		
		PayResultOne currentResult = game.spin();
		long win = currentResult.getCurrentPayResult();
		
		Assert.assertTrue( win == N1_3_PAY  );
	}
	
	@Test
	public void testWildFail() throws Exception {
		doReturn(new String[][]{
			new String[] {"N1","N2","N3"},
			new String[] {"WD","N3","N2"},
			new String[] {"N2","N3","N3"},
		}).when(slotReelSymbolGenerator).generateReelShowArray();
		
		PayResultOne currentResult = game.spin();
		long win = currentResult.getCurrentPayResult();
		
		Assert.assertTrue( win == 0  );
	}
	
	@Test
	public void testScatterWin() throws Exception {
		doReturn(new String[][]{
			new String[] {"SS","N2","N3"},
			new String[] {"SS","N3","N2"},
			new String[] {"SS","N3","N3"},
		}).when(slotReelSymbolGenerator).generateReelShowArray();
		
		PayResultOne currentResult = game.spin();
		long win = currentResult.getCurrentPayResult();
		
		Assert.assertTrue( win == SCATTER_3_PAY  );
	}
	
	@Test
	public void testScatterFail() throws Exception {
		doReturn(new String[][]{
			new String[] {"N1","SS","N3"},
			new String[] {"N2","N3","SS"},
			new String[] {"N1","N3","N3"},
		}).when(slotReelSymbolGenerator).generateReelShowArray();
		
		PayResultOne currentResult = game.spin();
		long win = currentResult.getCurrentPayResult();
		
		Assert.assertTrue( win == 0  );
	}
	
	
	
}

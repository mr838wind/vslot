package com.wdfall.vslot;

import java.util.concurrent.Callable;

import com.wdfall.vslot.game.SlotGame;
import com.wdfall.vslot.json.SlotGameSettingParam;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SlotTask<T extends SlotGame> implements Callable<SlotGame> { 
	private SlotGameSettingParam param;
	private Class<T> clazz;
	
	public SlotTask(Class<T> clazz, SlotGameSettingParam param) {
		this.clazz = clazz;
		this.param = param;
	}
	
	@Override
	public SlotGame call() throws Exception {
		log.info(" >>> SlotTask {} in Thread {} start ! ", this, Thread.currentThread().getId()); 
		
		SlotGameSetting setting = new SlotGameSetting();
		setting.initFromParam(param); 
		setting.validate();
		
		
		T game = clazz.newInstance();
		game.init(setting);
		for(int i=0; i<setting.getGameRunCount(); i++) {
			game.spin();
		}
		
		log.info(" >>> SlotTask {} in Thread {} end ! ", this, Thread.currentThread().getId()); 
		
		return game;
	}
}

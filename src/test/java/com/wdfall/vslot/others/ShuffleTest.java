package com.wdfall.vslot.others;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

/**
 * Hello world!
 *
 */
@Slf4j
public class ShuffleTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6);
		log.info("{}", list);
		//long seed = System.currentTimeMillis();
		
		for(int i=0; i<1000; i++) {
			Collections.shuffle(list);
			//Collections.shuffle(list, new Random());
			//Collections.shuffle(list, new Random(2));
			//Collections.shuffle(list, new Random(seed));
			log.info("{}", list);
			
			if(list.equals(Arrays.asList(1, 2, 3, 4, 5, 6))) {
				log.info("\r\n>>>>>> duplicate in {} ", i);
			}
		}
	}
	
	
}

package com.wdfall.vslot.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * Task
 * 
 * @author chhan
 *
 */
@Slf4j
public class CalcTaskMemorySimple extends CalcTask {

	public CalcTaskMemorySimple() {
		super();
	}

	@Data
	private static class SomeObj {
		private String[][] stringArray;
	}

	@Override
	public void doSomeMemoryProcess() {
		super.doSomeMemoryProcess();

		List<SomeObj> list = new ArrayList<>();
		for (int i = 0; i < 1000 * 1000; i++) {
			String[][] stringArray = generateStringArray();
			SomeObj obj = new SomeObj();
			obj.setStringArray(stringArray);
			list.add(obj);
		}

	}

	private String[][] generateStringArray() {
		String[][] result = new String[5][3];
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 3; j++) {
				result[i][j] = generateRandomString();
			}
		}
		return result;
	}

	String[] alphabet = new String[] { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P",
			"Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };
	Random numberRandom = new Random();

	private String generateRandomString() {
		int index = numberRandom.nextInt(26);
		String str = String.valueOf(alphabet[index]);
		return str;
	}

}

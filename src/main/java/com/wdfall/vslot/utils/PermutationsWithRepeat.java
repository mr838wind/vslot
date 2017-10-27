package com.wdfall.vslot.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * copied and modified from reference:
 * https://stackoverflow.com/questions/13157656/permutation-of-an-array-with-repetition-in-java
 * 
 * @author chhan
 */
public class PermutationsWithRepeat {

	/**
	 * list all permutations in n object and k space with repeat. (n = itemList.size, k = spaces) 
	 * @param itemList
	 * @param spaces
	 * @param callback
	 */
	public static <T> void permute(final List<T> itemList, int spaces, PermuteCallback<T> callback) {
		int n = itemList.size();

		int[] indexes = new int[spaces];
		int total = (int) Math.pow(n, spaces);

		List<T> snapshot = new ArrayList<>(Collections.nCopies(spaces, null));
		while (total-- > 0) {
			for (int i = 0; i < spaces; i++) {
				snapshot.set(i, itemList.get(indexes[i]));
			}
			if(callback != null) {
				callback.handle(snapshot);
			}

			for (int i = 0; i < spaces; i++) {
				if (indexes[i] >= n - 1) {
					indexes[i] = 0;
				} else {
					indexes[i]++;
					break;
				}
			}
		}
	}

	public static interface PermuteCallback<T> {
		public void handle(final List<T> snapshot);
	};

	public static void main(String[] args) {
		List<String> chars = Arrays.asList("a", "b");
		PermuteCallback<String> callback = new PermuteCallback<String>() {

			@Override
			public void handle(final List<String> snapshot) {
				for (int i = 0; i < snapshot.size(); i++) {
					System.out.print(snapshot.get(i));
				}
				System.out.println();
			}
		};
		permute(chars, 3, callback);
	}

}
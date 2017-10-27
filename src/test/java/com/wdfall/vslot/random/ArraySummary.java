package com.wdfall.vslot.random;
//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

//This file is part of dLife
//Copyright (c) 2010 Grant Braught. All rights reserved.
//
//  dLife is free software: you can redistribute it and/or modify
//  it under the terms of the GNU General Public License as published
//  by the Free Software Foundation, either version 3 of the License,
//  or (at your option) any later version.
//
//  dLife is distributed in the hope that it will be useful,
//  but WITHOUT ANY WARRANTY; without even the implied warranty
//  of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
//  See the GNU General Public License for more details.
//
//  You should have received a copy of the GNU General Public
//  License along with dLife. 
//  If not, see <http: www.gnu.org="" licenses="">.
//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

import java.util.Arrays;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

/**
 * 배열로부터 평균, 중간, 최소, 최대, 편차, 표준편차값 등을 계산하는 클래스
 * 
 * @author Grant Braught
 * @author Dickinson College
 * @version May 3, 2005
 */
@Slf4j
public class ArraySummary {

	public static void main(String[] args) {
		double[] arr = new double[]{100,200,300,400};
		log.debug(" 평균 = {}", ArraySummary.mean(arr)); 
		log.debug(" 표준편차 = {}", ArraySummary.stdDev(arr)); 
	}
	
	
	public static double[] convert(List<Integer> rsList) {
		double[] result = new double[rsList.size()];
		for(int i=0; i<rsList.size(); i++) {
			result[i] = (double)rsList.get(i);
		}
		return result;
	}
	
	/**
	 * 배열의 평균값을 계산하는 메소드
	 *
	 * @param arr
	 *            더블형 배열
	 * @return 배열의 평균값
	 */
	public static double mean(double[] arr) {
		double total = 0;
		for (int i = 0; i < arr.length; i++) {
			total = total + arr[i];
		}
		return total / arr.length;
	}

	/**
	 * 배열의 최대값을 찾는 메소드
	 *
	 * @param arr
	 *            더블형 배열
	 * @return 배열의 최대값
	 */
	public static double max(double[] arr) {
		int maxIndex = 0;
		for (int i = 1; i < arr.length; i++) {
			if (arr[i] > arr[maxIndex]) {
				maxIndex = i;
			}
		}
		return arr[maxIndex];
	}

	/**
	 * 배열의 최대값 인덱스를 찾는 메소드
	 *
	 * @param arr
	 *            더블형 배열
	 * @return 배열의 최대값 인덱스
	 */
	private static int maxIndex(double[] arr) {
		int maxIndex = 0;
		for (int i = 1; i < arr.length; i++) {
			if (arr[i] > arr[maxIndex]) {
				maxIndex = i;
			}
		}
		return maxIndex;
	}

	/**
	 * 배열의 최대값 인덱스를 찾는 메소드
	 *
	 * @param arr
	 *            더블형 배열
	 * @return 배열의 최대값 인덱스
	 */
	private static int[] maxIndex(double[][] arr) {
		int maxIndexX = 0;
		int maxIndexY = 0;
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr[i].length; j++) {
				if (arr[i][j] > arr[maxIndexX][maxIndexY]) {
					maxIndexX = i;
					maxIndexY = j;
				}
			}
		}

		return new int[] { maxIndexX, maxIndexY };
	}

	/**
	 * 배열의 최소값을 찾는 메소드
	 * 
	 * @param arr
	 *            더블형 배열
	 * @return 배열의 최소값
	 */
	public static double min(double[] arr) {
		int minIndex = 0;
		for (int i = 1; i < arr.length; i++) {
			if (arr[i] < arr[minIndex]) {
				minIndex = i;
			}
		}
		return arr[minIndex];
	}

	/**
	 * 주어진 배열의 중간값을 찾는 메소드 단, 해당 배열의 요소가 짝수개일 경우에는 구한 두개의 중간값중 낮은 값을 리턴함.
	 * 
	 * @param arr
	 *            더블형 배열
	 * @return 배열의 중간값
	 */
	public static double median(double[] arr) {
		double[] arrClone = new double[arr.length];
		System.arraycopy(arr, 0, arrClone, 0, arr.length);
		Arrays.sort(arrClone);
		return arrClone[(int) (Math.round(arrClone.length / 2.0) - 1)];
	}

	/**
	 * 배열의 편차값을 구하는 메소드
	 * 
	 * @param arr
	 *            더블형 배열
	 * @return 배열의 편차값
	 */
	public static double variance(double[] arr) {
		return variance(arr, mean(arr));
	}

	/**
	 * 배열의 편차값을 구하는 메소드
	 * 
	 * @param arr
	 *            더블형 배열
	 * @param mean
	 *            더블형 배열의 평균값
	 * @return 배열의 편차값
	 */
	private static double variance(double[] arr, double mean) {
		double totalDev = 0;

		for (int i = 0; i < arr.length; i++) {
			totalDev = totalDev + (mean - arr[i]) * (mean - arr[i]);
		}

		// Sample estimate of variance so divide by n-1.
		return totalDev / (arr.length - 1);
	}

	/**
	 * 배열의 표준편차값을 구하는 메소드
	 * 
	 * @param variance
	 *            편차
	 * @return 표준편차
	 */
	private static double stdDev(double variance) {
		return Math.sqrt(variance);
	}

	/**
	 * 배열의 표준편차값을 구하는 메소드
	 *
	 * @param arr
	 *            더블형 배열
	 * @return 표준편차
	 */
	public static double stdDev(double[] arr) {
		return stdDev(variance(arr));
	}

	/**
	 * 배열간의 RMS차를 구하는 메소드
	 *
	 * @param arr1
	 *            더블형 배열
	 * @param arr
	 *            더블형 배열
	 * @return RMS차
	 * @throws IllegalArgumentException
	 *             배열간의 길이가 다를 경우
	 */
	public static double RMS(double[] arr1, double[] arr2) {
		if (arr1.length != arr2.length) {
			throw new IllegalArgumentException("getRMSDifference: aar1 and aar2 " + "must be the same length.");
		}

		double totalSqDiff = 0;
		for (int i = 0; i < arr1.length; i++) {
			double diff = (arr1[i] - arr2[i]);
			totalSqDiff += diff * diff;
		}

		return Math.sqrt(totalSqDiff / arr1.length);
	}
}

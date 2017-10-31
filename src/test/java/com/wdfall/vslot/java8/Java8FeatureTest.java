package com.wdfall.vslot.java8;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.OptionalInt;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

@FunctionalInterface
interface MyFuc {
	double call(double x);
}

@Slf4j
public class Java8FeatureTest {

	/*
	 * stream : handle collection like data
	 */
	@Test
	public void streamTest1() {
		List<String> list = Arrays.asList("hi","hello","world");
		Stream<String> stream = list.stream();
		//
		stream.distinct().limit(5).sorted().forEach(log::info);
		//
	}
	
	@Test
	public void streamTest2() {
		List<String> list = Arrays.asList("hi","hello","world");
		Stream<String> stream = list.stream();
		//
		OptionalInt result = stream.mapToInt(s->s.length()).max();
		log.info("{}",result.isPresent());
		log.info("{}",result.getAsInt());
		//
	}
	
	@Test
	public void streamTest3() {
		List<String> list = Arrays.asList("hi","hello","world");
		Stream<String> stream = list.stream();
		//
		String result = stream.sorted().collect(Collectors.joining(","));
		//
		log.info("{}",result);
	}
	
	//...
	
	@Test
	public void streamTest4() {
		List<String> list = Arrays.asList("hi","gogo", "hello","world");
		Stream<String> stream = list.stream();
		//
		Map<String, List<String>> collect = stream.sorted().collect(Collectors.groupingBy(s->String.valueOf(s.charAt(0)), Collectors.toList()));
		//
		log.info("{}",collect);
	}
	
	//=============================
	/*
	 * lambda : a kind of interface
	 */
	@Test
	public void lambdaTest1() {
		MyFuc f = (x) -> x*x;
		double result = f.call(3);
		log.info("{}", result);
	}
	
	@Test
	public void lambdaTest2() {
		Function<Double, Double> f = (x) -> x*x; 
		double result = f.apply(3.0);
		log.info("{}", result);
	}
	
	// 하나의 메서드만 호출하는 람다식은 메서드 참조로 바꿀수 있다.
	@Test
	public void lambdaTest3() {
		Function<String, Integer> f = Integer::parseInt; 
		int result = f.apply("3");
		log.info("{}", result);
	}
	
	
	
	
}

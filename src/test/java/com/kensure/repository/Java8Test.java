package com.kensure.repository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.junit.Test;

import co.kensure.io.FileUtils;
import co.kensure.mem.DateUtils;

public class Java8Test {

	@Test
	public void test() {
		System.out.println("111Hello Lambda22!");
		// Runnable r = () -> System.out.println("Hello Lambda!");
		// r.run();

		// Consumer<String> con = (x) -> System.out.println(x);
		// con.accept("123455");

		// Comparator<Integer> com = (x, y) -> {
		// System.out.println("函数式接口");
		// return Integer.compare(x, y);
		// };
		//
		// System.out.println(com.compare(1, 2));

		// BinaryOperator<Long> operator = (Long x, Long y) -> {
		// System.out.println("实现函数接口方法");
		// return x + y;
		// };
		// System.out.println(operator.apply(1L, 2L));

		
//		new Thread(() -> System.out.println("hello world")).start();
//		new Thread(() -> System.out.println("22222")).start();
		
		
		 List<String> list1 = Arrays.asList("a", "b", "c", "d");
//		 list1.forEach(n -> System.out.println(n));
//		 list1.forEach(System.out::println);
		 
//		 list1.stream().map(x->x+"1").forEach(x -> System.out.println(x));
//		  String s = list1.stream().map(x->x+"1").reduce((sum,x) ->sum+x).get();
//		  System.out.println(s);
		 
		 List<String> filteredCost = list1.stream().filter(x -> x.startsWith("a")).collect(Collectors.toList());
		  filteredCost.forEach(x -> System.out.println(x));
		  
		  
	}

	private void getList(String path, Date date, List<String> list) {
		String[] childs = FileUtils.getChildList(path);
		for (String child : childs) {
			String filepath = path + "\\" + child;
			File file = new File(filepath);
			if (file.isDirectory()) {
				getList(filepath, date, list);
			} else {
				if (file.lastModified() >= date.getTime()) {
					list.add(filepath);
				}
			}
		}

	}

}

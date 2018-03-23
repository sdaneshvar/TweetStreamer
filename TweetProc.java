package edu.ucdavis.ecs160.hw3;

import java.util.function.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.*;
import java.util.*;
import java.io.*;
import java.nio.file.*;

public class TweetProc {
	static String tagged(String tw)		{
		 String patternStr = "@[A-Za-z0-9-_]*";
	      Pattern pattern = Pattern.compile(patternStr);
	      Matcher m = pattern.matcher(tw);
	      if (m.find())
	      return m.group(0);
	      else return "@NOTAG";
	}
	
	static Map<String, Map<String, Long>> getTweeterTaggeeCount(String FullPathname) {
		Stream<String> lines = null;
		try {
			lines = Files.lines(Paths.get(FullPathname));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}				
		Map<String, Map<String, Long>> map = lines
				.skip(1)
				.map(x->x.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)"))
				.collect(Collectors.groupingBy(x->x[8],Collectors.groupingBy(x->tagged(x[11]), Collectors.counting())));
//		for (String key : words.keySet()) {
//			System.out.println(key + " " + words.get(key));
//		}
		lines.close();
		return map;
	}
	static Map<String, Double> getTweeterAverageVerbosity(String FullPathname) {
		//Map<String, Double> map = new HashMap<>();
		//Map<String, List<Integer>> map2 = new HashMap<>();
		Stream<String> lines = null;
		try {
			lines = Files.lines(Paths.get(FullPathname));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Map <String, List<Integer>> map2 = lines
				.skip(1)
				.map(x->x.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)"))
				.collect(Collectors.groupingBy(x->x[8], Collectors.mapping(x->x[11].split(" ").length, Collectors.toList())));
		Map<String, Double> map = map2
				.entrySet()
				.stream()
				.collect(Collectors.toMap(x->x.getKey(), x->x.getValue().stream().mapToDouble(a->a).average().getAsDouble()));
//		for (String key : map.keySet()) {
//			System.out.println(key + " " + map.get(key));
//		}
		lines.close();
		return map;
	}
	static Map<String, Long> getTweeterWordCount(String FullPathname, String word) {
		//Map<String, Long> map = new HashMap<>();
		Stream<String> lines = null;
		try {
			lines = Files.lines(Paths.get(FullPathname));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Map<String, Long> map = lines
				.skip(1)
				.map(x->x.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)"))
				.filter(x->x[11].contains(word))
				.collect(Collectors.toMap(x->x[8], x->Long.valueOf(1), (oldValue, newValue) -> oldValue + newValue));
		
//		for (String key : map.keySet()) {
//			System.out.println(key + " " + map.get(key));
//		}
		lines.close();
		return map;
	}
	static Map<String, Long> getTweeterURLtweetCount(String FullPathname) {
		//Map<String, Long> map = new HashMap<>();
		Stream<String> lines = null;
		try {
			lines = Files.lines(Paths.get(FullPathname));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Map<String, Long> map = lines
				.skip(1)
				.map(x->x.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)"))
				.filter(x->x[11].contains("http://"))
				.collect(Collectors.toMap(x->x[8], x->Long.valueOf(1), (oldValue, newValue) -> oldValue + newValue));

//		for (String key : map.keySet()) {
//			System.out.println(key + " " + map.get(key));
//		}
		lines.close();
		return map;
	}
	
	static Map<String, Long> getPerTaggeeCount(String FullPathname) {
		//Map<String, Long> map = new HashMap<>();
		Stream<String> lines = null;
		try {
			lines = Files.lines(Paths.get(FullPathname));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Map<String, Long> map = lines
				.skip(1)
				.map(x->x.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)"))
				.map((String[] x) ->tagged(x[11]))
				.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
		
//		for (String key : map.keySet()) {
//			System.out.println(key + " " + map.get(key));
//		}
		
		lines.close();
		return map;
	}
	
	static Map<String, Long> getPerTweeterCount(String FullPathname) {
		//Map<String, Long> map = new HashMap<>();
		Stream<String> lines = null;
		try {
			lines = Files.lines(Paths.get(FullPathname));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Map<String, Long> map = lines
				.skip(1)
				.map(x->x.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)"))
				.collect(Collectors.toMap(x->x[8], x->Long.valueOf(1), (oldValue, newValue) -> oldValue + newValue));
		lines.close();
//		for (String key : map.keySet()) {
//			System.out.println(key + " " + map.get(key));
//		}
		return map;	
	}

	public static void main(String [] args) {
//		getPerTweeterCount("./cl-tweets-short.csv");
//		getPerTaggeeCount("./cl-tweets-short.csv");
//		getTweeterURLtweetCount("./cl-tweets-short.csv");
//		getTweeterWordCount("./cl-tweets-short.csv", "happy");
//		getTweeterAverageVerbosity("./cl-tweets-short.csv");
//		getTweeterTaggeeCount("./cl-tweets-short.csv");
	}
}

package com.word.web;

import java.util.Map;

//Cache class HashMaps are used globally in any bean 
public class ApplicationCache {

	private Map<String,Integer> WordMap;
	private Map<String,Integer> SortedMap;
	
	public Map<String,Integer> getWordMap() {
		return WordMap;
	}
	public void setWordMap(Map<String,Integer> wordMap) {
		WordMap = wordMap;
	}
	public Map<String,Integer> getSortedMap() {
		return SortedMap;
	}
	public void setSortedMap(Map<String,Integer> sortedMap) {
		SortedMap = sortedMap;
	}	
	
}

package com.dsa.pattern;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Driver {

	public static void main(String[] args) {
		final String patternToSearch = "test";
		final String sampleText = "this is a sample text"; 
		//		int test = searchUsingBruteForce(patternToSearch, sampleText);
		//		System.out.print(test);
		//		searchUsingKMP();
		searchUsingBoyerMoore(patternToSearch, sampleText);
	}

	private static int searchUsingBoyerMoore(String patternToSearch, String sampleText) {

		int index = -1;
		HashMap badMatchTable = constructBadMatchTable(patternToSearch.toLowerCase());
		//		printMap(badMatchTable);
		
		for(int i=patternToSearch.length()-1; i<sampleText.length();) {
			int j = patternToSearch.length()-1;
			if(sampleText.charAt(i) == patternToSearch.charAt(j)) {
				i--;
				j--;
				
				if(j==0) {
					break;
				}
			}
			else {
				Integer shiftLength = (Integer) badMatchTable.get(sampleText.charAt(i));
				System.out.println("RAM"+shiftLength);
//				i = i+shiftLength;
//				j = patternToSearch.length()-1;
			}
		}
		return index;
	}


	private static void printMap(HashMap<Character, Integer> badMatchTable) {
		Iterator<Map.Entry<Character, Integer>> entries = badMatchTable.entrySet().iterator();
		while (entries.hasNext()) {
			Entry<Character, Integer> entry = entries.next();
			System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
		}
	}

	private static HashMap<Character, Integer> constructBadMatchTable(String patternToSearch) {
		LinkedHashMap<Character,Integer> badMatchTable = new LinkedHashMap<Character,Integer>();		
		for(int i=0; i<patternToSearch.length();i++) {
			badMatchTable.put(patternToSearch.charAt(i), Math.max(1, patternToSearch.length() - i - 1));
		}
		badMatchTable.put('*', patternToSearch.length());
		return badMatchTable;
	}


	private static void searchUsingKMP() {


	}

	private static int searchUsingBruteForce(String patternToSearch, String sampleText) {
		int pointer = 0,i = 0;
		int index = -1;
		while(i<sampleText.length()) {
			if(sampleText.charAt(i) == patternToSearch.charAt(pointer)) {
				pointer++;
				i++;

				if(pointer == patternToSearch.length()) {
					index = i - patternToSearch.length();
					break;
				}
			}
			else {
				pointer=0;
				i++;
			}
		}
		return index;
	}
}

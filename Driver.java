package com.dsa.pattern;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import javafx.util.Pair;

public class Driver {

	public static void main(String[] args) {
		//				final String sampleText = "TTATAGATCTCGTATTCTTTTATAGATCTCCTATTCTT";
		//				final String patternToSearch = "TCCTATTCTT";
		final String patternToSearch = "abacab";
		final String sampleText = "abacaabaccabacabaabb";

		int naiveIndex = searchUsingBruteForce(patternToSearch, sampleText);
		System.out.println("Naive "+naiveIndex);
		int kmpIndex = searchUsingKMP(patternToSearch.toLowerCase().toCharArray(), sampleText.toLowerCase().toCharArray());
		System.out.println("KMP: "+kmpIndex);
		int horspoolIndex = searchUsingBoyerMooreHorspool(patternToSearch.toLowerCase(), sampleText.toLowerCase());
		System.out.println("Horspool: "+horspoolIndex);
	}

	private static int searchUsingBoyerMooreHorspool(String patternToSearch, String sampleText) {

		int noOfComparisons = 0;
		char hook = 'z';
		int index = -1;
		int shiftBy = 0;
		int correctmatches = 0;
		HashMap<Character, Integer> badMatchTable = constructBadMatchTable(patternToSearch);
		printMap(badMatchTable);
		int j = patternToSearch.length()-1;		
		for(int i=patternToSearch.length()-1; i<sampleText.length();) {
			noOfComparisons++;
//			System.out.println("Comparison happens between: ");
//			System.out.println(sampleText.charAt(i));
//			System.out.println(patternToSearch.charAt(j));
//			System.out.println("The search starts at "+i);
			if(j == patternToSearch.length()-1) {
				hook = sampleText.charAt(i);
//				System.out.println("Hook val: "+hook);
				correctmatches = 0;
			}

			if(sampleText.charAt(i) == patternToSearch.charAt(j)) {
				correctmatches++;
				i--;
				j--;

				if(j<0) {
					index = i+1;
					break;
				}
			}
			else {
//				System.out.println("Hook val: "+hook);
				//				System.out.println("Getting the number for "+sampleText.charAt(i));
				Integer shiftLength = (Integer)badMatchTable.get(hook);
				if(shiftLength==null) {
					shiftLength = (Integer)badMatchTable.get('*');
				}
				shiftBy = shiftLength.intValue();
				i = i+correctmatches+shiftBy;
				j = patternToSearch.length()-1;
			}
		}
		System.out.println("NO OF COMP (HOSR): "+noOfComparisons);
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
		for(int i=0; i<patternToSearch.length()-1;i++) {
			badMatchTable.put(patternToSearch.charAt(i), Math.max(1, patternToSearch.length() - i - 1));
		}
		badMatchTable.put('*', patternToSearch.length());
		//		printMap(badMatchTable);
		return badMatchTable;
	}


	private static int searchUsingKMP(char[] pattern, char[] text) {
		int noOfComparisons = 0;
		int lps[] = constructPiTable(pattern);
		System.out.println("KMP table"+Arrays.toString(lps));
		int i=0;
		int j=0;
		while(i < text.length && j < pattern.length){
			//			System.out.println("From Text: "+text[i]);
			//			System.out.println("From Pattern: "+pattern[j]);
			if(text[i] == pattern[j]){
				noOfComparisons++;
				i++;
				j++;
				//				if(j == pattern.length-1) {
				//					return i-j;
				//				}
			}else{
				noOfComparisons++;
				if(j>0){
					j = lps[j-1];
				}else{
					i++;
				}
			}
			if(j == pattern.length) {
				System.out.println("No of Comp (KMP): "+noOfComparisons);
				return i-j;
			}
		}
		System.out.println("No of Comp (KMP): "+noOfComparisons);
		return -1;
	}


	private static int[] constructPiTable(char[] pattern) {
		int [] lps = new int[pattern.length];
		int index =0;
		for(int i=1; i < pattern.length;){
			if(pattern[i] == pattern[index]){
				lps[i] = index + 1;
				index++;
				i++;
			}else{
				if(index != 0){
					index = lps[index-1];
				}else{
					lps[i] =0;
					i++;
				}
			}
		}
		return lps;
	}	

	private static int searchUsingBruteForce(String pattern, String text) {

		int j = 0,i = 0,startIndex=0;
		int noOfComparisons = 0;
		if(pattern.length() == 0 && text.length() == 0)
			return 0;

		if(text.length() < pattern.length())
			return -1;

		for(startIndex=0; startIndex<text.length();startIndex++) {
			i = startIndex;
			while(i<text.length() && j<pattern.length()) {
				noOfComparisons++;
				if(text.charAt(i) == pattern.charAt(j)) {
					i++;
					j++;
				}
				else {
					j=0;
					break;
				}

				if(j==pattern.length()) {
					System.out.println(noOfComparisons);
					return startIndex;
				}
			}
		}
		return -1;
	}
}
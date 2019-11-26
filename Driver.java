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
		final String sampleText = "TTATAGATCTCGTATTCTTTTATAGATCTCCTATTCTT";
		final String patternToSearch = "TCCTATTCTT";
		//		final String patternToSearch = "BARBER";
		//		final String sampleText = "JIM_SAW_ME_IN_A_BARBERSHOP"; 
		int naiveIndex = searchUsingBruteForce(patternToSearch, sampleText);
		System.out.println(naiveIndex);
		int kmpIndex = searchUsingKMP(patternToSearch.toLowerCase().toCharArray(), sampleText.toLowerCase().toCharArray());
		System.out.println(kmpIndex);
		int horspoolIndex = searchUsingBoyerMooreHorspool(patternToSearch.toLowerCase(), sampleText.toLowerCase());
		System.out.println(horspoolIndex);
	}

	private static int searchUsingBoyerMooreHorspool(String patternToSearch, String sampleText) {

		int index = -1;
		int shiftBy = 0;
		HashMap<Character, Integer> badMatchTable = constructBadMatchTable(patternToSearch);
		int j = patternToSearch.length()-1;
		for(int i=patternToSearch.length()-1; i<sampleText.length();) {
			//			System.out.println();
			//			System.out.println(sampleText.charAt(i));
			//			System.out.println(patternToSearch.charAt(j));
			//			System.out.println("The search starts at "+i);
			if(sampleText.charAt(i) == patternToSearch.charAt(j)) {
				i--;
				j--;

				if(j<0) {
					index = i+1;
					break;
				}
			}
			else {
				//				System.out.println("Getting the number for "+sampleText.charAt(i));
				Integer shiftLength = (Integer)badMatchTable.get(sampleText.charAt(i));
				if(shiftLength==null) {
					shiftLength = (Integer)badMatchTable.get('*');
				}
				shiftBy = shiftLength.intValue();
				i+=shiftBy;
				j = patternToSearch.length()-1;
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
		for(int i=0; i<patternToSearch.length()-1;i++) {
			badMatchTable.put(patternToSearch.charAt(i), Math.max(1, patternToSearch.length() - i - 1));
		}
		badMatchTable.put('*', patternToSearch.length());
		//		printMap(badMatchTable);
		return badMatchTable;
	}


	private static int searchUsingKMP(char[] pattern, char[] text) {
		int lps[] = constructPiTable(pattern);
		//		System.out.println(Arrays.toString(lps));
		int i=0;
		int j=0;
		while(i < text.length && j < pattern.length){
			//			System.out.println("From Text: "+text[i]);
			//			System.out.println("From Pattern: "+pattern[j]);
			if(text[i] == pattern[j]){
				i++;
				j++;
				if(j == pattern.length-1) {
					return i-j;
				}
			}else{
				if(j>0){
					j = lps[j-1];
				}else{
					i++;
				}
			}
		}

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


		for(startIndex=0; startIndex<text.length();startIndex++) {
			i = startIndex;
			while(i<text.length()) {
				if(text.charAt(i) == pattern.charAt(j)) {
					i++;
					j++;
					if(j==pattern.length()-1) {
						return startIndex;
					}
				}
				else {
					j=0;
					break;
				}
			}
		}
		return -1;
	}
}
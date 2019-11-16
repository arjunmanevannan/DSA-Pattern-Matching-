package com.dsa.pattern;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import javafx.util.Pair;

public class Driver {

	public static void main(String[] args) {
		final String patternToSearch = "text";
		final String sampleText = "this is a sample text"; 
		//		int test = searchUsingBruteForce(patternToSearch, sampleText);
		//		System.out.print(test);
		//		searchUsingKMP();
//		int test = searchUsingBoyerMoore(patternToSearch, sampleText);
//		System.out.println(test);
//		System.out.println(sampleText.charAt(17));
	}

	private static int searchUsingBoyerMoore(String patternToSearch, String sampleText) {

		int index = -1;
		int shiftBy = 0;
		HashMap badMatchTable = constructBadMatchTable(patternToSearch.toLowerCase());
		int j = patternToSearch.length()-1;
		for(int i=patternToSearch.length()-1; i<sampleText.length();) {
			
			if(sampleText.charAt(i) == patternToSearch.charAt(j)) {
				i--;
				j--;
				
				if(j<0) {
					index = i+1;
					break;
				}
			}
			else {
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
		for(int i=0; i<patternToSearch.length();i++) {
			badMatchTable.put(patternToSearch.charAt(i), Math.max(1, patternToSearch.length() - i - 1));
		}
		badMatchTable.put('*', patternToSearch.length());
		return badMatchTable;
	}


	private static void searchUsingKMP() {
		
		
		

	}
	
	
	private static HashMap<Character, Integer> constructPiTable(String patternToSearch) {
		ArrayList <Pair <Character, Integer> > l = new ArrayList <Pair <Character, Integer> > (); 
		
		for(int i=0; i<patternToSearch.length();i++) {
			Pair<Character, Integer> p1 = new Pair<Character, Integer>(patternToSearch.charAt(i),0);
			l.add(p1);
		}
		Modify the table to house the char and index. 
		
		
		
		return piTable;
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

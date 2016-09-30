package com.word.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import junit.framework.TestCase;

public class LoadFilesTest extends TestCase {

	LoadFiles lfs = new LoadFiles();
	public void testReadWords() {
		Map<String,Integer> hmInvalid = lfs.readWords("invalidfilepath");
		assertEquals(hmInvalid.isEmpty(),true);		
		
		Map<String,Integer> hmValid = lfs.readWords("files");
		assertEquals(hmValid.isEmpty(),false);
		
		Map<String,Integer> hmCount = lfs.readWords("files");
		assertEquals(hmCount.get("dolor"),new Integer(8)); //Postive test
		assertEquals(hmCount.get("in"),new Integer(15));  // Lower and Uppercase test
		assertEquals(hmCount.get("prasant"),null);  // Negative test
		
		//Map<String,Integer> hmSortAsc=lfs.sortByValues(hmValid,true); //Ascending Order Test
		//assertEquals(new ArrayList<String>(hmSortAsc.keySet()).indexOf("estfringillarutrum"),1);
		
		//Map<String,Integer> hmSortDesc=lfs.sortByValues(hmValid,false); //Descending Order Test
		//assertEquals(new ArrayList<String>(hmSortDesc.keySet()).indexOf("in"),15);
		
	}

}

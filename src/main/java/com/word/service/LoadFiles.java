package com.word.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.word.web.ApplicationCache;

public class LoadFiles {
	
	 private String filePath;
	 private boolean sortAscOrder;
	 
	 public boolean getSortAscOrder() {
		return sortAscOrder;
	}

	public void setSortAscOrder(boolean sortAscOrder) {
		this.sortAscOrder = sortAscOrder;
	}

	public String getFilePath() {
	     return filePath;
	 }
	
	 public void setFilePath(String filePath) {
	     this.filePath = filePath;
	 }
	
	@Autowired
    private ApplicationCache appcache;
	
	// Load the bean on startup so that file is loaded on startup
    @PostConstruct
    public void init() {
    	try {
    	 Map<String, Integer> WordCountMap= readWords(filePath);
    	 appcache.setWordMap(WordCountMap);
    	 Map<String, Integer> sortedMapDesc= sortByValues(WordCountMap,sortAscOrder);
    	 appcache.setSortedMap(sortedMapDesc);
    	} catch(Exception e) {
    		System.out.println(e.getMessage());
    	}
    }
    
    // Reads the TXT file and loads them in to HashMap
    public Map<String, Integer> readWords(String path) {
    	Map<String, Integer> wordCount = new HashMap<String, Integer>();     //Creates an Hash Map for storing the words and its count
    	try {
    		ClassLoader classLoader = getClass().getClassLoader();
        	File directory = new File(classLoader.getResource(path).getPath());
            File[] listOfFiles = directory.listFiles();//To get the list of file-names found at the "directoy"
            BufferedReader br = null;
            String words[] = null;
            String line;
            String files;
            int i=0;          
            for (File file : listOfFiles) { 
                if (file.isFile()) {
                    files = file.getName();
                    try {
                        if (files.endsWith(".txt") || files.endsWith(".TXT")) {  //Checks whether an file is an text file 
                            br = new BufferedReader(new FileReader(file));      //creates an Buffered Reader to read the contents of the file
                            while ((line = br.readLine()) != null) {
                                line = line.toLowerCase();
                                words = line.replaceAll("[^0-9a-zA-Z\\s]", " ").split("\\s+");  //Splits the words with "space" as an delimeter 
    		                    for (String read : words) {
    		                    	i++;
    		                        Integer freq = wordCount.get(read);		                        
    		                        wordCount.put(read, (freq == null) ? 1 : freq + 1); //For Each word the count will be incremented in the Hashmap
    		                    }
                            }
                            br.close();
                        }

                    } catch (NullPointerException | IOException e) {
                        System.out.println("Could not read files:" + e);
                    }

                }
                //System.out.println(wordCount.size() + " distinct words:");     //Prints the Number of Distinct words found in the files read
                //System.out.println(wordCount);                                 //Prints the Word and its occurrence
                //System.out.println(i +" Total Words");              			//Prints the Total Words
               
            }
        } catch(Exception e) {
        	System.out.println(e.getMessage());
        }
            return wordCount;
        }
    	
    	// Sort the word count map based on highest word count 
        public static Map<String, Integer> sortByValues(Map<String, Integer> unsortMap, final boolean order)
        {
            List<Entry<String, Integer>> list = new LinkedList<Entry<String, Integer>>(unsortMap.entrySet());
            // Sorting the list based on values
            Collections.sort(list, new Comparator<Entry<String, Integer>>()
            {
                public int compare(Entry<String, Integer> o1,Entry<String, Integer> o2)
                {
                    if (order)
                    {
                        return o1.getValue().compareTo(o2.getValue());
                    }
                    else
                    {
                        return o2.getValue().compareTo(o1.getValue());
                    }
                }
            });

            // Maintaining insertion order with the help of LinkedList
            Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
            for (Entry<String, Integer> entry : list)
            {
                sortedMap.put(entry.getKey(), entry.getValue());
            }
            return sortedMap;
        }

  }
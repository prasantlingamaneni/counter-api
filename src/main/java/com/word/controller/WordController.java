package com.word.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.word.model.Word;
import com.word.web.ApplicationCache;

	
	@RestController
	public class WordController {
		
		@Autowired
		private ApplicationCache appcache;
		
		//TASK1 - Retrieve count of given words
		@RequestMapping(value = "/search", method = RequestMethod.POST,headers="Accept=application/json")
		public Word getWordCount(HttpServletRequest request)
		{
			Word wrd = new Word();
			HashMap listOfWords = new HashMap();
			Map<String,Integer> wMap= appcache.getWordMap();
			try {
				String payloadRequest = getBody(request);
				
				JSONParser jsonParser = new JSONParser();
				JSONObject jsonObject = (JSONObject) jsonParser.parse(payloadRequest);
				JSONArray lang= (JSONArray) jsonObject.get("searchText");
				for(int i=0; i<lang.size(); i++){
					Integer cnt=wMap.get(lang.get(i));
					listOfWords.put(lang.get(i),(cnt == null) ? 0 : cnt);
				}				
				wrd.setCounts(listOfWords);
			} catch(Exception e){
				System.out.println(e.getMessage());
			}
			return wrd;
		}
		
		//TASK2 - Retrieve top X words with highest count
		@RequestMapping(value = "/top/{num}")
		public @ResponseBody void getListInCSV(@PathVariable int num, HttpServletResponse response)
		{
			Map<String,Integer> sMap= appcache.getSortedMap();			
			ArrayList<Entry<String, Integer>> topList = new ArrayList<Entry<String, Integer>>();
			try {
				response.setContentType("text/csv");
				if(num <=sMap.size()) {
					ArrayList<Entry<String, Integer>> rows = new ArrayList<Entry<String, Integer>>(sMap.entrySet());
					for (int i = 0; i < num; i++) {
						topList.add(rows.get(i));
					}
			 
					Iterator<Entry<String, Integer>> iter = topList.iterator();
					while (iter.hasNext()) {
						Entry<String, Integer> outputString = iter.next();
						response.getOutputStream().println(outputString.getKey()+"|"+outputString.getValue());
					}
				} else {
					response.getOutputStream().println("Given number is more than available words");
				}
				response.getOutputStream().flush();
			} catch(Exception e){
				System.out.println(e.getMessage());
			}
		 			
		}
		
		//load the JSON data from the http body
		public static String getBody(HttpServletRequest request) throws IOException {

		    String body = null;
		    BufferedReader bufferedReader = null;
		    StringBuffer jb = new StringBuffer();
		    String line = null;
		    try {
		    	bufferedReader = request.getReader();
		        while ((line = bufferedReader.readLine()) != null) {
		          jb.append(line);
		        }
		        
		    } catch (IOException ex) {
		        throw ex;
		    } finally {
		        if (bufferedReader != null) {
		            try {
		                bufferedReader.close();
		            } catch (IOException ex) {
		                throw ex;
		            }
		        }
		    }

		    body = jb.toString();
		    return body;
		}
		
	}


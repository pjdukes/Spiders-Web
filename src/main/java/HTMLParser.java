package test;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Stack;

public class HTMLParser {
	
	private class Pair {
		int start = 0, end = 0;
	}
	
	/**
	 * parseHTML
	 * @param html - String of page HTML
	 * @return HashMap of <String, <LinkedList<String>> of either 0 length (in case of missing String HTML), or 
	 * containing exactly the same number of elements as the string had valid html tags (which also may be 0)
	 */
	public HashMap<String, LinkedList<String>> parseHTML(String html){
		char[] htmlArray = html.toCharArray();
		if (htmlArray.length == 0)
			return new HashMap<String, LinkedList<String>>(0);
		
		LinkedList<Pair> tagPositions = new LinkedList<Pair>();
		Stack<Pair> tagStartEnd = new Stack<Pair>();
		int tagPlacement = 0;
	
		for (int x = 0; x < htmlArray.length; x++) {	
			
			//If this character ends a tag
			if (htmlArray[x] == '>' && tagPlacement % 2 == 0) {
				tagStartEnd.peek().end = x;
				tagPositions.addLast((tagStartEnd.pop()));
			}
			
			//If this character starts a tag
			if (htmlArray[x] == '<' && htmlArray[x + 1] != '/') {
				Pair pair = new Pair();
				pair.start = x;
				tagStartEnd.push(pair);//Add to linked list to make sure we aren't rewriting top pair over and over
				tagPlacement++;
			}
			
			//If this character is starting to end a tag
			if (htmlArray[x] == '/') {
				tagPlacement--;
			}
		}
		
		return getTags(html, tagPositions);
	}
	
	/**
	 * getTag
	 * 
	 * @param html - String of webpage data
	 * @param tagStartEnd - List of all tag start and ends by index of character in html
	 * @return
	 */
	private HashMap<String, LinkedList<String>> getTags(String html, LinkedList<Pair> tagPositions) {
		HashMap<String, LinkedList<String>> mapping = new HashMap<String, LinkedList<String>>(150);
		
		for (Pair pair: tagPositions) {
			//Get full tag
			String tag = html.substring(pair.start, pair.end);
			char[] tagArray = tag.toCharArray();
			
			//Get tag key (what tag is is, like <p> or whatever
			boolean tagEnded = false;
			int tagEnd = 0;
			for (int x = 0; x < tagArray.length; x++) {
				//If the tag type name has started
				if (tagArray[x] != '<' && tagArray[x] != ' ')
					tagEnded = true;
				
				if (tagEnded && (tagArray[x] == ' ' || tagArray[x] == '>'))
					tagEnd = x;
				
				continue;
			}
			
			//Add tag to hashmap
			String tagType = tag.substring(0,tagEnd); //tag type, like <p>
			//Tag type already exists in mapping
			if (mapping.get(tagType) != null) {
				mapping.get(tagType).addLast(tag);
			} else {
			
			//Tag type needs to be created in mapping
				mapping.put(tagType, new LinkedList<String>());
				mapping.get(tagType).addLast(tag);
			}
		}
		return mapping;
	}
	
}

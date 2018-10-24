import java.

public class HTMLParser {
	
	public class Node {
		String key, value;
		
		Node(String key, String value){
			this.key = key;
			this.value = value;
		
			String getKey(){
				return key;
			}
		
			String getValue(){
				return value;
			}
		
		}
	}
	
	HashMap<LinkedList<Node>> HTMLTags = new HashMap<LinkedList<Node>>(50);
	
}
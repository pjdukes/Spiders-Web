import java.util.*;
import static org.junit.Assert.*;
import org.junit.Test;



public class CrawlerTest {
	
	@Test
	public void testLinkedListSize() {
		Crawler test = new Crawler();
		ArrayList<String> linkList = new ArrayList<>();
		test.crawl("http://www.google.com", linkList, 1);
		assertTrue("The size should be greater than 0", linkList.size() >= 1);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testError() {
		Crawler test = new Crawler();
		ArrayList<String> linkList = new ArrayList<>();
		test.crawl("bad.link", linkList, 1);
	}
}

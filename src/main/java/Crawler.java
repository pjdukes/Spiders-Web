import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class Crawler {

	public ArrayList<String> crawl (String firstLink, ArrayList<String> linkList, int limit) {
		
		Elements links;
		Document doc;
		
		try {
			doc = Jsoup.connect(firstLink).get();
			links = doc.select("a[href]");
			for (Element link : links) {
				if (!(linkList.contains(link.attr("abs:href")))) {
					linkList.add(link.attr("abs:href"));
				}
			}
		} catch (IOException e) {
			System.out.println("Please provide a valid URL or Link");
			System.out.println("Please make sure the link includes HTTP:// HTTPS:// and www. ");
			System.out.println("Here is an example of a valid URL: http://www.google.com");
		}

		for (int i = 1; i <= limit; i++) {
			try {
				if (i >= linkList.size()) {
					System.out.println("All Links Searched Exiting.");
					break;
				}
				System.out.println(i + " = " + linkList.get(i));
				if (!(linkList.get(i).startsWith("http"))) {
					linkList.remove(i);
					continue;
				}
				doc = Jsoup.connect(linkList.get(i)).get();
				links = doc.select("a[href]");
				for (Element link : links) {
					if (!(linkList.contains(link.attr("abs:href")))) {
						linkList.add(link.attr("abs:href"));
					}
				}
				System.out.println("Total number of links found: " + linkList.size());

			} catch (IOException e) {
				System.err.println("Bad Link Encountered... Moving On");
				continue;
			}
		}

        System.out.println("\nLimit Reached");
        System.out.println("Total Number Of Links Found: \n" + linkList.size());
		return linkList;
	}
}
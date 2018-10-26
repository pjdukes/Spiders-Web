import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Crawler {
	
	
	public static ArrayList<String> crawl (String firstLink, ArrayList<String> linkList, int limit) {
		
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
			System.err.println("Please provide a valid URL or Link");
		}

		for (int i = 0; i < limit; i++) {
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
				System.out.println(linkList.size());

			} catch (IOException e) {
				System.err.println("Bad Link Encountered... Moving On");
				continue;
			}
		}
		return linkList;
	}

	public static void main(String[] args) {
		int limit = 25;
		ArrayList<String> linkList = new ArrayList<>();
		
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter the site to start crawling at: ");
		String firstLink = scan.nextLine();
		System.out.println("");
		
		linkList = crawl(firstLink, linkList, limit);
		
		System.out.println("");
		System.out.println("Limit Reached");
		System.out.println("Total Number Of Links Found: ");
		System.out.println(linkList.size());
		

	}
}
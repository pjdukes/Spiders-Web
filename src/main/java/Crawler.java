import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;

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

        System.out.println("\nLimit Reached");
        System.out.println("Total Number Of Links Found: \n" + linkList.size());
		return linkList;
	}
	
	public static String getData(String link) {
		String data = "";
		Document doc = null;
		
		try {
			doc = Jsoup.connect(link).get();
			data += doc.select("html");
		} catch (IOException e) {
			e.printStackTrace();
		}
		//System.out.println(data);
		
		return data;
	}
	
	public static void storeData(String link) throws MalformedURLException {
		DBO dbo = new DBO();
		Connection c = dbo.connectDB();
		URL aURL = new URL(link);
		String protocol = null;
		String domain = null;
		String path = null;
		protocol = aURL.getProtocol();
		domain = aURL.getHost();
	    String TLD[] = domain.split("\\.");
		path = aURL.getPath();
		//dbo.insertTag(domain, ".".concat(TLD[TLD.length - 1]), protocol, tag, data, c);
		
	}
}
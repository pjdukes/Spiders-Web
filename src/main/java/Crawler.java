import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Crawler {

	public static void main(String[] args) {
		int limit = 250;
		Elements links;
		Document doc;
		ArrayList<String> linkList = new ArrayList<>();

		try {
			doc = Jsoup.connect("https://oli.org/").get();
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
	}
}
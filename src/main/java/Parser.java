import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;

public class Parser {

	/**
	 * getAndStoreTags
	 * Gets HTML tags from websites along with domain, top level domain, and protocol
	 * Connection c - A connection to a SQLite Database
	 * String start - The address to reconnect to and gain information from
	 */
    public void getAndStoreTags(Connection c, String start) {
        // domain, tld, path, protocol, tag, innerdata, c
        DBO db = new DBO();

        String domain = "";
        String[] TLD = null;
        String path = "";
        String protocol = "";
        String tag = "";
        String innerData = "";

        try {
            URL aURL = new URL(start);
            domain = aURL.getHost();
            TLD = domain.split("\\.");
            path = aURL.getPath();
            protocol = aURL.getProtocol();
        } catch (MalformedURLException mex) {
        	System.out.println("The following URL encountered errors when being stored to the database: " + start);
        	System.out.println("This URL will not be stored to the database");
        }

        Document doc;

        try {
            doc = Jsoup.connect(start).get();
            for(Element e : doc.getAllElements()){// all elements in html
                db.insertTag(domain, ".".concat(TLD[TLD.length - 1]), path, protocol, e.tagName(), e.html(), c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
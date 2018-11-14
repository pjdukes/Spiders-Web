import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;

public class Parser {

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
            mex.printStackTrace();
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
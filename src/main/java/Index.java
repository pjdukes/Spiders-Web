import java.util.ArrayList;
import java.util.Scanner;
import java.net.MalformedURLException;

public class Index {
    public static void main(String[] args) {
        Crawler crawler = new Crawler();
        Parser ps = new Parser();
        int crawlLimit = 25;
        int dataLimit = 5;
        String data = null;
        ArrayList<String> linkList = new ArrayList<>();

        Scanner scan = new Scanner(System.in);
        System.out.println("Enter the site to start crawling at: ");
        String firstLink = scan.nextLine();
        System.out.println("");


        linkList = crawler.crawl(firstLink, linkList, crawlLimit);
        for (int i = 0; i < linkList.size() && i < dataLimit; i++) {
            ps.getAndStoreTags(linkList.get(i));
        }
    }
}
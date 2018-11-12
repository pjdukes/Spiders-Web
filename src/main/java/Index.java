import java.sql.Connection;
import java.util.ArrayList;
import java.util.Scanner;

public class Index {
    public static void main(String[] args) {
        Crawler crawler = new Crawler();
        Parser ps = new Parser();
        DBO db = new DBO();
        String data = null;
        ArrayList<String> linkList = new ArrayList<>();
        Connection c = db.connectDB();

        Scanner scan = new Scanner(System.in);
        System.out.println("Enter the site to start crawling at: ");
        String firstLink = scan.nextLine();
        System.out.println("Enter the number of sites you would like to crawl: ");
        int crawlLimit = scan.nextInt();
        System.out.println("Enter the number of sites that you would like to store in the data base: ");
        int dataLimit = scan.nextInt(); 
        System.out.println("");


        linkList = crawler.crawl(firstLink, linkList, crawlLimit);
        for (int i = 0; i < linkList.size() && i < dataLimit; i++) {
            ps.getAndStoreTags(c, linkList.get(i));
        }
        
        System.out.println("\nData has been stored in the database!\n");
        System.out.println("What would you like to do now?");
        System.out.println("[1]: Get a pie chart of all TLDs");
        System.out.println("[2]: Get a graph of all domains");
        System.out.println("[3]: Dump the database to a file");
    }
}
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
		boolean loop = true;

		Scanner scan = new Scanner(System.in);
		System.out.println("Enter the site to start crawling at: ");
		String firstLink = scan.nextLine();
		System.out.println("Enter the number of sites you would like to crawl: ");
		int crawlLimit = scan.nextInt();
		System.out.println("Enter the number of sites that you would like to store in the data base: ");
		int dataLimit = scan.nextInt();
		System.out.println("");
		
		System.out.println("Crawling finished... data is being stored to the database, this may take some time");

		linkList = crawler.crawl(firstLink, linkList, crawlLimit);
		for (int i = 0; i < linkList.size() && i < dataLimit; i++) {
			ps.getAndStoreTags(c, linkList.get(i));
		}
		
		System.out.println("\nData has been stored in the database!\n");
		
		while (loop) {
			System.out.println("What would you like to do now?");
			System.out.println("[1]: Get the data on all TLDs");
			System.out.println("[2]: Get data on domains");
			System.out.println("[3]: Export the database to a CSV file");
			System.out.println("[4]: Exit the program");

			switch (scan.nextInt()) {
			case 1:
				// Get data on TLDs
				break;
			case 2:
				// Get data on domains
				break;
			case 3:
				// Export data
				break;
			case 4:
				loop = false;
				break;
			default:
				System.out.println("Please enter a valid option\n");
				break;
			}
		}
		
		System.out.println("Program Exiting");
	}
}
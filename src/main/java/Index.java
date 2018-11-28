import java.sql.Connection;
import java.util.ArrayList;
import java.util.Scanner;
import java.sql.*;
import java.io.*;

public class Index {
	public static void main(String[] args) {
		Crawler crawler = new Crawler();
		Parser ps = new Parser();
		DBO db = new DBO();
		String data = null;
		ArrayList<String> linkList = new ArrayList<>();
		Connection c = db.connectDB(false);
		boolean loop = true;
		String tmp;
		Scanner scan = new Scanner(System.in);

		System.out.println("   +----------------------------------------------------------");
		System.out.println("   :\". /  /  /        Welcome To Spiders Web!");
		System.out.println("   :.-\". /  /");
		System.out.println("   : _.-\". /");
		System.out.println("   :\"  _.-\".");
		System.out.println("   :-\"\"     \".");
		System.out.println("   :");
		System.out.println("   :");
		System.out.println(" ^.-.^");
		System.out.println("'^\\+/^`");
		System.out.println("'/`\"'\\`\n");

		try {
			db.insertTag("", "", "", "", "", "", c);
			System.out.println("The database is correctly configured");
		} catch (SQLException e) {
			System.out.println("web.db cannot be found or is corrupted");
			try {
				db.makeTables(c);
			} catch (FileNotFoundException e2) {
				System.out.println("File to create tables not found");
			}
		}
		
		System.out.println("Enter the site to start crawling at: ");
		String firstLink = scan.nextLine();
		System.out.println("Enter the number of sites you would like to crawl: ");
		int crawlLimit = scan.nextInt();
		System.out.println("Enter the number of sites that you would like to store in the data base: ");
		int dataLimit = scan.nextInt();
		System.out.println("");

		linkList = crawler.crawl(firstLink, linkList, crawlLimit);
		System.out.println("Crawling finished... data is being stored to the database, this may take some time");
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

			switch (scan.next()) {
			case "1":
				// Get data on TLDs
				while (loop) {
					System.out.println("How would you like the data?");
					System.out.println("[1] Pie Chart");
					System.out.println("[2] Bar Chart");
					System.out.println("[3] Go Back");
					switch (scan.next()) {
					case "1":
						// Make chart
						System.out.println("Would you like to save the chart? Y/n");
						tmp = scan.next();
						if (tmp.substring(0, 1).toLowerCase().equals("y")) {
							// Save file
							System.out.println("File saved");
						}
						break;
					case "2":
						// Make chart
						System.out.println("Would you like to save the chart? Y/n");
						tmp = scan.next();
						if (tmp.substring(0, 1).toLowerCase().equals("y")) {
							// Save file
							System.out.println("File saved");
						}
						break;
					case "3":
						loop = false;
						break;
					default:
						System.out.println("Please enter a valid option");
						break;
					}
				}
				loop = true;
				break;
			case "2":
				// Get data on domains
				while (loop) {
					System.out.println("How would you like the data?");
					System.out.println("[1] Pie Chart");
					System.out.println("[2] Bar Chart");
					System.out.println("[3] Go Back");
					switch (scan.next()) {
					case "1":
						// Make chart
						System.out.println("Would you like to save the chart? Y/n");
						tmp = scan.next();
						if (tmp.substring(0, 1).toLowerCase().equals("y")) {
							// Save file
							System.out.println("File saved");
						}
						break;
					case "2":
						// Make chart
						System.out.println("Would you like to save the chart? Y/n");
						tmp = scan.next();
						if (tmp.substring(0, 1).toLowerCase().equals("y")) {
							// Save file
							System.out.println("File saved");
						}
						break;
					case "3":
						loop = false;
						break;
					default:
						System.out.println("Please enter a valid option");
						break;
					}
				}
				loop = true;
				break;
			case "3":
				System.out.println("What would you like to name the file?");
				String filename = scan.next();
				// Export data method
				break;
			case "4":
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
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Scanner;
import java.sql.*;
import java.io.*;

public class Index {
	
	public static final Scanner scan = new Scanner(System.in);
	
	/**
	 * main
	 * Querys user for site to crawl, and number of sights to crawl and store
	 * args - not used
	 */
	public static void main (String args[]) {
		//Introduction
		
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
		
		System.out.println("Enter the site to start crawling at: ");
		String firstLink = scan.nextLine();
		System.out.println("Enter the number of sites you would like to crawl: ");
		int crawlLimit = scan.nextInt();
		scan.nextLine();
		System.out.println("Enter the number of sites that you would like to store in the data base: ");
		int dataLimit = scan.nextInt();
		scan.nextLine();
		System.out.println("");
		//scan.close();
		indexMain(firstLink, dataLimit, crawlLimit, true);
	}
	
	/**
	 * indexMain
	 * firstLink - First link to crawl
	 * dataLimit - Number of links (maximum) to store HTML
	 * crawlLimit - Number of links (maximum) to crawl
	 * operateRecord - Whether or not to query user for options to display data
	 * @return True if closes successfully
	 */
	public static boolean indexMain(String firstLink, int dataLimit, int crawlLimit, boolean operateRecord) throws IndexOutOfBoundsException {
		//if (firstLink != null)
			//throw new IndexOutOfBoundsException("Worked");
		Crawler crawler = new Crawler();
		Parser ps = new Parser();
		DBO db = new DBO();
		String data = null;
		ArrayList<String> linkList = new ArrayList<>();
		Connection c = db.connectDB(false);
	
		

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

		linkList = crawler.crawl(firstLink, linkList, crawlLimit);
		System.out.println("Crawling finished... data is being stored to the database, this may take some time");
		for (int i = 0; i < linkList.size() && i < dataLimit; i++) {
			ps.getAndStoreTags(c, linkList.get(i));
		}
		System.out.println("\nData has been stored in the database!\n");

		return indexRecord(operateRecord, db, c);
	}
	
	/**
	 * indexRecord
	 * boolean operate
	 * DBO db - A database object which allows parsing of data 
	 * 		from a database connection to query and retrive data
	 * Connection c - Connection to a SQLite database
	 */
	public static boolean indexRecord(boolean operate, DBO db, Connection c) {
	    Visualize v = new Visualize();
		boolean loop = true;
		
		if (operate == false) {
			return true;
		}
		
//		System.out.println("TESTING SCANNER, PLEASE INPUT SOMETHING");
//		String b = scan.next();
//		System.out.println("b = " + b);
		
		
		
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
					    ArrayList<String> al1 = new ArrayList<>();
					    ArrayList<Integer> al2 = new ArrayList<>();
						int flag = 0;
						System.out.println("Would you like to save the chart? Y/N");
						if (scan.next().substring(0, 1).toLowerCase().equals("y")) {
							flag = 1;
                            System.out.println("File will be saved");
						}
                        al1 = db.getTlds(c);
						al2 = db.queryByTag(al1, c);
						v.makePieChart(al1, al2, flag, "testChart");
						break;
					case "2":
                        ArrayList<String> al1 = new ArrayList<>();
                        ArrayList<Integer> al2 = new ArrayList<>();
                        int flag = 0;
                        System.out.println("Would you like to save the chart? Y/N");
                        if (scan.next().substring(0, 1).toLowerCase().equals("y")) {
                            flag = 1;
                            System.out.println("File will be saved");
                        }
                        al1 = db.getTlds(c);
                        al2 = db.queryByTag(al1, c);
                        v.makeBarChart(al1, al2, flag, "testChart");
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
                        ArrayList<String> al1 = new ArrayList<>();
                        ArrayList<Integer> al2 = new ArrayList<>();
                        int flag = 0;
                        System.out.println("Would you like to save the chart? Y/N");
                        if (scan.next().substring(0, 1).toLowerCase().equals("y")) {
                            flag = 1;
                            System.out.println("File will be saved");
                        }
                        al1 = db.getDomains(c);
                        al2 = db.queryByTag(al1, c);
                        v.makePieChart(al1, al2, flag, "testChart");
						break;
					case "2":
                        ArrayList<String> al1 = new ArrayList<>();
                        ArrayList<Integer> al2 = new ArrayList<>();
                        int flag = 0;
                        System.out.println("Would you like to save the chart? Y/N");
                        if (scan.next().substring(0, 1).toLowerCase().equals("y")) {
                            flag = 1;
                            System.out.println("File will be saved");
                        }
                        al1 = db.getDomains(c);
                        al2 = db.queryByTag(al1, c);
                        v.makeBarChart(al1, al2, flag, "testChart");
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
				if (!filename.contains(".csv"))
				{
					///System.out.println("looks like you forgot the .csv");
					filename = filename + ".csv";
					//System.out.println(filename);
				}
				try {
					db.exportAsCSV(filename, c);
				} catch (IOException e) {
					e.printStackTrace();
				}
				
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
		scan.close();
		return true;
	}
}
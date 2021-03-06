import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
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
		indexMain(firstLink, dataLimit, crawlLimit, true);
	}
	
	private static void printProgress(long startTime, long total, long current) {
	    long eta = current == 0 ? 0 : 
	        (total - current) * (System.currentTimeMillis() - startTime) / current;

	    String etaHms = current == 0 ? "N/A" : 
	            String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(eta),
	                    TimeUnit.MILLISECONDS.toMinutes(eta) % TimeUnit.HOURS.toMinutes(1),
	                    TimeUnit.MILLISECONDS.toSeconds(eta) % TimeUnit.MINUTES.toSeconds(1));

	    StringBuilder string = new StringBuilder(140);   
	    int percent = (int) (current * 100 / total);
	    string
	        .append('\r')
	        .append(String.join("", Collections.nCopies(percent == 0 ? 2 : 2 - (int) (Math.log10(percent)), " ")))
	        .append(String.format(" %d%% [", percent))
	        .append(String.join("", Collections.nCopies(percent, "=")))
	        .append('>')
	        .append(String.join("", Collections.nCopies(100 - percent, " ")))
	        .append(']')
	        .append(String.join("", Collections.nCopies(current == 0 ? (int) (Math.log10(total)) : (int) (Math.log10(total)) - (int) (Math.log10(current)), " ")))
	        .append(String.format(" %d/%d, ETA: %s", current, total, etaHms));

	    System.out.print(string);
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
		Crawler crawler = new Crawler();
		Parser ps = new Parser();
		DBO db = new DBO();
		String data = null;
		ArrayList<String> linkList = new ArrayList<>();
		Connection c = db.connectDB(false);
	
		

		try {
			db.insertTag("", "", "", "", "", "", c);
			String sql = "DELETE FROM Tags WHERE name = \"\"";
			PreparedStatement p = c.prepareStatement(sql);
			p.executeUpdate();
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
		long startTime = System.currentTimeMillis();
		int smallest = (linkList.size() < dataLimit) ? linkList.size() : dataLimit;
		for (int i = 0; i <= linkList.size() && i <= dataLimit; i++) {
			ps.getAndStoreTags(c, linkList.get(i));
			printProgress(startTime, smallest, i);
		}
		System.out.println("\nData has been stored in the database!\n");

		return indexRecord(operateRecord, db, c);
	}
	
	/**
	 * indexRecord
	 * Queries user for data usage in the form of visual charts and .csv outputs
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
					    String filename1 = null;
						int flag1 = 0;
						System.out.println("Would you like to save the chart? Y/N");
						if (scan.next().substring(0, 1).toLowerCase().equals("y")) {
						    System.out.println("What would you like to name the file?");
                            filename1 = scan.next();
							flag1 = 1;
                            System.out.println("File will be saved");
						}
                        al1 = db.getTlds(c);
						al2 = db.queryByTld(c);
						v.makePieChart(al1, al2, flag1, filename1);
						break;
					case "2":
                        ArrayList<String> al3 = new ArrayList<>();
                        ArrayList<Integer> al4 = new ArrayList<>();
                        String filename2 = null;
                        int flag2 = 0;
                        System.out.println("Would you like to save the chart? Y/N");
                        if (scan.next().substring(0, 1).toLowerCase().equals("y")) {
                            System.out.println("What would you like to name the file?");
                            filename2 = scan.next();
                            flag2 = 1;
                            System.out.println("File will be saved");
                        }
                        al3 = db.getTlds(c);
                        al4 = db.queryByTld(c);
                        v.makeBarChart(al3, al4, flag2, filename2);
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
                        String filename1 = null;
                        int flag1 = 0;
                        System.out.println("Would you like to save the chart? Y/N");
                        if (scan.next().substring(0, 1).toLowerCase().equals("y")) {
                            System.out.println("What would you like to name the file?");
                            filename1 = scan.next();
                            flag1 = 1;
                            System.out.println("File will be saved");
                        }
                        al1 = db.getDomains(c);
                        al2 = db.queryByDomain(c);
                        v.makePieChart(al1, al2, flag1, filename1);
						break;
					case "2":
                        ArrayList<String> al3 = new ArrayList<>();
                        ArrayList<Integer> al4 = new ArrayList<>();
                        String filename2 = null;
                        int flag2 = 0;
                        System.out.println("Would you like to save the chart? Y/N");
                        if (scan.next().substring(0, 1).toLowerCase().equals("y")) {
                            System.out.println("What would you like to name the file?");
                            filename2 = scan.next();
                            flag2 = 1;
                            System.out.println("File will be saved");
                        }
                        al3 = db.getDomains(c);
                        al4 = db.queryByDomain(c);
                        v.makeBarChart(al3, al4, flag2, filename2);
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
			    // Export data method
				System.out.println("What would you like to name the file?");
				String filename = scan.next();
				if (!filename.contains(".csv"))
				{
					filename = filename + ".csv";
				}
				try {
					db.exportAsCSV(filename, c);
				} catch (IOException e) {
					e.printStackTrace();
				}
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
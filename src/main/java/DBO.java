import java.sql.*;
import java.util.*;
import java.nio.file.*;
import java.io.*;

public class DBO {

	/**
	 * connectDB
	 * Connects to a database, returning it for reference
	 * boolean flag - Whether or not connection should be made to
	 * 			a test database, or a real one (used for testing)
	 * @return Connection object which connects to the chosen database
	 */
	public Connection connectDB(boolean flag) {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {

		}
		Connection connection = null;
		try {
			if (flag) {
				connection = DriverManager.getConnection("jdbc:sqlite:mock.db");
			} else {
				connection = DriverManager.getConnection("jdbc:sqlite:web.db");
			}
			System.out.println("Connection to the database has been established");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connection;
	}

	/**
	 * exportAsCSV
	 * String filename - Name of file to be made
	 * Connection c - Connection to database
	 * @return 1 if the CSV is created (empty or not), 0 if it fails
	 */
	public int exportAsCSV(String filename, Connection c) throws IOException {

		FileWriter f = new FileWriter(filename);
		String sql = "SELECT * FROM Tags";
		ResultSet data = null;
		try {
			Statement s = c.createStatement();
			data = s.executeQuery(sql);
			while (data.next()) {
				f.append(data.getString(1));
				f.append(',');
				f.append(data.getString(2));
				f.append(',');
				f.append(data.getString(3));
				f.append(',');
				f.append(data.getString(4));
				f.append(',');
				f.append(data.getString(5));
				f.append(',');
				f.append(data.getString(6));
				f.append('\n');
			}
			f.flush();
			f.close();

			return 1;
		} catch (SQLException e) {
			return 0;
		}

	}

	/**
	 * queryByTag
	 * ArrayList<String> s - List of tags to query
	 * Connection c - Connection to the database to query
	 * @return Count of respective tags in the database
	 */
	public ArrayList<Integer> queryByTag(ArrayList<String> s, Connection c) {
		ArrayList<Integer> results = new ArrayList<Integer>();
		for (String str : s){
			ResultSet rs;
			String sql = "SELECT COUNT(*) AS count FROM Tags WHERE tag = ?";
			try {
				PreparedStatement p = c.prepareStatement(sql);
				p.setString(1, str);
				rs = p.executeQuery();
				int count = rs.getInt("count");
				results.add(count);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return results;
		
		
	}

	/**
	 * insertTag
	 * Inserts a single tag into the connected database
	 * String d - Domain
	 * String TLD - Top level domain of link
	 * String path - Full path
	 * String protocol - Internet transfer protocol as detected
	 * String tag - HTML tag to be stored
	 * String innerData - Inner data of the tag (currently disabled insertion due to lack of testing storage)
	 * Connection c - Connection to the database
	 */
	public void insertTag(String d, String TLD, String path, String protocol, String tag, String innerData,
			Connection c) throws SQLException {
		String sql = "INSERT INTO Tags VALUES(?, ?, ?, ?, ?, ?)";

		PreparedStatement p = c.prepareStatement(sql);
		p.setString(1, d);
		p.setString(2, TLD);
		p.setString(3, path);
		p.setString(4, protocol);
		p.setString(5, tag);
		p.setString(6, "");
		p.executeUpdate();

	}

	/**
	 * getTlds
	 * Finds and displays all unique top level domains in the Database
	 * Connection c - connection to database
	 * @return List of top level domains as strings
	 */
	public ArrayList<String> getTlds(Connection c) {
	    ArrayList<String> list = new ArrayList<>();
	    ResultSet rs;
	    String sql = "SELECT DISTINCT TLD AS topLD FROM Tags";
	    try {
	        PreparedStatement p = c.prepareStatement(sql);
	        rs = p.executeQuery();
	        while (rs.next()) {
                list.add(rs.getString("topLD"));
            }
        } catch (SQLException e) {
	        e.printStackTrace();
        }
        return list;
    }
	
	/**
	 * getTlds
	 * Finds and displays all unique domains in the Database
	 * Connection c - connection to database
	 * @return List of domains as strings
	 */
    public ArrayList<String> getDomains(Connection c) {
        ArrayList<String> list = new ArrayList<>();
        ResultSet rs;
        String sql = "SELECT DISTINCT name AS domain FROM Tags";
        try {
            PreparedStatement p = c.prepareStatement(sql);
            rs = p.executeQuery();
            while (rs.next()) {
                list.add(rs.getString("domain"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    /**
     * makeTables
     * Makes the appropriate table for the SQLite database
     * Connection c - Connection to the database
     */
    public static void makeTables(Connection c) throws FileNotFoundException {
		String sql = "CREATE TABLE Tags(name text, TLD text, path text, protocol text, tag text, innerData text)";
		
		try {
			PreparedStatement p = c.prepareStatement(sql);
			p.executeUpdate();
			System.out.println("Table created successfully");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
    
    /**
     * protocolCount
     * String protocol - Protocol to search for
     * Connection c - Connection to database
     * @return number of occurances of the protocol in the table
     */
	public int protocolCount(String protocol, Connection c) {
		String sql = "SELECT COUNT(*) AS count FROM Tags WHERE Protocol = ? ";
		int count = -1;
		ResultSet rs = null;
		try {
			PreparedStatement p = c.prepareStatement(sql);
			p.setString(1, protocol);
			rs = p.executeQuery();
			count = rs.getInt("count");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(count);
		return count;

	}
	
	/**
	 * uniqueDomainCount
	 * Finds and counts unique domains in the database
	 * Connection c - Connection to the database
	 * @return number of qunique domains in the table
	 */
	public static int uniqueDomainCount(Connection c) {
		String sql = "SELECT COUNT(DISTINCT name) AS count FROM Tags";
		int count = -1;
		ResultSet rs = null;
		try {
			PreparedStatement p = c.prepareStatement(sql);
			rs = p.executeQuery();
			count = rs.getInt("count");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(count);
		return count;
	}

	/**
	 * domainTagCount
	 * Finds the number of tags matching String tag in domains matching String domain
	 * String domain - Domain to search in relation to
	 * String tag - Tag to search in relation to
	 * Connection c - connection to Database
	 * @return number of tags resembling String tag in domains resembling String domain
	 */
	public static int domainTagCount(String domain, String tag, Connection c) {
		String sql = "SELECT COUNT(*) AS count FROM Tags WHERE tag = ? and name = ?";
		int count = -1;
		ResultSet rs = null;
		try {
			PreparedStatement p = c.prepareStatement(sql);
			p.setString(1, tag);
			p.setString(2, domain);
			rs = p.executeQuery();
			count = rs.getInt("count");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(count);
		return count;
	}


}

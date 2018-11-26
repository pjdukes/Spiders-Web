import java.sql.*;
import java.util.*;
import java.nio.file.*;
import java.io.*;

public class DBO {
	
	public class StringInt
	{
		String s;
		int i;
		
		public StringInt(String str, int num)
		{
			s = str;
			i = num;
		}
	}

	public Connection connectDB(boolean flag) {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {

		}
		Connection connection = null;
		try {
			if (flag)
			{
				connection = DriverManager.getConnection("jdbc:sqlite:mock.db");
			}
			else
			{
				connection = DriverManager.getConnection("jdbc:sqlite:web.db");
			}
			System.out.println("Connection to the database has been established");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connection;
	}

	public void insertTag(String d, String TLD, String path, String protocol, String tag, String innerData, Connection c) {
		String sql = "INSERT INTO Tags VALUES(?, ?, ?, ?, ?, ?)";
		try {
			PreparedStatement p = c.prepareStatement(sql);
			p.setString(1, d);
			p.setString(2, TLD);
			p.setString(3, path);
			p.setString(4, protocol);
			p.setString(5, tag);
			p.setString(6, innerData);
			p.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


//	public static void makeTables(Connection c) throws FileNotFoundException {
//
//		String basePath = new File("").getAbsolutePath();
//		System.out.println(basePath);
//
//		String path = new File("setupTables.sql").getAbsolutePath();
//
//		File script = new File(path);
//
//		// Path path = Paths.get("../../../setupTables.sql");
//		// try {
//		Scanner scanner = new Scanner(script);
//		// } catch (FileNotFoundException ex) {
//
//		// }
//
//
//		ArrayList<String> lines = new ArrayList();
//		String l = "";
//		while (scanner.hasNextLine()) {
//			lines.add(scanner.nextLine());
//			System.out.println("Adding " + l + " to arraylist");
//		}
//		
//		String sql = "DROP TABLE Tags;";
//		
//		try {
//			PreparedStatement p = c.prepareStatement(sql);
//			p.executeUpdate();
//			System.out.println("Tables dropped successfully");
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		for (int x = 0; x < lines.size(); x++) {
//			sql = lines.get(x);
//			System.out.println(sql);
//			try {
//				PreparedStatement p = c.prepareStatement(sql);
//				p.executeUpdate();
//				System.out.println("Table created successfully");
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//
//	}

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
	
	public static ArrayList<StringInt> tldStats(Connection c)
	{
		String sql = "SELECT DISTINCT TLD FROM Tags";
		ArrayList<StringInt> list = new ArrayList();
		return list;
	}



}

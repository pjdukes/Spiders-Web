import java.sql.*;
import java.util.*;
import java.nio.file.*;
import java.io.*;

public class DBO {

	public Connection connectDB() {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {

		}
		Connection connection = null;
		try {
			connection = DriverManager.getConnection("jdbc:sqlite:web.db");
			System.out.println("Connection has been established");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connection;
	}

	public void insertTag(String d, String TDL, String protocol, String tag, String innerData, Connection c) {
		String sql = "INSERT INTO Tags VALUES(?, ?, ?, ?, ?)";
		try {
			PreparedStatement p = c.prepareStatement(sql);
			p.setString(1, d);
			p.setString(2, TDL);
			p.setString(3, protocol);
			p.setString(4, tag);
			p.setString(5, innerData);
			p.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void clearDB(Connection c) throws FileNotFoundException {
		System.out.println("You must recreate the tables now");
		String sql = "DROP TABLE Tags";
		try {
			PreparedStatement p = c.prepareStatement(sql);
			p.executeUpdate();
			System.out.println("Database Cleared");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		makeTables(c);

	}

	public static void makeTables(Connection c) throws FileNotFoundException {

		String basePath = new File("").getAbsolutePath();
		System.out.println(basePath);

		String path = new File("setupTables.sql").getAbsolutePath();

		File script = new File(path);

		// Path path = Paths.get("../../../setupTables.sql");
		// try {
		Scanner scanner = new Scanner(script);
		// } catch (FileNotFoundException ex) {

		// }


		ArrayList<String> lines = new ArrayList();
		String l = "";
		while (scanner.hasNextLine()) {
			lines.add(scanner.nextLine());
			System.out.println("Adding " + l + " to arraylist");
		}

		for (int x = 0; x < lines.size(); x++) {
			String sql = lines.get(x);
			System.out.println(sql);
			try {
				PreparedStatement p = c.prepareStatement(sql);
				p.executeUpdate();
				System.out.println("Table created successfully");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public int protocolCount(String protocol, Connection c) {
		String sql = "SELECT COUNT(*) AS count FROM Tags  WHERE Protocol = ? ";
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

	public static void main(String[] args) {
		DBO test = new DBO();
		Connection c = test.connectDB();
		// test.insertDomain("Google", ".com", "https", c);

	}

}

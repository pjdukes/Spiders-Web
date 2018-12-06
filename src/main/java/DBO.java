import java.sql.*;
import java.util.*;
import java.nio.file.*;
import java.io.*;

public class DBO {


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

public static void makeTables(Connection c) throws FileNotFoundException {
		
		
		/*
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

		// String sql = "DROP TABLE Tags;";

		// try {
		// PreparedStatement p = c.prepareStatement(sql);
		// p.executeUpdate();
		// System.out.println("Tables dropped successfully");
		// } catch (SQLException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		 */
		String sql = "CREATE TABLE Tags(name text, TLD text, path text, protocol text, tag text, innerData text)";
		
		try {
			PreparedStatement p = c.prepareStatement(sql);
			p.executeUpdate();
			System.out.println("Table created successfully");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*
		for (int x = 0; x < lines.size(); x++) {
			sql = lines.get(x);
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
		*/

	}

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


}

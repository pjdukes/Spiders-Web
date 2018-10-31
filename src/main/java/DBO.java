import java.sql.*;

public class DBO {

	public Connection connectDB() {
		try {
		Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e)
		{
			
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
	
	public static void clearDB(Connection c) {
		System.out.println("You must recreate the tables now");
		String sql = "DROP TABLE Tags";
		try {
			PreparedStatement p = c.prepareStatement(sql);
			p.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		DBO test = new DBO();
		Connection c = test.connectDB();
		// 	 	test.insertDomain("Google", ".com", "https", c);
		

	}
}

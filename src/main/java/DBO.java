package jdbctest;

import java.sql.*;

public class DBO{

	public Connection connectDB() {
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

	public void disconnectDB() {

	}

	public void insertDomain(String d, String TDL, String protocol, Connection c) {
		String sql = "INSERT INTO Domain VALUES(?, ?, ?)";
		try {
			PreparedStatement p = c.prepareStatement(sql);
			p.setString(1, d);
			p.setString(2, TDL);
			p.setString(3, protocol);
			p.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void insertPath(String d, String TDL, String path, String data, Connection c)
	{
		String sql = "INSERT INTO Path VALUES(?, ?, ?, ?)";
		try {
			PreparedStatement p = c.prepareStatement(sql);
			p.setString(1, d);
			p.setString(2, TDL);
			p.setString(3, path);
			p.setString(4, data);
			p.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	public static void main(String[] args) {
		DBO test = new DBO();
		Connection c = test.connectDB();
		test.insertDomain("Google", ".com", "https", c);
		test.insertPath("Google", ".com", "/nice/", "html data", c);
	}
}


import java.util.*;
import static org.junit.Assert.*;
import org.junit.* ;
import org.junit.Test;
import java.io.*;

import java.sql.*;

public class DBTest {
	
	@BeforeClass
	public static void setupDatabase() throws IOException, SQLException
	{
		File f = new File("mock.db");
		if (f.createNewFile())
		{
			System.out.println("mock.db created successfully");
			DBO test = new DBO();
			Connection c = test.connectDB(true);
			test.makeTables(c);
			c.close();
		}
		else 
		{
			System.out.println("Failed to create mock.db");			
		}
		
	}
	
//	@AfterClass
//	public static void deleteDatabase()
//	{
//		File f = new File("mock.db");
//		System.out.println("f = " + f.getAbsoluteFile());
//		
//		if(f.delete()) 
//        { 
//            System.out.println("File deleted successfully"); 
//        } 
//        else
//        { 
//            System.out.println("Failed to delete the file"); 
//        } 
//	}
	
	@Test 
	public void testQueryByTag() throws SQLException {
		DBO test = new DBO();
		Connection c = test.connectDB(true);
		ArrayList<String> testArray = new ArrayList<String>();
		testArray.add("<p>");
		ArrayList<Integer> testVals = test.queryByTag(testArray, c);
		assertNotEquals((int)testVals.get(0), -1);
		c.close();
	}
	
	@Test
	public void testDatabaseConnection() throws SQLException {
		DBO test = new DBO();
		Connection c = test.connectDB(true);
		test.insertTag("Google", ".com", "/path", "https", "<tag>", "innerData", c);
		assertNotNull(test);
		c.close();

	}
	
	@Test
	public void testExportAsCSV() throws SQLException, IOException {
		DBO test = new DBO();
		Connection c = test.connectDB(true);
		int ret = test.exportAsCSV("testExport.csv", c);
		assertEquals(1, ret);
		c.close();
	}

	//@Ignore("We don't want to clear the database every time we test")
//	@Test
//	public void testClearDatabase() {
//		DBO test = new DBO();
//		Connection c = test.connectDB(true);
//		try {
//			test.clearDB(c);
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}
//
//		assertNotNull(test);
//	}

	@Test
	public void testProtocolQuery() throws SQLException {
		DBO test = new DBO();
		Connection c = test.connectDB(true);
		int t = test.protocolCount("https", c);
		assertNotEquals(-1, t);
		c.close();
	}
	
	//@Ignore("Depreciated")
//	@Test
//	public void testMakeTables() {
//		DBO test = new DBO();
//		Connection c = test.connectDB(true);
//
//		try {
//			test.makeTables(c);
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}
//	}
	
	@Test
	public void testUniqueDomain() throws SQLException{
		DBO test = new DBO();
		Connection c = test.connectDB(true);
		
		int count = -1;
		
		count = test.uniqueDomainCount(c);
		assertNotEquals(count, -1);
		c.close();
	}
	/*
	@Test
	public void testExportCSV() {
		DBO test = new DBO();
		Connection c = test.connectDB();
		
		test.exportDataCSV(c, "Select * from Tags", "testExportCSV");
	}
	*/
	
	@Test
	public void domainTagCountTest() throws SQLException {
		DBO test = new DBO();
		Connection c = test.connectDB(true);
		
		int count = -1;
		
		count = test.domainTagCount("www.google.com", "html", c);
		//System.out.println("\n\n\n" + count + "\n\n\n");
		assertNotEquals(count, -1);
		c.close();
	}





}


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
	
	@Test
	public void testDatabaseConnection() throws SQLException {
		DBO test = new DBO();
		Connection c = test.connectDB(true);
		test.insertTag("Google", ".com", "/path", "https", "<tag>", "innerData", c);
		assertNotNull(test);
		c.close();
	}
	
	@Test
	public void testGetTlds() throws SQLException {
		DBO test = new DBO();
		Connection c = test.connectDB(true);
		ArrayList<String> list;
		list = test.getTlds(c);
		assertNotNull(list);
		c.close();
	}
	
    @Test
    public void testGetDomains() throws SQLException {
        DBO test = new DBO();
        Connection c = test.connectDB(true);
        ArrayList<String> list;
        list = test.getDomains(c);
        assertNotNull(list);
        c.close();
    }
	
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
	public void testQueryByTld() throws SQLException {
		DBO test = new DBO();
		Connection c = test.connectDB(true);
		ArrayList<Integer> list = new ArrayList<>();
		list = test.queryByTld(c);
		assertNotNull(list);
		c.close();
	}
	
	@Test 
	public void testQueryByDomain() throws SQLException {
		DBO test = new DBO();
		Connection c = test.connectDB(true);
		ArrayList<Integer> testArray = test.queryByDomain(c);
		assertNotEquals((int)testArray.get(0), -1);
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

	@Test
	public void testProtocolQuery() throws SQLException {
		DBO test = new DBO();
		Connection c = test.connectDB(true);
		int t = test.protocolCount("https", c);
		assertNotEquals(-1, t);
		c.close();
	}
		
	@Test
	public void testUniqueDomain() throws SQLException{
		DBO test = new DBO();
		Connection c = test.connectDB(true);
		
		int count = -1;
		
		count = test.uniqueDomainCount(c);
		assertNotEquals(count, -1);
		c.close();
	}
	
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

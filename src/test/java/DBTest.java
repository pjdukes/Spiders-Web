
import java.util.*;
import static org.junit.Assert.*;
import org.junit.Ignore;
import org.junit.Test;
import java.io.*;

import java.sql.*;

public class DBTest {

	@Test
	public void testDatabaseConnection() throws SQLException {
		DBO test = new DBO();
		Connection c = test.connectDB(true);
		test.insertTag("Google", ".com", "/path", "https", "<tag>", "innerData", c);
		assertNotNull(test);

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
	public void testProtocolQuery() {
		DBO test = new DBO();
		Connection c = test.connectDB(true);
		int t = test.protocolCount("https", c);
		assertNotEquals(-1, t);
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
	public void testUniqueDomain() {
		DBO test = new DBO();
		Connection c = test.connectDB(true);
		
		int count = -1;
		
		count = test.uniqueDomainCount(c);
		assertNotEquals(count, -1);
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
	public void domainTagCountTest() {
		DBO test = new DBO();
		Connection c = test.connectDB(true);
		
		int count = -1;
		
		count = test.domainTagCount("www.google.com", "html", c);
		//System.out.println("\n\n\n" + count + "\n\n\n");
		assertNotEquals(count, -1);
	}





}

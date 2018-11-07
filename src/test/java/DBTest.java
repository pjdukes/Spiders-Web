
import java.util.*;
import static org.junit.Assert.*;
import org.junit.Ignore;
import org.junit.Test;
import java.io.*;

import java.sql.*;

public class DBTest {

	@Test
	public void testDatabaseConnection() {
		DBO test = new DBO();
		Connection c = test.connectDB();
		test.insertTag("Google", ".com", "https", "<tag>", "innerData", c);
		assertNotNull(test);

	}

	@Ignore("We don't want to clear the database every time we test")
	@Test
	public void testClearDatabase() {
		DBO test = new DBO();
		Connection c = test.connectDB();
		try {
			test.clearDB(c);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		assertNotNull(test);
	}

	@Test
	public void testProtocolQuery() {
		DBO test = new DBO();
		Connection c = test.connectDB();
		int t = test.protocolCount("https", c);
		assertNotEquals(-1, t);
	}
	
	@Ignore("Depreciated")
	@Test
	public void testMakeTables() {
		DBO test = new DBO();
		Connection c = test.connectDB();

		try {
			test.makeTables(c);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testUniqueDomain() {
		DBO test = new DBO();
		Connection c = test.connectDB();
		
		int count = -1;
		
		count = test.uniqueDomainCount(c);
		assertNotEquals(count, -1);
	}
	
	
	

}

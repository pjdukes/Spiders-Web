
import java.util.*;
import static org.junit.Assert.*;
import org.junit.Ignore;
import org.junit.Test;

import java.sql.*;

public class DBTest {

	@Test
	public void testDatabaseConnection() {
		DBO test = new DBO();
		Connection c = test.connectDB();
		assertNotNull(c);
	}

	@Test
    public void testInsertTag() {
	    DBO test = new DBO();
	    Connection c = test.connectDB();
	    test.insertTag("Google", ".com", "https", "<tag>", "innerData", c);
        System.out.println("When DBO has a function to pull data, pull and check if inserted data equals new data.");
    }
	
	
	@Ignore("We don't want to clear the database every time")@Test
	public void testClearDatabase() {
		DBO test = new DBO();
		Connection c = test.connectDB();
		test.clearDB(c);
		assertNotNull(test);
	}

}

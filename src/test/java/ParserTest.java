import org.junit.*;

import java.sql.*;

import static org.junit.Assert.*;

import java.io.*;

public class ParserTest {

    @Test
    public void testParserWorking() throws SQLException{
        Parser ps = new Parser();
        DBO db = new DBO();
        Connection c = db.connectDB(true);
        ps.getAndStoreTags(c, "http://www.google.com");
        assertNotNull(ps);
        c.close();
    }
    
    
    @AfterClass
	public static void deleteDatabase()
	{
		File f = new File("mock.db");
		System.out.println("f = " + f.getAbsoluteFile());
		
		if(f.delete()) 
        { 
            System.out.println("File deleted successfully"); 
        } 
        else
        { 
            System.out.println("Failed to delete the file"); 
        } 
	}
}

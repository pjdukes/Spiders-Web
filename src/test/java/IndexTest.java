
import java.util.*;
import static org.junit.Assert.*;
import org.junit.Ignore;
import org.junit.Test;
import java.io.*;
import java.sql.Connection;

public class IndexTest {

	@Test
	public void testIndexClosure() {
        System.setIn(new ByteArrayInputStream("3 excel.csv 4".getBytes())); //DON'T TOUCH THIS
	    assertTrue(Index.indexMain("https://www.google.com/",1,1,false));
	}

	@Test
	public void testIndexCharting() {
        DBO db = new DBO();
		Connection c = db.connectDB(true);
		assertTrue(Index.indexRecord(true, db, c));
	}

}

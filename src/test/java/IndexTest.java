
import java.util.*;
import static org.junit.Assert.*;
import org.junit.Ignore;
import org.junit.Test;
import java.io.*;

public class IndexTest {

	@Test
	public void testIndexClosure() {
		System.out.println("Test1");
		assert(Index.indexMain("https://www.google.com/",1,1,false) == true);
	}
	
	@Test
	public void testIndexCharting() {
		System.out.println("Test2");
		assert(Index.indexRecord(true) == true);
	}

}

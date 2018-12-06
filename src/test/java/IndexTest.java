
import java.util.*;
import static org.junit.Assert.*;
import org.junit.Ignore;
import org.junit.Test;
import java.io.*;

public class IndexTest {

	@Test
	public void testIndexClosure() {
		assertTrue(Index.indexMain("https://www.google.com/",1,1,false));
	}

	@Test
	public void testIndexCharting() {
		assertTrue(Index.indexRecord(true));
	}

}

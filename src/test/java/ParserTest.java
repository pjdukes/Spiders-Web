import org.junit.Test;

import java.sql.Connection;

import static org.junit.Assert.*;

public class ParserTest {

    @Test
    public void testLinkedListSize() {
        Parser ps = new Parser();
        DBO db = new DBO();
        Connection c = db.connectDB(true);
        ps.getAndStoreTags(c, "http://www.google.com");
        assertNotNull(ps);
    }
}

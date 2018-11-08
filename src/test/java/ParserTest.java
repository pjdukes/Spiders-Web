import java.util.*;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Ignore;


public class ParserTest {

    @Test
    public void testLinkedListSize() {
        Parser ps = new Parser();
        ps.getAndStoreTags("http://www.google.com");
        assertNotNull(ps);
    }
}

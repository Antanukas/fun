import lt.home.ColumnDefinition;
import lt.home.GembaseTableHeaderParser;
import org.junit.Test;

import java.util.Map;

/**
 * @author Antanas Bastys
 */
public class GembaseTableHeaderParserTest {

    private String test = "DEFINE FIELD SUBR_ID &\n" +
            "        /DESCRIPTION = \"SUBR_ID\" &\n" +
            "        /PROMPT = \"SUBR_ID\" &\n" +
            "        /HEADING = \"SUBR_ID\" &\n" +
            "        /DATATYPE = TEXT &\n" +
            "        /LENGTH = 10 &\n" +
            "        /POSITION = 1";

    @Test
    public void test() {
        java.util.List<ColumnDefinition> parsed = new GembaseTableHeaderParser().parseJ(test);
        for (ColumnDefinition definition : parsed) {
            System.out.println(definition.getName());
            Map<String, String> properties = definition.getProperties();
            System.out.println(properties);
        }
    }
}

package jscl.text;

import org.junit.Assert;

/**
 * User: serso
 * Date: 10/27/11
 * Time: 3:45 PM
 */
public class PowerParserTest {

	@org.junit.Test
	public void testParse() throws Exception {
	   PowerParser.parser.parse("  ^", new MutableInt(0), 0);
	   PowerParser.parser.parse(" **", new MutableInt(0), 0);
	   PowerParser.parser.parse(" **7", new MutableInt(0), 0);
	   PowerParser.parser.parse("^", new MutableInt(0), 0);
	   PowerParser.parser.parse("**", new MutableInt(0), 0);
		try {
			PowerParser.parser.parse("*", new MutableInt(0), 0);
			Assert.fail();
		} catch (ParseException e) {

		}
	}
}

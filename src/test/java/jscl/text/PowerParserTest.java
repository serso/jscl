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
	   PowerParser.parser.parse("  ^", new MutableInt(0), null);
	   PowerParser.parser.parse(" **", new MutableInt(0), null);
	   PowerParser.parser.parse(" **7", new MutableInt(0), null);
	   PowerParser.parser.parse("^", new MutableInt(0), null);
	   PowerParser.parser.parse("**", new MutableInt(0), null);
		try {
			PowerParser.parser.parse("*", new MutableInt(0), null);
			Assert.fail();
		} catch (ParseException e) {

		}
	}
}

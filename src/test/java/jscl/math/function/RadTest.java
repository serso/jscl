package jscl.math.function;

import jscl.JsclMathEngine;
import junit.framework.Assert;
import org.junit.Test;

/**
 * User: serso
 * Date: 11/12/11
 * Time: 4:00 PM
 */
public class RadTest {
	@Test
	public void testRad() throws Exception {
		final JsclMathEngine mathEngine = new JsclMathEngine();

		Assert.assertEquals("0.03490658503988659", mathEngine.evaluate("rad(2)"));
		Assert.assertEquals("0.03490658503988659", mathEngine.evaluate("rad(1+1)"));
		Assert.assertEquals("-0.03490658503988659", mathEngine.evaluate("rad(-2)"));
		Assert.assertEquals("-0.03490658503988659", mathEngine.evaluate("rad(-1-1)"));
		Assert.assertEquals(String.valueOf(Math.PI), mathEngine.evaluate("rad(180)"));
		Assert.assertEquals(String.valueOf(-Math.PI), mathEngine.evaluate("rad(-180)"));

		Assert.assertEquals("rad(-180)", mathEngine.simplify("rad(-180)"));
		Assert.assertEquals("rad(2)", mathEngine.simplify("rad(1+1)"));

		Assert.assertEquals("rad(-180)", mathEngine.elementary("rad(-180)"));
		Assert.assertEquals("rad(2)", mathEngine.elementary("rad(1+1)"));
	}
}

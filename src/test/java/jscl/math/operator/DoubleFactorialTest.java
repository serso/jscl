package jscl.math.operator;

import jscl.JsclMathEngine;
import jscl.MathEngine;
import jscl.text.ParseException;
import jscl.text.msg.Messages;
import junit.framework.Assert;
import org.junit.Test;

/**
 * User: serso
 * Date: 12/15/11
 * Time: 10:41 PM
 */
public class DoubleFactorialTest {

	@Test
	public void testDoubleFactorial() throws Exception {
		final MathEngine me = JsclMathEngine.instance;

		Assert.assertEquals("1.0", me.evaluate("0!"));
		Assert.assertEquals("1.0", me.evaluate("1!"));
		Assert.assertEquals("2.0", me.evaluate("2!"));
		Assert.assertEquals("6.0", me.evaluate("3!"));
		Assert.assertEquals("24.0", me.evaluate("4!"));

		try {
			me.evaluate("(-1)!!");
			Assert.fail();
		} catch (ArithmeticException e) {
			// ok
		}

		Assert.assertEquals("-1.0", me.evaluate("-1!!"));
		Assert.assertEquals("1.0", me.evaluate("0!!"));
		Assert.assertEquals("1.0", me.evaluate("1!!"));
		Assert.assertEquals("2.0", me.evaluate("2!!"));
		Assert.assertEquals("2.0", me.evaluate("(2!!)!"));
		Assert.assertEquals("2.0", me.evaluate("(2!)!!"));
		Assert.assertEquals("3.0", me.evaluate("3!!"));
		Assert.assertEquals("48.0", me.evaluate("(3!)!!"));
		Assert.assertEquals("6.0", me.evaluate("(3!!)!"));
		Assert.assertEquals("8.0", me.evaluate("4!!"));
		Assert.assertEquals("15.0", me.evaluate("5!!"));
		Assert.assertEquals("48.0", me.evaluate("6!!"));
		Assert.assertEquals("105.0", me.evaluate("7!!"));
		Assert.assertEquals("384.0", me.evaluate("8!!"));
		Assert.assertEquals("945.0", me.evaluate("9!!"));

		try {
			me.evaluate("9!!!");
			Assert.fail();
		} catch (ParseException e) {
			if (Messages.msg_18.equals(e.getMessageCode())) {
				// ok
			} else {
				Assert.fail();
			}
		}


	}
}

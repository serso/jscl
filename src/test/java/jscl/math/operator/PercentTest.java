package jscl.math.operator;

import jscl.JsclMathEngine;
import jscl.text.ParseException;
import junit.framework.Assert;
import org.junit.Test;

/**
 * User: serso
 * Date: 11/14/11
 * Time: 2:10 PM
 */
public class PercentTest {

	@Test
	public void testNumeric() throws Exception {
		final JsclMathEngine mathEngine = new JsclMathEngine();

		Assert.assertEquals("150.0", mathEngine.evaluate("100+50%"));
		Assert.assertEquals("0.0", mathEngine.evaluate("100-100%"));
		//Assert.assertEquals("50.0", mathEngine.evaluate("100*50%"));
		//Assert.assertEquals("150.0", mathEngine.evaluate("100+100*50%"));
		//Assert.assertEquals("150.0", mathEngine.evaluate("100*50%+100"));
		Assert.assertEquals("75.0", mathEngine.evaluate("100+50%-50%"));
		Assert.assertEquals("75.0", mathEngine.evaluate("100+50%+(-50%)"));
		Assert.assertEquals("0.0", mathEngine.evaluate("0+(-50%)"));
		Assert.assertEquals("0.0", mathEngine.evaluate("0+(50%)"));
		Assert.assertEquals("0.0", mathEngine.evaluate("0+50%"));
		Assert.assertEquals("-150.0", mathEngine.evaluate("-100+50%"));
		Assert.assertEquals("-148.5", mathEngine.evaluate("1-100+50%"));
		Assert.assertEquals("-49.5", mathEngine.evaluate("1-100-50%"));
		Assert.assertEquals("-49.5", mathEngine.evaluate("(1-100)-50%"));
		Assert.assertEquals("-49.0", mathEngine.evaluate("1-(100-50%)"));
		Assert.assertEquals("50.0", mathEngine.evaluate("100-50%"));
		Assert.assertEquals("2600.0", mathEngine.evaluate("100+50%^2"));
		Assert.assertEquals("2600.0", mathEngine.evaluate("100+50^2%"));
		Assert.assertEquals("22500.0", mathEngine.evaluate("(100+50%)^2"));
		Assert.assertEquals("225.0", mathEngine.evaluate("(100+50%)+50%"));
		Assert.assertEquals("225.0", mathEngine.evaluate("(100+50%)+(abs(-50)+10-10)%"));
		//Assert.assertEquals("0.0", mathEngine.evaluate("100-2*50%"));
		//Assert.assertEquals("3.0", mathEngine.evaluate("100-2*50%+3"));
		Assert.assertEquals("120.0", mathEngine.evaluate("100-20+50%"));


		Assert.assertEquals("100+50%-50%", mathEngine.simplify("100+50%-50%"));


		try {
			mathEngine.evaluate("+50%");
			Assert.fail();
		} catch (ParseException e) {
		}

		try {
			Assert.assertEquals("0.5", mathEngine.evaluate("50%"));
			Assert.fail();
		} catch (ArithmeticException e) {
		}

		try {
			Assert.assertEquals("-0.5", mathEngine.evaluate("-50%"));
			Assert.fail();
		} catch (ArithmeticException e) {
		}

		Assert.assertEquals("225.0", mathEngine.evaluate("(100+50%)+50%"));

	}
}

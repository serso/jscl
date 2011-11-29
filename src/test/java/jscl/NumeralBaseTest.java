package jscl;

import jscl.math.function.Constant;
import jscl.math.function.ExtendedConstant;
import jscl.math.function.IConstant;
import jscl.text.ParseException;
import junit.framework.Assert;
import org.junit.Test;

/**
 * User: serso
 * Date: 11/29/11
 * Time: 12:20 PM
 */
public class NumeralBaseTest {

	@Test
	public void testEvaluation() throws Exception {
		MathEngine me = JsclMathEngine.instance;

		Assert.assertEquals("3.0", me.evaluate("0b1+0b10"));
		Assert.assertEquals("5.0", me.evaluate("0b1+0b100"));
		Assert.assertEquals("8.0", me.evaluate("0b1+0b100+(0b1+0b10)"));
		Assert.assertEquals("18.0", me.evaluate("0b1+0b100+(0b1+0b10)+10"));
		Assert.assertEquals("18.5", me.evaluate("0b1+0b100+(0b1+0b10)+10.5"));
		try {
			me.evaluate("0b1+0b100.+(0b1+0b10)+10.5");
			Assert.fail();
		} catch (ParseException e) {
		}

		try {
			me.evaluate("0b1+0b100E-2+(0b1+0b10)+10.5");
			Assert.fail();
		} catch (ParseException e) {
		}

		Assert.assertEquals("2748.0", me.evaluate("0xabc"));

		IConstant constant = null;
		try {
			constant = me.getConstantsRegistry().add(new ExtendedConstant.Builder(new Constant("a"), 2d));
			Assert.assertEquals("2748.0", me.evaluate("0xabc"));
			Assert.assertEquals("5496.0", me.evaluate("0xabc*a"));
			Assert.assertEquals("27480.0", me.evaluate("0xabc*0xa"));
		} finally {
			if (constant != null) {
				me.getConstantsRegistry().remove(constant);
			}
		}

	}
}

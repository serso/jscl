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

		Assert.assertEquals("3.0", me.evaluate("0b:1+0b:10"));
		Assert.assertEquals("5.0", me.evaluate("0b:1+0b:100"));
		Assert.assertEquals("8.0", me.evaluate("0b:1+0b:100+(0b:1+0b:10)"));
		Assert.assertEquals("18.0", me.evaluate("0b:1+0b:100+(0b:1+0b:10)+10"));
		Assert.assertEquals("18.5", me.evaluate("0b:1+0b:100+(0b:1+0b:10)+10.5"));
		try {
			me.evaluate("0b:1+0b:100.+(0b:1+0b:10)+10.5");
			Assert.fail();
		} catch (ParseException e) {
		}

		try {
			me.evaluate("0b:1+0b:100E-2+(0b:1+0b:10)+10.5");
			Assert.fail();
		} catch (ParseException e) {
		}

		Assert.assertEquals("2748.0", me.evaluate("0x:abc"));

		try {
			me.evaluate("0x:");
			Assert.fail();
		} catch (ParseException e) {
		}

		Assert.assertEquals("0.0", me.evaluate("0x:0"));

		IConstant constant = null;
		try {
			constant = me.getConstantsRegistry().add(new ExtendedConstant.Builder(new Constant("a"), 2d));
			Assert.assertEquals("2748.0", me.evaluate("0x:abc"));
			Assert.assertEquals("5496.0", me.evaluate("0x:abc*a"));
			Assert.assertEquals("27480.0", me.evaluate("0x:abc*0x:a"));
		} finally {
			if (constant != null) {
				me.getConstantsRegistry().remove(constant);
			}
		}
	}

	@Test
	public void testNumeralBases() throws Exception {
		MathEngine me = JsclMathEngine.instance;

		final NumeralBase defaultNumeralBase = me.getNumeralBase();
		try{
			me.setNumeralBase(NumeralBase.bin);
			Assert.assertEquals("11", me.evaluate("0b:1+0b:10"));
			Assert.assertEquals("11", me.evaluate("1+10"));
			Assert.assertEquals("-1", me.evaluate("1-10"));
			Assert.assertEquals("11-i", me.evaluate("1+i+10-10*i"));
			Assert.assertEquals("11111110", me.evaluate("111001+11000101"));
			Assert.assertEquals("1101100100101111", me.evaluate("11011001001011110/10"));
			Assert.assertEquals("1001000011001010", me.evaluate("11011001001011110/11"));
			try {
				Assert.assertEquals("0.101010101", me.evaluate("10/11"));
				Assert.fail();
			} catch (NumeralBaseException e) {
				// ok
			}

			me.setNumeralBase(NumeralBase.hex);
			org.junit.Assert.assertEquals("637b", me.evaluate("56ce+cad"));
			org.junit.Assert.assertEquals("637b", me.simplify("56ce+cad"));

		} finally {
			me.setNumeralBase(defaultNumeralBase);
		}
	}
}

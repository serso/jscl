package jscl.math.function.trigonometric;

import jscl.AngleUnit;
import jscl.JsclMathEngine;
import org.junit.Assert;
import org.junit.Test;

/**
 * User: serso
 * Date: 1/7/12
 * Time: 4:03 PM
 */
public class TanTest {

	@Test
	public void testIntegrate() throws Exception {
		final JsclMathEngine me = JsclMathEngine.instance;

		// todo serso: uncomment after variable modification issue fixed
/*		Assert.assertEquals("-2*ln(2)-ln(cos(x))", me.simplify("∫(tan(x), x)"));
		Assert.assertEquals("-(2*ln(2)+ln(cos(x*π)))/π", me.simplify("∫(tan(π*x), x)"));

		Assert.assertEquals("-0.015308831465985804", me.evaluate("ln(cos(10))"));
		Assert.assertEquals("-0.1438410362258904", me.evaluate("ln(cos(30))"));
		Assert.assertEquals("0.12853220475990468", me.evaluate("∫ab(tan(x), x, 10, 30)"));*/

		try {
			me.setAngleUnits(AngleUnit.rad);
			Assert.assertEquals("-2*ln(2)-ln(cos(x))", me.simplify("∫(tan(x), x)"));
			Assert.assertEquals("-(2*ln(2)+ln(cos(x*π)))/π", me.simplify("∫(tan(π*x), x)"));
			Assert.assertEquals("-0.015308831465985804", me.evaluate("ln(cos(10*π/180))"));
			Assert.assertEquals("-0.1438410362258904", me.evaluate("ln(cos(30*π/180))"));
			Assert.assertEquals("0.12853220475990468", me.evaluate("∫ab(tan(x), x, 10*π/180, 30*π/180)"));
		} finally {
			me.setAngleUnits(AngleUnit.deg);
		}
	}
}

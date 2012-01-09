package jscl.math.function;

import jscl.JsclMathEngine;
import jscl.MathEngine;
import org.junit.Assert;
import org.junit.Test;

/**
 * User: serso
 * Date: 1/9/12
 * Time: 6:49 PM
 */
public class LnTest {
	@Test
	public void testAntiDerivative() throws Exception {
		final MathEngine me = JsclMathEngine.instance;

		Assert.assertEquals("-x+x*ln(x)", me.simplify("∫(ln(x), x)"));
		Assert.assertEquals("-x+x*lg(x)", me.simplify("∫(lg(x), x)"));
	}
}

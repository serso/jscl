package jscl.math.function;

import jscl.math.Generic;
import junit.framework.Assert;
import org.junit.Test;

/**
 * User: serso
 * Date: 10/29/11
 * Time: 5:20 PM
 */
public class FunctionTest {

	@Test
	public void testSubstituteParameter() throws Exception {
		Ln ln = new Ln(null);
		Assert.assertEquals("ln(a)", ln.toString());
		Comparison eq = new Comparison("eq", null, null);
		Assert.assertEquals("eq(a, b)", eq.toString());
		Generic[] parameter = new Generic[40];
		for(int i = 0; i < 40; i++) {
			parameter[i] = null;
		}
		eq.setParameter(parameter);
		Assert.assertEquals("eq(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, w, x, y, z, a, b, c, d, e, f, g, h, i, j, k, l, m, n)", eq.toString());
	}
}

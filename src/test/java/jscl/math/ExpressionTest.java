package jscl.math;

import junit.framework.Assert;
import org.junit.Test;

/**
 * User: serso
 * Date: 10/27/11
 * Time: 3:54 PM
 */
public class ExpressionTest {

	@Test
	public void testExpressions() throws Exception {
		Assert.assertEquals("3.0", Expression.valueOf("3").numeric().toString());
		Assert.assertEquals("0.6931471805599453", Expression.valueOf("ln(2)").numeric().toString());
		Assert.assertEquals("1.0", Expression.valueOf("lg(10)").numeric().toString());
		Assert.assertEquals("0.0", Expression.valueOf("eq(0, 1)").numeric().toString());
		Assert.assertEquals("1.0", Expression.valueOf("eq(1, 1)").numeric().toString());

		Assert.assertEquals("24", Expression.valueOf("4!").numeric().toString());
		Assert.assertEquals("24", Expression.valueOf("(2+2)!").numeric().toString());
		Assert.assertEquals("120", Expression.valueOf("(2+2+1)!").numeric().toString());
		Assert.assertEquals("(2*2.0)!", Expression.valueOf("(2.0+2.0)!").numeric().toString());
		Assert.assertEquals("4.0!", Expression.valueOf("4.0!").numeric().toString());
		Assert.assertEquals("(pi/pi)!", Expression.valueOf("(pi/pi)!").numeric().toString());
	}
}

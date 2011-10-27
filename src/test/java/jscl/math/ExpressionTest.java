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
	public void testSpecialCase() throws Exception {
		Assert.assertEquals("3.0", Expression.valueOf("3").numeric().toString());
		Assert.assertEquals("0.6931471805599453", Expression.valueOf("ln(2)").numeric().toString());
		Assert.assertEquals("1.0", Expression.valueOf("log(10)").numeric().toString());
	}
}

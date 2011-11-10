package jscl.math;

import jscl.JsclMathEngine;
import jscl.math.function.Constant;
import jscl.math.function.ExtendedConstant;
import junit.framework.Assert;
import org.junit.Test;

import static junit.framework.Assert.fail;

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

		Assert.assertEquals("24.0", Expression.valueOf("4!").numeric().toString());
		try {
			Expression.valueOf("(-3+2)!").numeric().toString();
			fail();
		} catch (ArithmeticException e) {

		}
		Assert.assertEquals("24.0", Expression.valueOf("(2+2)!").numeric().toString());
		Assert.assertEquals("120.0", Expression.valueOf("(2+2+1)!").numeric().toString());
		Assert.assertEquals("24.0", Expression.valueOf("(2.0+2.0)!").numeric().toString());
		Assert.assertEquals("24.0", Expression.valueOf("4.0!").numeric().toString());
		Assert.assertEquals("-0.9055783620066238", Expression.valueOf("sin(4!)").numeric().toString());
		Assert.assertEquals("1.0", Expression.valueOf("(3.14/3.14)!").numeric().toString());
		Assert.assertEquals("1.0", Expression.valueOf("2/2!").numeric().toString());
		try {
			Assert.assertEquals("3.141592653589793!", Expression.valueOf("3.141592653589793!").numeric().toString());
			fail();
		} catch (NotIntegerException e) {

		}
		Assert.assertEquals("0.5235987755982988", Expression.valueOf("3.141592653589793/3!").numeric().toString());
		try {
			Assert.assertEquals("3.141592653589793/3.141592653589793!", Expression.valueOf("3.141592653589793/3.141592653589793!").numeric().toString());
			fail();
		} catch (ArithmeticException e) {

		}
		try {
			Assert.assertEquals("7.2!", Expression.valueOf("7.2!").numeric().toString());
			fail();
		} catch (NotIntegerException e) {}

		try {
			Assert.assertEquals("ln(7.2!)", Expression.valueOf("ln(7.2!)").numeric().toString());
			fail();
		} catch (NotIntegerException e) {}

		Assert.assertEquals("ln(7.2!)", Expression.valueOf("ln(7.2!)").simplify().toString());


		Assert.assertEquals("36.0", Expression.valueOf("3!^2").numeric().toString());
		Assert.assertEquals("1.0", Expression.valueOf("(pi/pi)!").numeric().toString());
		Assert.assertEquals("720.0", Expression.valueOf("3!!").numeric().toString());
		Assert.assertEquals("36.0", Expression.valueOf("3!*3!").numeric().toString());

		Assert.assertEquals("0.05235987755982988", Expression.valueOf("3°").numeric().toString());
		Assert.assertEquals("0.2617993877991494", Expression.valueOf("3°*5").numeric().toString());
		Assert.assertEquals("0.002741556778080377", Expression.valueOf("3°^2").numeric().toString());
		Assert.assertEquals("0.010966227112321508", Expression.valueOf("3!°^2").numeric().toString());
		Assert.assertEquals("9.138522593601257E-4", Expression.valueOf("3°°").numeric().toString());
		Assert.assertEquals("0.08726646259971647", Expression.valueOf("5°").numeric().toString());
		Assert.assertEquals("2.0523598775598297", Expression.valueOf("2+3°").numeric().toString());

		Assert.assertEquals("6", Expression.valueOf("2*d(3*x,x)").expand().toString());
		Assert.assertEquals("3", Expression.valueOf("d(3*x,x)").expand().toString());
		Assert.assertEquals("12", Expression.valueOf("d(x^3,x,2)").expand().toString());
		Assert.assertEquals("3*a", Expression.valueOf("d(3*x*a,x)").expand().toString());
		Assert.assertEquals("0", Expression.valueOf("d(3*x*a,x,0.011,2)").expand().toString());
		Assert.assertEquals("0", Expression.valueOf("2*d(3*x*a,x,0.011,2)").expand().toString());
		Assert.assertEquals("ln(8)+lg(8)*ln(8)", Expression.valueOf("ln(8)*lg(8)+ln(8)").expand().toString());
		Assert.assertEquals("3.9573643765059856", Expression.valueOf("ln(8)*lg(8)+ln(8)").numeric().toString());

		Assert.assertEquals("4.0!", Expression.valueOf("4.0!").simplify().toString());
		Assert.assertEquals("4.0°", Expression.valueOf("4.0°").simplify().toString());
		Assert.assertEquals("30°", Expression.valueOf("30°").simplify().toString());


		Assert.assertEquals("1.0", Expression.valueOf("abs(1)").numeric().toString());
		Assert.assertEquals("0.0", Expression.valueOf("abs(0)").numeric().toString());
		Assert.assertEquals("0.0", Expression.valueOf("abs(-0)").numeric().toString());
		Assert.assertEquals("1.0", Expression.valueOf("abs(-1)").numeric().toString());
		Assert.assertEquals("Infinity", Expression.valueOf("abs(-infin)").numeric().toString());

		Assert.assertEquals("1.0", Expression.valueOf("abs(√(-1))").numeric().toString());
		Assert.assertEquals("0.0", Expression.valueOf("abs(0+0*√(-1))").numeric().toString());
		Assert.assertEquals("1.0", Expression.valueOf("abs(-√(-1))").numeric().toString());
		Assert.assertEquals("2.23606797749979", Expression.valueOf("abs(2-√(-1))").numeric().toString());
		Assert.assertEquals("2.23606797749979", Expression.valueOf("abs(2+√(-1))").numeric().toString());
		Assert.assertEquals("2.8284271247461903", Expression.valueOf("abs(2+2*√(-1))").numeric().toString());
		Assert.assertEquals("2.8284271247461903", Expression.valueOf("abs(2-2*√(-1))").numeric().toString());

		new JsclMathEngine().getConstantsRegistry().add(null, new ExtendedConstant.Builder(new Constant("k"), 2.8284271247461903));
		Assert.assertEquals("2.8284271247461903", Expression.valueOf("k").numeric().toString());
		Assert.assertEquals("k", Expression.valueOf("k").simplify().toString());
		Assert.assertEquals("k", Expression.valueOf("k").simplify().toString());
		Assert.assertEquals("k^3", Expression.valueOf("k*k*k").simplify().toString());
		Assert.assertEquals("22.627416997969526", Expression.valueOf("k*k*k").numeric().toString());

		new JsclMathEngine().getConstantsRegistry().add(null, new ExtendedConstant.Builder(new Constant("k_1"), 3d));
		Assert.assertEquals("3.0", Expression.valueOf("k_1").numeric().toString());
		Assert.assertEquals("3.0", Expression.valueOf("k_1[0]").numeric().toString());
		Assert.assertEquals("3.0", Expression.valueOf("k_1[2]").numeric().toString());

		Assert.assertEquals("t", Expression.valueOf("t").simplify().toString());
		Assert.assertEquals("t^3", Expression.valueOf("t*t*t").simplify().toString());

		try {
			Expression.valueOf("t").numeric();
			fail();
		} catch (ArithmeticException e) {
		}

		new JsclMathEngine().getConstantsRegistry().add(null, new ExtendedConstant.Builder(new Constant("t"), (String)null));
		try {
			Expression.valueOf("t").numeric();
			fail();
		} catch (ArithmeticException e) {
		}
		Assert.assertEquals("t", Expression.valueOf("t").simplify().toString());
		Assert.assertEquals("t^3", Expression.valueOf("t*t*t").simplify().toString());
	}
}

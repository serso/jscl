package jscl.math;

import jscl.JsclMathEngine;
import jscl.math.function.Constant;
import jscl.math.function.ExtendedConstant;
import jscl.text.ParseException;
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
		Assert.assertEquals("48.0", Expression.valueOf("2*4.0!").numeric().toString());
		Assert.assertEquals("40320.0", Expression.valueOf("(2*4.0)!").numeric().toString());
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
		} catch (NotIntegerException e) {
		}

		try {
			Assert.assertEquals("ln(7.2!)", Expression.valueOf("ln(7.2!)").numeric().toString());
			fail();
		} catch (NotIntegerException e) {
		}

		Assert.assertEquals("ln(7.2!)", Expression.valueOf("ln(7.2!)").simplify().toString());


		Assert.assertEquals("36.0", Expression.valueOf("3!^2").numeric().toString());
		Assert.assertEquals("1.0", Expression.valueOf("(π/π)!").numeric().toString());
		Assert.assertEquals("720.0", Expression.valueOf("3!!").numeric().toString());
		Assert.assertEquals("36.0", Expression.valueOf("3!*3!").numeric().toString());

		Assert.assertEquals("100.0", Expression.valueOf("0.1E3").numeric().toString());

		Assert.assertEquals("0.017453292519943295", Expression.valueOf("1°").numeric().toString());
		Assert.assertEquals("0.03490658503988659", Expression.valueOf("2°").numeric().toString());
		Assert.assertEquals("0.05235987755982988", Expression.valueOf("3°").numeric().toString());
		Assert.assertEquals("0.2617993877991494", Expression.valueOf("3°*5").numeric().toString());
		Assert.assertEquals("0.002741556778080377", Expression.valueOf("3°^2").numeric().toString());
		Assert.assertEquals("0.010966227112321508", Expression.valueOf("3!°^2").numeric().toString());
		Assert.assertEquals("9.138522593601257E-4", Expression.valueOf("3°°").numeric().toString());
		Assert.assertEquals("0.08726646259971647", Expression.valueOf("5°").numeric().toString());
		Assert.assertEquals("2.0523598775598297", Expression.valueOf("2+3°").numeric().toString());

		Assert.assertEquals("6", Expression.valueOf("2*∂(3*x,x)").expand().toString());
		Assert.assertEquals("3", Expression.valueOf("∂(3*x,x)").expand().toString());
		Assert.assertEquals("12", Expression.valueOf("∂(x^3,x,2)").expand().toString());
		Assert.assertEquals("3*a", Expression.valueOf("∂(3*x*a,x)").expand().toString());
		Assert.assertEquals("0", Expression.valueOf("∂(3*x*a,x,0.011,2)").expand().toString());
		Assert.assertEquals("0", Expression.valueOf("2*∂(3*x*a,x,0.011,2)").expand().toString());
		Assert.assertEquals("ln(8)+lg(8)*ln(8)", Expression.valueOf("ln(8)*lg(8)+ln(8)").expand().toString());
		Assert.assertEquals("3.9573643765059856", Expression.valueOf("ln(8)*lg(8)+ln(8)").numeric().toString());

		Assert.assertEquals("4.0!", Expression.valueOf("4.0!").simplify().toString());
		Assert.assertEquals("4.0°", Expression.valueOf("4.0°").simplify().toString());
		Assert.assertEquals("30°", Expression.valueOf("30°").simplify().toString());


		Assert.assertEquals("1.0", Expression.valueOf("abs(1)").numeric().toString());
		Assert.assertEquals("0.0", Expression.valueOf("abs(0)").numeric().toString());
		Assert.assertEquals("0.0", Expression.valueOf("abs(-0)").numeric().toString());
		Assert.assertEquals("1.0", Expression.valueOf("abs(-1)").numeric().toString());
		Assert.assertEquals("Infinity", Expression.valueOf("abs(-∞)").numeric().toString());

		Assert.assertEquals("1.0", Expression.valueOf("abs(√(-1))").numeric().toString());
		Assert.assertEquals("0.0", Expression.valueOf("abs(0+0*√(-1))").numeric().toString());
		Assert.assertEquals("1.0", Expression.valueOf("abs(-√(-1))").numeric().toString());
		Assert.assertEquals("2.23606797749979", Expression.valueOf("abs(2-√(-1))").numeric().toString());
		Assert.assertEquals("2.23606797749979", Expression.valueOf("abs(2+√(-1))").numeric().toString());
		Assert.assertEquals("2.8284271247461903", Expression.valueOf("abs(2+2*√(-1))").numeric().toString());
		Assert.assertEquals("2.8284271247461903", Expression.valueOf("abs(2-2*√(-1))").numeric().toString());

		new JsclMathEngine().getConstantsRegistry().add(new ExtendedConstant.Builder(new Constant("k"), 2.8284271247461903));
		Assert.assertEquals("2.8284271247461903", Expression.valueOf("k").numeric().toString());
		Assert.assertEquals("k", Expression.valueOf("k").simplify().toString());
		Assert.assertEquals("k", Expression.valueOf("k").simplify().toString());
		Assert.assertEquals("k^3", Expression.valueOf("k*k*k").simplify().toString());
		Assert.assertEquals("22.627416997969526", Expression.valueOf("k*k*k").numeric().toString());

		new JsclMathEngine().getConstantsRegistry().add(new ExtendedConstant.Builder(new Constant("k_1"), 3d));
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

		new JsclMathEngine().getConstantsRegistry().add(new ExtendedConstant.Builder(new Constant("t"), (String) null));
		try {
			Expression.valueOf("t").numeric();
			fail();
		} catch (ArithmeticException e) {
		}
		Assert.assertEquals("t", Expression.valueOf("t").simplify().toString());
		Assert.assertEquals("t^3", Expression.valueOf("t*t*t").simplify().toString());

		Assert.assertEquals("-2/57", Expression.valueOf("1/(-57/2)").simplify().toString());
		Assert.assertEquals("sin(30)", Expression.valueOf("sin(30)").expand().toString());
		Assert.assertEquals("sin(n)", Expression.valueOf("sin(n)").expand().toString());
		Assert.assertEquals("sin(n!)", Expression.valueOf("sin(n!)").expand().toString());
		Assert.assertEquals("sin(n°)", Expression.valueOf("sin(n°)").expand().toString());
		Assert.assertEquals("sin(30°)", Expression.valueOf("sin(30°)").expand().toString());
		Assert.assertEquals("0.49999999999999994", Expression.valueOf("sin(30°)").expand().numeric().toString());
		Assert.assertEquals("sin(2!)", Expression.valueOf("sin(2!)").expand().toString());

		Assert.assertEquals("12", Expression.valueOf("3*(3+1)").expand().toString());
		Assert.assertEquals("114.59155902616465", Expression.valueOf("deg(2)").numeric().toString());
		try {
			Assert.assertEquals("-0.1425465430742778", Expression.valueOf("∏(tan(3))").numeric().toString());
			fail();
		} catch (ParseException e) {
		}
		try {
			Assert.assertEquals("-0.14255", Expression.valueOf("sin(2,2)").expand().numeric().toString());
			fail();
		} catch (ParseException e) {
		}
		try {
			Assert.assertEquals("114.59155902616465", Expression.valueOf("deg(2,2)").numeric().toString());
			fail();
		} catch (ParseException e) {
		}

		org.junit.Assert.assertEquals("0.49999999999999994", Expression.valueOf("sin(30°)").numeric().toString());
	}

	@Test
	public void testName() throws Exception {
		Expression.valueOf("a*c+b*sin(c)").toString();
	}

	@Test
	public void testIntegrals() throws Exception {
		Assert.assertEquals("50.0", Expression.valueOf("∫ab(x, x, 0, 10)").expand().numeric().toString());
		Assert.assertEquals("1/2*a^2", Expression.valueOf("∫ab(x, x, 0, a)").expand().toString());
		try {
			Assert.assertEquals("∫ab(x, x, 0)", Expression.valueOf("∫ab(x, x, 0)").expand().toString());
			fail();
		} catch (ParseException e) {
		}
		try {
			Assert.assertEquals("∫ab(x, x)", Expression.valueOf("∫ab(x, x)").expand().simplify().toString());
			fail();
		} catch (ParseException e) {
		}
		Assert.assertEquals("x^2/2", Expression.valueOf("∫(x, x)").expand().simplify().toString());
		try {
			Assert.assertEquals("x^2/2", Expression.valueOf("∫(x, x)").expand().numeric().toString());
			fail();
		} catch (ArithmeticException e) {
		}

		Assert.assertEquals("x^2/2", Expression.valueOf("∫(x, x)").expand().simplify().toString());
		Assert.assertEquals("ln(x)", Expression.valueOf("∫(1/x, x)").expand().simplify().toString());
		Assert.assertEquals("2*ln(2)+ln(cosh(x))", Expression.valueOf("∫(tanh(x), x)").expand().simplify().toString());
		Assert.assertEquals("2*ln(2)+ln(sin(x))", Expression.valueOf("∫(cot(x), x)").expand().simplify().toString());
		Assert.assertEquals("-2*ln(2)-ln(cos(x))", Expression.valueOf("∫(tan(x), x)").expand().simplify().toString());
			}

	@Test
	public void testDerivations() throws Exception {
		Assert.assertEquals("-0.9092974268256817", Expression.valueOf("∂(cos(t),t,2)").numeric().toString());
		Assert.assertEquals("∂(cos(t), t, 2, 1)", Expression.valueOf("∂(cos(t),t,2)").simplify().toString());
		Assert.assertEquals("-2.234741690198506", Expression.valueOf("∂(t*cos(t),t,2)").numeric().toString());
		Assert.assertEquals("-4.469483380397012", Expression.valueOf("2*∂(t*cos(t),t,2)").numeric().toString());
		Assert.assertEquals("-sin(2)", Expression.valueOf("∂(cos(t),t,2)").expand().toString());
		Assert.assertEquals("-sin(t)", Expression.valueOf("∂(cos(t),t)").expand().toString());
		org.junit.Assert.assertEquals("-sin(t)", Expression.valueOf("∂(cos(t),t,t,1)").expand().simplify().toString());
		org.junit.Assert.assertEquals("∂(cos(t), t, t, 1°)", Expression.valueOf("∂(cos(t),t,t,1°)").expand().simplify().toString());

		// todo serso: uncomment and check!!!
		//org.junit.Assert.assertEquals("∂(cos(t), t, t, 1°)", Expression.valueOf("∂(cos(t),t,t,1°)").expand().numeric().toString());

		//Assert.assertEquals("cos'(t)", Expression.valueOf("cos'(t)").simplify().toString());
		//Assert.assertEquals("-0.9092974268256817", Expression.valueOf("cos'(2)").numeric().toString());
		//Assert.assertEquals(Expression.valueOf("-cos(2)").numeric().toString(), Expression.valueOf("cos''(2)").numeric().toString());
	}

	@Test
	public void testSum() throws Exception {
		Assert.assertEquals("3", Expression.valueOf("Σ(n,n,1,2)").expand().toString());
		Assert.assertEquals("200", Expression.valueOf("Σ(n/n,n,1,200)").expand().toString());
		Assert.assertEquals("1/3", Expression.valueOf("Σ((n-1)/(n+1),n,1,2)").expand().toString());
		Assert.assertEquals("sin(1)", Expression.valueOf("Σ(sin(n),n,1,1)").expand().toString());
		Assert.assertEquals("1/1!", Expression.valueOf("Σ(n/n!,n,1,1)").expand().toString());
		Assert.assertEquals("2.0", Expression.valueOf("Σ(n/n!,n,1,2)").expand().numeric().toString());
		Assert.assertEquals("2.7182818284590455", Expression.valueOf("Σ(n/n!,n,1,200)").expand().numeric().toString());
		Assert.assertEquals("2.718281828459046", Expression.valueOf("Σ(n/(2*n/2)!,n,1,200)").expand().numeric().toString());
		Assert.assertEquals("0.05235987755982989", Expression.valueOf("Σ(n°,n,1,2)").expand().numeric().toString());
		Assert.assertEquals("200.0", Expression.valueOf("Σ(n°/n°,n,1,200)").expand().numeric().toString());
		Assert.assertEquals("-sin(1)-sin(2)", Expression.valueOf("Σ(∂(cos(t),t,n),n,1,2)").expand().toString());
		Assert.assertEquals("-1.7507684116335782", Expression.valueOf("Σ(∂(cos(t),t,n),n,1,2)").expand().numeric().toString());
	}
}

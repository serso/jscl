package jscl.math.function;

import jscl.JsclMathEngine;
import jscl.math.Expression;
import jscl.text.ParseException;
import org.junit.Assert;
import org.junit.Test;

/**
 * User: serso
 * Date: 11/15/11
 * Time: 5:35 PM
 */
public class CustomFunctionTest {

	@Test
	public void testFunction() throws Exception {
		JsclMathEngine mathEngine = JsclMathEngine.instance;

		mathEngine.getFunctionsRegistry().add(new CustomFunction.Builder("testFunction", new String[]{"a", "b", "c", "d"}, "b*cos(a)/c+d"));
		Assert.assertEquals("6.749543120264322", Expression.valueOf("testFunction(2, 3, 4, 6)").numeric().toString());
		Assert.assertEquals("7.749543120264322", Expression.valueOf("testFunction(2, 3, 4, 7)").numeric().toString());
		Assert.assertEquals("6.749543120264322", Expression.valueOf("testFunction(2*1, 3, 4, 6)").numeric().toString());
		Assert.assertEquals("6.749543120264322", Expression.valueOf("testFunction(2*1, 3, 4, 3!)").numeric().toString());
		Assert.assertEquals("6.749543120264322", Expression.valueOf("testFunction(2*1, 3, 2^2-1+e^0, 3!)").numeric().toString());
		Assert.assertEquals("testFunction(2, 3, 4, 3!)", Expression.valueOf("testFunction(2*1, 3, 2^2-1+e^0, 3!)").simplify().toString());
		Assert.assertEquals("3*cos(2)/4+3!", Expression.valueOf("testFunction(2*1, 3, 2^2-1+e^0, 3!)").expand().toString());
		Assert.assertEquals("3*(1/2*1/exp(2*i)+1/2*exp(2*i))/4+3!", Expression.valueOf("testFunction(2*1, 3, 2^2-1+e^0, 3!)").elementary().toString());
		Assert.assertEquals("sin(t)^2*testFunction(2, 3, 4, 3!)", Expression.valueOf("sin(t)*testFunction(2*1, 3, 2^2-1+e^0, 3!)*sin(t)").simplify().toString());
		Assert.assertEquals("testFunction(2, 3, 4, 3!)^2", Expression.valueOf("testFunction(2*1, 3, 2^2-1+e^0, 3!)*testFunction(2, 3, 4, 3!)").simplify().toString());
		try {
			Expression.valueOf("testFunction(2*1, 3, 2^2-1+e^0, 3!)*testFunction(2, 3, 4)");
			Assert.fail();
		} catch (ParseException e) {
			// ok, not enough parameters
		}

		mathEngine.getConstantsRegistry().add(new ExtendedConstant.Builder(new Constant("a"), 1000d));
		mathEngine.getFunctionsRegistry().add(new CustomFunction.Builder("testFunction2", new String[]{"a", "b", "c", "d"}, "b*cos(a)/c+d"));
		Assert.assertEquals("6.749543120264322", Expression.valueOf("testFunction2(2, 3, 4, 6)").numeric().toString());
		Assert.assertEquals("7.749543120264322", Expression.valueOf("testFunction2(2, 3, 4, 7)").numeric().toString());
		Assert.assertEquals("6.749543120264322", Expression.valueOf("testFunction2(2*1, 3, 4, 6)").numeric().toString());
		Assert.assertEquals("6.749543120264322", Expression.valueOf("testFunction2(2*1, 3, 2^2-1+e^0, 3!)").numeric().toString());

		mathEngine.getFunctionsRegistry().add(new CustomFunction.Builder("testFunction3", new String[]{"a", "b", "c", "d"}, "testFunction2(a, b, c, d) - testFunction(a, b, c, d)"));
	   	Assert.assertEquals("0.0", Expression.valueOf("testFunction3(2, 3, 4, 6)").numeric().toString());
		Assert.assertEquals("0.0", Expression.valueOf("testFunction3(2, 3, 4, 7)").numeric().toString());
		Assert.assertEquals("0.0", Expression.valueOf("testFunction3(2*1, 3, 4, 6)").numeric().toString());
		Assert.assertEquals("0.0", Expression.valueOf("testFunction3(2*1, 3, 2^2-1+e^0, 3!)").numeric().toString());

		mathEngine.getFunctionsRegistry().add(new CustomFunction.Builder("testFunction4", new String[]{"a", "b", "c", "d"}, "testFunction2(a, b/2, c/3, d/4) - testFunction(a, b!, c, d)"));
	   	Assert.assertEquals("-4.874771560132161", Expression.valueOf("testFunction4(2, 3, 4, 6)").numeric().toString());
		Assert.assertEquals("-5.624771560132161", Expression.valueOf("testFunction4(2, 3, 4, 7)").numeric().toString());
		Assert.assertEquals("-4.874771560132161", Expression.valueOf("testFunction4(2*1, 3, 4, 6)").numeric().toString());
		Assert.assertEquals("-4.874771560132161", Expression.valueOf("testFunction4(2*1, 3, 2^2-1+e^0, 3!)").numeric().toString());

		mathEngine.getFunctionsRegistry().add(new CustomFunction.Builder("testFunction5", new String[]{"a", "b"}, "testFunction2(a, b/2, 2, 1) - testFunction(a, b!, 4!, 1)"));
		Assert.assertEquals("0.4996954135095478", Expression.valueOf("testFunction5(2, 3)").numeric().toString());
		Assert.assertEquals("0.4996954135095478", Expression.valueOf("testFunction5(2, 3)").numeric().toString());
		Assert.assertEquals("0.4996954135095478", Expression.valueOf("testFunction5(2*1, 3)").numeric().toString());
		Assert.assertEquals("-1.1102230246251565E-16", Expression.valueOf("testFunction5(2*1, 2^2-1+e^0)").numeric().toString());

		try {
			Expression.valueOf("testFunction5(2, 3.5)").numeric();
			Assert.fail();
		} catch (ArithmeticException e) {

		}

		mathEngine.getFunctionsRegistry().add(new CustomFunction.Builder("testFunction6", new String[]{"a", "b"}, "testFunction(a, b!, 4!, π)"));
		Assert.assertEquals("180.24984770675476", Expression.valueOf("testFunction6(2, 3)").numeric().toString());

		mathEngine.getConstantsRegistry().add(new ExtendedConstant.Builder(new Constant("e"), 181d));
		mathEngine.getFunctionsRegistry().add(new CustomFunction.Builder("testFunction7", new String[]{"a", "b"}, "testFunction(a, b!, 4!, e)"));
		Assert.assertEquals("181.24984770675476", Expression.valueOf("testFunction7(2, 3)").numeric().toString());

		mathEngine.getConstantsRegistry().add(new ExtendedConstant.Builder(new Constant("e"), 181d));
		mathEngine.getFunctionsRegistry().add(new CustomFunction.Builder("testFunction8", new String[]{"a", "b"}, "testFunction(sin(a), b!, 4!, e)"));
		Assert.assertEquals("181.24999995362296", Expression.valueOf("testFunction8(2, 3)").numeric().toString());

	}
}

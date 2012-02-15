package jscl2.math.numeric;

import jscl2.JsclMathContext;
import jscl2.JsclMathContextImpl;
import junit.framework.Assert;
import junit.framework.TestCase;

import java.util.Date;

/**
 * User: serso
 * Date: 1/31/12
 * Time: 11:13 AM
 */
public class TimeTest extends TestCase {

	public static final int COMPLEX_MAX = 10000000;
	public static final int REAL_MAX = 100000000;

	public void testComplexTime() throws Exception {
		JsclMathContext mc = JsclMathContextImpl.defaultInstance();

		long startTime1 = new Date().getTime();
		Numeric result = mc.newReal(0L);
		for (long i = 0; i < COMPLEX_MAX; i++) {
			result = (  result.add(mc.newReal(i)).add(Complex.I(mc))  ).divide(   mc.newReal(2L).multiply(Complex.I(mc))   );
		}
		long time1 = new Date().getTime() - startTime1;


		long startTime2 = new Date().getTime();
		jscl.math.numeric.Numeric jsclResult = jscl.math.numeric.Real.valueOf(0d);
		for (long i = 0; i < COMPLEX_MAX; i++) {
			jsclResult = (  jsclResult.add(jscl.math.numeric.Real.valueOf(i)).add(jscl.math.numeric.Complex.I)  ).divide(jscl.math.numeric.Real.valueOf(2).multiply(jscl.math.numeric.Complex.I));
		}
		long time2 = new Date().getTime() - startTime2;


		System.out.println("Complex");
		System.out.println(time1);
		System.out.println(time2);

		System.out.println(result);
		System.out.println(jsclResult);

		//Assert.assertEquals(Real.newInstance(mc, mc.toRawNumber(value)), result);
		//Assert.assertEquals(Real.newInstance(mc, mc.toRawNumber(value)), jsclResult);
	}

	public void testRealTime() throws Exception {
		JsclMathContext mc = JsclMathContextImpl.defaultInstance();

		long startTime1 = new Date().getTime();
		Numeric result = mc.newReal(0L);
		final Real TWO = mc.newReal(2L);
		for (long i = 0; i < REAL_MAX; i++) {
			result = (  result.add(mc.newReal(i))  ).divide(TWO);
		}
		long time1 = new Date().getTime() - startTime1;


		long startTime2 = new Date().getTime();
		jscl.math.numeric.Numeric jsclResult = jscl.math.numeric.Real.valueOf(0d);
		final jscl.math.numeric.Real jsclTWO = jscl.math.numeric.Real.valueOf(2);
		for (long i = 0; i < REAL_MAX; i++) {
			jsclResult = (  jsclResult.add(jscl.math.numeric.Real.valueOf(i)) ).divide(jsclTWO);
		}
		long time2 = new Date().getTime() - startTime2;

		long startTime3= new Date().getTime();
		double value = 0.0;
		for (double i = 0; i < REAL_MAX; i+=1d) {
			value = (value + i) / 2;
		}
		long time3 = new Date().getTime() - startTime3;

		System.out.println("Real");
		System.out.println(time1);
		System.out.println(time2);
		System.out.println(time3);

		System.out.println(result);
		System.out.println(jsclResult);
		System.out.println(value);

		Assert.assertEquals(Real.newInstance(mc, mc.fromDouble(value)), result);
		Assert.assertEquals(jscl.math.numeric.Real.valueOf(value), jsclResult);
	}
}

package jscl2.math.numeric;

import jscl2.AngleUnit;
import jscl2.MathContext;
import jscl2.MathContextImpl;
import jscl2.NumeralBase;
import jscl2.math.RawNumberType;
import junit.framework.Assert;
import junit.framework.TestCase;

import java.util.Date;

/**
 * User: serso
 * Date: 1/31/12
 * Time: 11:13 AM
 */
public class RealTest extends TestCase {

	public static final int COMPLEX_MAX = 10000000;
	public static final int REAL_MAX = 100000000;

	public void testAdd() throws Exception {
		MathContext mc = MathContextImpl.defaultInstance();

		Numeric result = mc.newReal(0L);
		Long value = 0L;
		for (long i = 0; i < 1000; i++) {
			result = result.add(mc.newReal(i));
			value += i;
		}

		Assert.assertEquals(Real.newInstance(mc, mc.toRawNumber(value)), result);

		result = mc.newReal(0L);
		Double doubleValue = 0.0;
		for (long i = 0; i < 1000; i++) {
			result = result.add(mc.newReal(i)).divide(mc.newReal(3L));
			doubleValue = ( (double)i + doubleValue) / 3.0;
		}

		Assert.assertEquals(mc.newReal(doubleValue), result);

		Real r = mc.newReal(1L).divide(mc.newReal(10L));
		result = r.add(r).add(r).add(r).add(r).add(r).add(r).add(r);
		Assert.assertEquals(mc.newReal(0.7999999999999999), result);

		mc = MathContextImpl.newInstance(AngleUnit.deg, NumeralBase.dec, RawNumberType.BIG_DECIMAL);
		r = mc.newReal(1L).divide(mc.newReal(10L));
		result = r.add(r).add(r).add(r).add(r).add(r).add(r).add(r);
		Assert.assertEquals(mc.newReal(0.8), result);
	}

	public void testComplexTime() throws Exception {
		MathContext mc = MathContextImpl.defaultInstance();

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
		MathContext mc = MathContextImpl.defaultInstance();

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

		Assert.assertEquals(Real.newInstance(mc, mc.toRawNumber(value)), result);
		Assert.assertEquals(jscl.math.numeric.Real.valueOf(value), jsclResult);
	}
}

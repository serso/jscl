package jscl2.math.numeric;

import jscl2.AngleUnit;
import jscl2.MathContext;
import jscl2.MathContextImpl;
import jscl2.NumeralBase;
import jscl2.math.RawNumberType;
import junit.framework.Assert;
import org.junit.Test;

/**
 * User: serso
 * Date: 2/1/12
 * Time: 9:45 PM
 */
public class RealTest {
	@Test
	public void testAdd() throws Exception {
		MathContext mc = MathContextImpl.defaultInstance();

		Numeric result = mc.newReal(0L);
		Long value = 0L;
		for (long i = 0; i < 1000; i++) {
			result = result.add(mc.newReal(i));
			value += i;
		}

		Assert.assertEquals(Real.newInstance(mc, mc.fromLong(value)), result);

		result = mc.newReal(0L);
		Double doubleValue = 0.0;
		for (long i = 0; i < 1000; i++) {
			result = result.add(mc.newReal(i)).divide(mc.newReal(3L));
			doubleValue = ((double) i + doubleValue) / 3.0;
		}

		Assert.assertEquals(mc.newReal(doubleValue), result);

		Real r = mc.newReal(1L).divide(mc.newReal(10L));
		result = r.add(r).add(r).add(r).add(r).add(r).add(r).add(r);
		Assert.assertEquals(mc.newReal(0.7999999999999999), result);

		mc = MathContextImpl.newInstance(AngleUnit.deg, NumeralBase.dec, RawNumberType.BIG_DECIMAL);
		r = mc.newReal(1L).divide(mc.newReal(10L));
		result = r.add(r).add(r).add(r).add(r).add(r).add(r).add(r);
		Assert.assertEquals(mc.newReal(0.8), result);

		mc = MathContextImpl.defaultInstance();

		Assert.assertEquals(mc.newComplex(4, 2), mc.newReal(2).add(mc.newComplex(2, 2)));
	}

	@Test
	public void testSubtract() throws Exception {

	}

	@Test
	public void testMultiply() throws Exception {

	}

	@Test
	public void testDivide() throws Exception {

	}
}

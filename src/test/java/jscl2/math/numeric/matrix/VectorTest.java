package jscl2.math.numeric.matrix;

import jscl2.AngleUnit;
import jscl2.JsclMathContext;
import jscl2.JsclMathContextImpl;
import jscl2.NumeralBase;
import jscl2.math.RawNumberType;
import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.MathContext;

import static org.junit.Assert.fail;

/**
 * User: serso
 * Date: 2/14/12
 * Time: 4:38 PM
 */
public abstract class VectorTest<V extends NumericVector> {

	private static final JsclMathContext mc = JsclMathContextImpl.defaultInstance();

	@NotNull
	protected abstract Vector.Builder<V> getBuilder(@NotNull JsclMathContext mc, int length);

	@Test
	public void testNegate() throws Exception {
		final int length = 10;

		final Vector.Builder<V> b = getBuilder(mc, length);
		for (int i = 0; i < length; i++) {
			b.setI(i, mc.newReal(i));
		}

		final NumericVector v = b.build();
		for (int i = 0; i < v.getLength(); i++) {
			Assert.assertTrue(v.getI(i).moreOrEquals(mc.newReal(0L)));
		}

		final NumericVector v1 = v.negate();
		for (int i = 0; i < v1.getLength(); i++) {
			Assert.assertTrue(v1.getI(i).lessOrEquals(mc.newReal(0L)));
		}
	}

	@Test
	public void testMultiplication() throws Exception {
		final int length = 10;

		final Vector.Builder<V> b = getBuilder(mc, length);
		for (int i = 0; i < length; i++) {
			b.setI(i, mc.newReal(i));
		}

		final NumericVector v1 = b.build();
		final NumericVector v2 = b.build();

		try {
			v1.multiply(v2);
			Assert.fail();
		} catch (DimensionMustAgreeException e) {
			// ok, one of the vector must be transposed
		}

		try {
			v1.multiply(v2.transpose());
			Assert.fail();
		} catch (DimensionMustAgreeException e) {
			// ok, first vector must be transposed
		}

		Assert.assertEquals(mc.newReal(285.0), v1.transpose().multiply(v2));
	}

	@Test
	public void testSubtraction() throws Exception {
		final int length = 10;

		final V v = NumericVector.random(mc, length, getBuilder(mc, length));

		Assert.assertEquals(NumericVector.zero(mc, length), v.subtract(v));
	}

	@Test
	public void testAddition() throws Exception {
		final int length = 10;

		final JsclMathContext mc = JsclMathContextImpl.newInstance(AngleUnit.deg, NumeralBase.dec, RawNumberType.BIG_DECIMAL);

		final V v = NumericVector.random(mc, length, getBuilder(mc, length));

		Assert.assertEquals(NumericVector.zero(mc, length), v.add(v.negate()));
		for (int i = 2; i < 10; i++) {
			NumericVector sum = NumericVector.zero(mc, length);
			for (int j = 0; j < i; j++) {
				sum = sum.add(v);
			}
			Assert.assertEquals(sum, v.multiply(mc.newReal(i)));
		}
	}

	@Test
	public void testBuilderLock() throws Exception {
		final int length = 10;

		final Vector.Builder b = getBuilder(mc, length);
		for (int i = 0; i < length; i++) {
			b.setI(i, mc.newReal(i));
		}

		b.build();

		try {
			b.setI(0, mc.newReal(1L));
			fail();
		} catch (IllegalStateException e) {
			// trying to update already built vector
		}
	}
}

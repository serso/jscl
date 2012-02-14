package jscl2.math.numeric.matrix;

import jscl2.MathContext;
import jscl2.MathContextImpl;
import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.junit.Test;

/**
 * User: serso
 * Date: 2/14/12
 * Time: 4:38 PM
 */
public abstract class VectorTest<V extends NumericVector> {

	private static final MathContext mc = MathContextImpl.defaultInstance();

	@NotNull
	protected abstract Vector.Builder<V> getBuilder(@NotNull MathContext mc, int length);

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
}

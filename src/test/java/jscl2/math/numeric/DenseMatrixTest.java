package jscl2.math.numeric;

import jscl2.MathContext;
import jscl2.MathContextImpl;
import org.junit.Assert;
import org.junit.Test;

/**
 * User: serso
 * Date: 2/2/12
 * Time: 5:39 PM
 */
public class DenseMatrixTest {

	@Test
	public void testCreate() throws Exception {
		MathContext mc = MathContextImpl.defaultInstance();

		DenseMatrix.Builder b = new DenseMatrix.Builder(2, 2, mc);
		b.setIJ(0, 0, mc.newReal(1L));
		b.setIJ(1, 1, mc.newReal(2L));
		final Matrix m = b.build();

		Assert.assertEquals(Real.ZERO(mc), m.getIJ(0, 1));
		Assert.assertEquals(Real.ZERO(mc), m.getIJ(1, 0));
		Assert.assertEquals(Real.ONE(mc), m.getIJ(0, 0));
		Assert.assertEquals(Real.TWO(mc), m.getIJ(1, 1));

		try {
			b = new DenseMatrix.Builder(1, 1, mc);
			Assert.fail();
		} catch (AssertionError e) {
			// OK, should have an exception while creating 1x1 matrix
		}

	}

	@Test
	public void testMultiply() throws Exception {
		MathContext mc = MathContextImpl.defaultInstance();

		final Matrix E = AbstractMatrix.identity(mc, 5);
		final Matrix A = AbstractMatrix.random(mc, 5);
		final Matrix B = AbstractMatrix.random(mc, 5, 3);

		Assert.assertEquals(A, E.multiply(A));
		Assert.assertEquals(A, A.multiply(E));
		Assert.assertEquals(E, E.multiply(E));
		Assert.assertEquals(B, E.multiply(B));
	}
}

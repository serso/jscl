package jscl2.math.numeric.matrix;

import jscl2.MathContext;
import org.jetbrains.annotations.NotNull;

/**
 * User: serso
 * Date: 2/14/12
 * Time: 5:10 PM
 */
public class DenseVectorTest extends VectorTest<DenseVector> {
	@NotNull
	@Override
	protected Vector.Builder<DenseVector> getBuilder(@NotNull MathContext mc, int length) {
		return new DenseVector.Builder(mc, length);
	}
}

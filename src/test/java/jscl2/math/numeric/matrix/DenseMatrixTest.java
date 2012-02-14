package jscl2.math.numeric.matrix;

import jscl2.MathContext;
import org.jetbrains.annotations.NotNull;

/**
 * User: serso
 * Date: 2/3/12
 * Time: 11:36 AM
 */
public class DenseMatrixTest extends MatrixTest<DenseMatrix> {

	@NotNull
	@Override
	protected Matrix.Builder<DenseMatrix> getBuilder(@NotNull MathContext mc, int rows, int cols) {
		return new DenseMatrix.Builder(mc, rows, cols);
	}
}

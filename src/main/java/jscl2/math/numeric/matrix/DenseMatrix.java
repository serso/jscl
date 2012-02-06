package jscl2.math.numeric.matrix;

import jscl2.MathContext;
import jscl2.math.numeric.Numeric;
import jscl2.math.numeric.Real;
import org.jetbrains.annotations.NotNull;

/**
 * User: serso
 * Date: 2/2/12
 * Time: 12:53 PM
 */
public class DenseMatrix extends AbstractMatrix {

	public static class Builder extends AbstractBuilder<DenseMatrix> {
		@NotNull
		private final Numeric m[][];

		public Builder(@NotNull MathContext mc, int rows, int cols) {
			this(mc, rows, cols, false);
		}

		public Builder(@NotNull MathContext mc, int rows, int cols, boolean transposed) {
			super(mc, rows, cols, transposed);

			this.m = new Numeric[rows][cols];
		}

		@NotNull
		@Override
		protected Numeric getIJ0(int row, int col) {
			return this.m[row][col];
		}

		@Override
		public void setIJ0(int row, int col, @NotNull Numeric value) {
			this.m[row][col] = value;
		}

		@Override
		@NotNull
		public DenseMatrix build() {

			for (int i = 0; i < m.length; i++) {
				for (int j = 0; j < m[i].length; j++) {
					if (this.m[i][j] == null) {
						this.m[i][j] = Real.ZERO(mc);
					}
				}
			}

			return new DenseMatrix(mc, m);
		}
	}

	@NotNull
	private final Numeric m[][];

	private DenseMatrix(@NotNull final MathContext mc, @NotNull Numeric m[][]) {
		this(mc, m, false);
	}

	private DenseMatrix(@NotNull final MathContext mc, @NotNull Numeric m[][], final boolean transposed) {
		super(mc, m.length, m.length > 0 ? m[0].length : 0, transposed);
		this.m = m;
	}

	@NotNull
	@Override
	protected Matrix.Builder<? extends AbstractMatrix> getBuilder(int rows, int cols, boolean transposed) {
		return new Builder(this.mc, rows, cols, transposed);
	}

	@NotNull
	protected DenseMatrix newInstance0(@NotNull Numeric[][] m, boolean transposed) {
		return new DenseMatrix(getMathContext(), m, transposed);
	}

	@Override
	@NotNull
	public Numeric getIJ0(int row, int col) {
		return m[row][col];
	}

	@NotNull
	protected static DenseMatrix newInstance(@NotNull MathContext mc, @NotNull Numeric m[][]) {
		assert m.length > 0 && m[0].length > 0;
		assert m.length > 1 || m[0].length > 1;
		return new DenseMatrix(mc, m, false);
	}

	@NotNull
	@Override
	public DenseMatrix transpose() {
		return newInstance0(m, !transposed);
	}
}

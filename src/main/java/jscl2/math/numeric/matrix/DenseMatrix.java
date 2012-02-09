package jscl2.math.numeric.matrix;

import jscl2.MathContext;
import jscl2.math.numeric.AbstractNumber;
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
		private final AbstractNumber m[][];

		public Builder(@NotNull MathContext mc, int rows, int cols) {
			super(mc, rows, cols);

			this.m = new AbstractNumber[rows][cols];
		}

		@NotNull
		@Override
		protected AbstractNumber getIJ0(int row, int col) {
			return this.m[row][col];
		}

		@Override
		public void setIJ0(int row, int col, @NotNull AbstractNumber value) {
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
	private final AbstractNumber m[][];

	private DenseMatrix(@NotNull final MathContext mc, @NotNull AbstractNumber m[][]) {
		this(mc, m, false);
	}

	private DenseMatrix(@NotNull final MathContext mc, @NotNull AbstractNumber m[][], final boolean transposed) {
		super(mc, m.length, m.length > 0 ? m[0].length : 0, transposed);
		this.m = m;
	}

	@NotNull
	@Override
	protected Matrix.Builder<? extends AbstractMatrix> getBuilder(int rows, int cols) {
		return new Builder(this.mc, rows, cols);
	}

	@Override
	@NotNull
	public AbstractNumber getIJ0(int row, int col) {
		return m[row][col];
	}

	@NotNull
	@Override
	public DenseMatrix transpose() {
		return new DenseMatrix(mc, m, !transposed);
	}
}

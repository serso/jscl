package jscl2.math.numeric;

import jscl2.MathContext;
import org.jetbrains.annotations.NotNull;

/**
 * User: serso
 * Date: 2/2/12
 * Time: 12:53 PM
 */
public class DenseMatrix extends AbstractMatrix {

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
	protected DenseMatrix copy() {
		return new DenseMatrix(mc, new Numeric[rows][cols], transposed);
	}

	@NotNull
	protected DenseMatrix newInstance0(@NotNull Numeric[][] m) {
		return new DenseMatrix(getMathContext(), m, false);
	}

@NotNull
	protected DenseMatrix newInstance0(@NotNull Numeric[][] m, boolean transposed) {
		return new DenseMatrix(getMathContext(), m, transposed);
	}


	@Override
	protected void setIJ0(int row, int col, @NotNull Numeric value) {
		m[row][col] = value;
	}

	@Override
	@NotNull
	public Numeric getIJ0(int row, int col) {
		return m[row][col];
	}

	@NotNull
	public static DenseMatrix newInstance(@NotNull MathContext mc, @NotNull Numeric m[][]) {
		assert m.length > 0 && m[0].length > 0;
		assert m.length > 1 || m[0].length > 1;
		return new DenseMatrix(mc, m, false);
	}

	@Override
	@NotNull
	public Numeric[][] asArray() {
		return m;
	}
}

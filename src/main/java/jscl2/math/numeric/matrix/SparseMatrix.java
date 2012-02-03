package jscl2.math.numeric.matrix;

import jscl2.math.numeric.Real;
import jscl2.MathContext;
import jscl2.math.numeric.Numeric;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * User: serso
 * Date: 2/3/12
 * Time: 10:51 AM
 */
public class SparseMatrix extends AbstractMatrix {

	public static class Builder extends AbstractBuilder<SparseMatrix> {

		@NotNull
		private final SparseMatrix m;

		public Builder(@NotNull MathContext mc, int rows, int cols) {
			super(mc, rows, cols);
			this.m = new SparseMatrix(mc, rows, cols);
		}


		@Override
		public void setIJ(int row, int col, @NotNull Numeric value) {
			this.m.setIJ(row, col, value);
		}

		@NotNull
		@Override
		public SparseMatrix build() {
			return this.m;
		}
	}

	private static class Entry {

		private final int pos;

		@NotNull
		private final Numeric value;

		private Entry(int pos, @NotNull Numeric value) {
			this.pos = pos;
			this.value = value;
		}
	}

	@NotNull
	private final List<List<Entry>> m;

	protected SparseMatrix(@NotNull MathContext mc, int rows, int cols, boolean transposed) {
		super(mc, rows, cols, transposed);
		this.m = new ArrayList<List<Entry>>(rows);
		for (int i = 0; i < rows; i++) {
			this.m.add(new ArrayList<Entry>(0));
		}
	}

	protected SparseMatrix(@NotNull MathContext mc, int rows, int cols) {
		this(mc, rows, cols, false);
	}

	private SparseMatrix(@NotNull MathContext mc, @NotNull List<List<Entry>> m, int rows, int cols, boolean transposed) {
		super(mc, rows, cols, transposed);
		this.m = m;
	}

	@NotNull
	@Override
	protected AbstractMatrix newInstance0() {
		return new SparseMatrix(mc, rows, cols, transposed);
	}

	@NotNull
	@Override
	protected AbstractMatrix newInstance0(int rows, int cols) {
		return new SparseMatrix(mc, rows, cols);
	}

	@NotNull
	@Override
	protected Numeric getIJ0(int row, int col) {

		// for each row check the entry
		for (final Entry e : this.m.get(row)) {
			if (e.pos == col) {
				return e.value;
			} else if (e.pos > col) {
				break;
			}
		}

		return Real.ZERO(mc);
	}

	@Override
	protected void setIJ0(int row, int col, @NotNull Numeric value) {
		final List<Entry> mRow = this.m.get(row);

		for (int i = 0; i < mRow.size(); i++) {
			final Entry e = mRow.get(i);
			if (e.pos == col) {
				mRow.set(i, new Entry(col, value));
				return;
			} else if (e.pos > col) {
				mRow.add(i, new Entry(col, value));
				return;
			}
		}


		mRow.add(new Entry(col, value));
	}

	@NotNull
	@Override
	public SparseMatrix transpose() {
		return new SparseMatrix(mc, m, rows, cols, !transposed);
	}
}

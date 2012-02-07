package jscl2.math.numeric.matrix;

import jscl2.math.numeric.AbstractNumber;
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

	private static class Entry {

		private final int pos;

		@NotNull
		private final AbstractNumber value;

		private Entry(int pos, @NotNull AbstractNumber value) {
			this.pos = pos;
			this.value = value;
		}
	}


	public static class Builder extends AbstractBuilder<SparseMatrix> {

		@NotNull
		private final List<List<Entry>> m;

		public Builder(@NotNull MathContext mc, int rows, int cols) {
			this(mc, rows, cols, false);
		}

		public Builder(@NotNull MathContext mc, int rows, int cols, boolean transposed) {
			super(mc, rows, cols, transposed);
			this.m = new ArrayList<List<Entry>>();
			for (int i = 0; i < rows; i++) {
				this.m.add(new ArrayList<Entry>(0));
			}
		}

		@NotNull
		@Override
		protected AbstractNumber getIJ0(int row, int col) {

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
		public void setIJ0(int row, int col, @NotNull AbstractNumber value) {
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
		public SparseMatrix build() {
			return new SparseMatrix(mc, this.m, rows, cols, transposed);
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
	protected Matrix.Builder<? extends AbstractMatrix> getBuilder(int rows, int cols) {
		return new Builder(this.mc, rows, cols, false);
	}

	@NotNull
	@Override
	protected AbstractNumber getIJ0(int row, int col) {

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

	@NotNull
	@Override
	public SparseMatrix transpose() {
		return new SparseMatrix(mc, m, rows, cols, !transposed);
	}
}

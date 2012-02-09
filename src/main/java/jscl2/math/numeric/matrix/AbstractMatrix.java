package jscl2.math.numeric.matrix;

import jscl.math.NotDivisibleException;
import jscl2.MathContext;
import jscl2.math.numeric.AbstractNumber;
import jscl2.math.numeric.INumeric;
import jscl2.math.numeric.Numeric;
import jscl2.math.numeric.Real;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractMatrix extends Numeric implements Matrix<AbstractMatrix> {

	private static final String MATRIX_DIMENSIONS_MUST_AGREE = "Matrix dimensions must agree!";

	/*
	* NOTE: fields below are for internal representation of matrix - use them carefully.
	* If you want to get the actual matrix parameters use appropriate getters
	*/

	// number of rows in internal representation of matrix
	protected final int rows;

	// number of columns in internal representation of matrix
	protected final int cols;

	// is matrix transposed?
	protected final boolean transposed;

	/*
	* **********************************************
	* BUILDER
	* ***********************************************
	*/

	public static abstract class AbstractBuilder<T extends Matrix> implements Builder<T> {

		protected final int rows;

		protected final int cols;

		protected final boolean transposed;

		@NotNull
		protected final MathContext mc;

		protected AbstractBuilder(@NotNull MathContext mc, int rows, int cols) {
			if (!(rows > 0 && cols > 0)) {
				throw new IllegalArgumentException("Number of rows and columns must be positive!");
			}

			if (!(rows > 1 || cols > 1)) {
				throw new IllegalArgumentException("Matrix dimension must be more than 1 - use Vector instead!");
			}

			this.rows = rows;
			this.cols = cols;
			this.mc = mc;
			this.transposed = false;
		}

		public final void setIJ(int row, int col, @NotNull AbstractNumber value) {
			if (transposed) {
				setIJ0(col, row, value);
			} else {
				setIJ0(row, col, value);
			}
		}


		@NotNull
		@Override
		public final AbstractNumber getIJ(int row, int col) {
			if (transposed) {
				return getIJ0(col, row);
			} else {
				return getIJ0(row, col);
			}
		}

		@NotNull
		protected abstract AbstractNumber getIJ0(int row, int col);

		protected abstract void setIJ0(int row, int col, @NotNull AbstractNumber value);
	}

	/*
	* **********************************************
	* CONSTRUCTORS
	* ***********************************************
	*/

	protected AbstractMatrix(@NotNull MathContext mc, int rows, int cols) {
		this(mc, rows, cols, false);
	}

	protected AbstractMatrix(@NotNull MathContext mc, int rows, int cols, boolean transposed) {
		super(mc);
		this.rows = rows;
		this.cols = cols;
		this.transposed = transposed;
	}

	/**
	 * Method must return matrix builder of current matrix type with specified dimensions
	 * @param rows number of rows in matrix
	 * @param cols number of columns in matrix
	 *
	 * @return builder of matrix of current type
	 */
	@NotNull
	protected abstract Builder<? extends AbstractMatrix> getBuilder(int rows, int cols);

	/**
	 * @return builder for matrix with same dimensions as this matrix
	 */
	@NotNull
	private Builder<? extends AbstractMatrix> getBuilder() {
		return getBuilder(getRows(), getCols());
	}

	@Override
	public final int getRows() {
		return transposed ? cols : rows;
	}

	@Override
	public final int getCols() {
		return transposed ? rows : cols;
	}

	@NotNull
	public final AbstractNumber getIJ(int row, int col) {
		if (transposed) {
			return getIJ0(col, row);
		} else {
			return getIJ0(row, col);
		}
	}

	@NotNull
	protected abstract AbstractNumber getIJ0(int row, int col);

	@NotNull
	@Override
	public Real norm() {
		Real result = ZERO();

		for (int row = 0; row < getRows(); row++) {
			for (int col = 0; col < getCols(); col++) {
				final Real elementNorm = this.getIJ(row, col).norm();
				if ( elementNorm.more(result) ) {
					result = elementNorm;
				}
			}
		}

		return result;
	}

	/*
	 * **********************************************
	 * CHECKERS
	 * ***********************************************
	*/

	private void checkSameDimensions(@NotNull Matrix l, @NotNull Matrix r) {
		if (l.getCols() != r.getCols() || l.getRows() != r.getRows()) {
			throw new ArithmeticException(MATRIX_DIMENSIONS_MUST_AGREE);
		}
	}

	private void checkCrossDimensions(@NotNull Matrix l, @NotNull Matrix r) {
		if (l.getCols() != r.getRows()) {
			throw new ArithmeticException(MATRIX_DIMENSIONS_MUST_AGREE);
		}
	}

	/*
	 * **********************************************
	 * ADDITION
	 * ***********************************************
	*/

	@NotNull
	protected AbstractMatrix add0(@NotNull Matrix that) {
		final Builder<? extends AbstractMatrix> b = getBuilder();

		for (int i = 0; i < getRows(); i++) {
			for (int j = 0; j < getCols(); j++) {
				b.setIJ(i, j, this.getIJ(i, j).add(that.getIJ(i, j)));
			}
		}

		return b.build();
	}

	@NotNull
	@Override
	public AbstractMatrix add(@NotNull Matrix that) {
		checkSameDimensions(this, that);
		return add0(that);
	}

	@NotNull
	public Numeric add(@NotNull Numeric that) {
		if (that instanceof Matrix) {
			return add((Matrix) that);
		} else {
			throw new ArithmeticException(MATRIX_DIMENSIONS_MUST_AGREE);
		}
	}

	/*
	 * **********************************************
	 * SUBTRACTION
	 * ***********************************************
	*/

	@NotNull
	protected AbstractMatrix subtract0(@NotNull Matrix that) {
		final Builder<? extends AbstractMatrix> b = getBuilder();

		for (int i = 0; i < getRows(); i++) {
			for (int j = 0; j < getCols(); j++) {
				b.setIJ(i, j, this.getIJ(i, j).subtract(that.getIJ(i, j)));
			}
		}

		return b.build();
	}

	@NotNull
	@Override
	public final AbstractMatrix subtract(@NotNull Matrix that) {
		checkSameDimensions(this, that);
		return subtract0(that);
	}

	@NotNull
	public Numeric subtract(@NotNull Numeric that) {
		if (that instanceof Matrix) {
			return subtract((Matrix) that);
		} else {
			throw new ArithmeticException(MATRIX_DIMENSIONS_MUST_AGREE);
		}
	}

	/*
	 * **********************************************
	 * SCALAR TRANSFORMATION
	 * ***********************************************
	*/

	@NotNull
	private static Numeric tryToScalar(@NotNull AbstractMatrix m) {
		if (m.rows == 1 && m.cols == 1) {
			return m.getIJ(0, 0);
		} else {
			return m;
		}
	}

	@NotNull
	private static Numeric tryToScalar(@NotNull Numeric n) {
		if (n instanceof Matrix) {
			return tryToScalar(((AbstractMatrix) n));
		} else if (n instanceof Vector) {
			return tryToScalar(((AbstractVector) n));
		} else {
			return n;
		}
	}

	@NotNull
	private static Numeric tryToScalar(@NotNull AbstractVector v) {
		if (v.getLength() == 1) {
			return v.getI(0);
		} else {
			return v;
		}
	}

	/*
	 * **********************************************
	 * MULTIPLICATION
	 * ***********************************************
	*/

	@Override
	@NotNull
	public AbstractMatrix multiply(@NotNull Matrix that) {
		checkCrossDimensions(this, that);
		return multiply0(that);
	}

	@NotNull
	protected AbstractMatrix multiply0(@NotNull Matrix that) {
		final Builder<? extends AbstractMatrix> b = getBuilder(this.getRows(), that.getCols());

		for (int row = 0; row < this.getRows(); row++) {
			for (int col = 0; col < that.getCols(); col++) {
				AbstractNumber value = ZERO();

				for (int i = 0; i < this.getCols(); i++) {
					value = value.add(this.getIJ(row, i).multiply(that.getIJ(i, col)));
				}

				b.setIJ(row, col, value);

			}
		}

		return b.build();
	}

	@NotNull
	protected AbstractMatrix multiply(@NotNull AbstractNumber that) {
		final Builder<? extends AbstractMatrix> b = getBuilder();

		for (int i = 0; i < getRows(); i++) {
			for (int j = 0; j < getCols(); j++) {
				b.setIJ(i, j, this.getIJ(i, j).multiply(that));
			}
		}

		return b.build();
	}

	@NotNull
	public Numeric multiply(@NotNull Vector that) {
		if (!that.isTransposed()) {
			if (this.getCols() != that.getLength()) {
				throw new ArithmeticException(MATRIX_DIMENSIONS_MUST_AGREE);
			}
		} else {
			if (this.getRows() != that.getLength()) {
				throw new ArithmeticException(MATRIX_DIMENSIONS_MUST_AGREE);
			}
		}

		final SparseVector v = SparseVector.newInstance(getMathContext(), new AbstractNumber[this.getRows()]);
		final Numeric[] vElements = v.getElements();
		for (int i = 0; i < this.getRows(); i++) {
			vElements[i] = ZERO();
			for (int k = 0; k < this.getCols(); k++) {
				vElements[i] = vElements[i].add(getIJ(i, k).multiply(that.getI(k)));
			}
		}

		return tryToScalar(v);
	}

	@NotNull
	public Numeric multiply(@NotNull Numeric that) {
		if (that instanceof Matrix) {
			return multiply((Matrix) that);
		} else if (that instanceof Vector) {
			return multiply((Vector) that);
		} else if (that instanceof AbstractNumber) {
			return multiply((AbstractNumber) that);
		} else {
			throw new ArithmeticException();
		}
	}


	/*
	 * **********************************************
	 * DIVISION
	 * ***********************************************
	*/

	@NotNull
	public Numeric divide(@NotNull Numeric that) throws NotDivisibleException {

		if (that instanceof Matrix) {
			return multiply(that.inverse());
		} else if (that instanceof Vector) {
			throw new ArithmeticException("Matrix dimensions must agree!");
		} else {
			return scalarDivide(that);
		}

	}

	@NotNull
	protected AbstractMatrix scalarDivide(@NotNull Numeric that) {
		final Builder<? extends AbstractMatrix> b = getBuilder();

		for (int i = 0; i < this.getRows(); i++) {
			for (int j = 0; j < this.getCols(); j++) {
				b.setIJ(i, j, this.getIJ(i, j).divide(that));
			}
		}

		return b.build();
	}


	@NotNull
	public AbstractMatrix negate() {
		final Builder<? extends AbstractMatrix> b = getBuilder();

		for (int i = 0; i < this.getRows(); i++) {
			for (int j = 0; j < this.getCols(); j++) {
				b.setIJ(i, j, this.getIJ(i, j).negate());
			}
		}

		return b.build();
	}

	@NotNull
	@Override
	public Real abs() {
		throw new ArithmeticException();
	}

	public int signum() {
		// todo serso: strange signum definition for matrix
		for (int i = 0; i < this.getRows(); i++) {
			for (int j = 0; j < this.getCols(); j++) {
				int c = getIJ(i, j).signum();
				if (c < 0) {
					return -1;
				} else if (c > 0) {
					return 1;
				}
			}
		}

		return 0;
	}

	@NotNull
	@Override
	public Numeric sgn() {
		throw new ArithmeticException();
	}

	@NotNull
	@Override
	public AbstractNumber trace() {
		AbstractNumber result = ZERO();
		for (int i = 0; i < this.getRows(); i++) {
			result = result.add(getIJ(i, i));
		}
		return result;
	}

	@NotNull
	public Numeric inverse() {
		final Builder<? extends AbstractMatrix> b = getBuilder();

		for (int i = 0; i < this.getRows(); i++) {
			for (int j = 0; j < this.getRows(); j++) {
				b.setIJ(i, j, inverseElement(i, j));
			}
		}

		return b.build().transpose().divide(determinant());
	}

	AbstractNumber inverseElement(int k, int l) {
		final Builder<? extends AbstractMatrix> b = getBuilder();

		for (int i = 0; i < getRows(); i++) {
			for (int j = 0; j < getRows(); j++) {
				if (i == k) {
					b.setIJ(i, j, j == l ? ONE() : ZERO());
				} else {
					b.setIJ(i, j, this.getIJ(i, j));
				}
			}
		}

		return b.build().determinant();
	}

	@Override
	@NotNull
	public AbstractNumber determinant() {
		AbstractNumber a = ZERO();

		for (int i = 0; i < getRows(); i++) {
			if (getIJ(i, 0).signum() != 0) {

				final AbstractNumber determinant;
				if (getRows() == 1) {
					determinant = ZERO();
				} else if (getRows() == 2) {
					determinant =  this.getIJ(0 < i ? 0 : 1, 1);
				} else {
					final Builder<? extends AbstractMatrix> b = getBuilder(getRows() - 1, getRows() - 1);

					for (int j = 0; j < getRows() - 1; j++) {
						for (int k = 0; k < getRows() - 1; k++) {
							b.setIJ(j, k, this.getIJ(j < i ? j : j + 1, k + 1));
						}
					}
					determinant = b.build().determinant();
				}

				if (i % 2 == 0) {
					a = a.add(getIJ(i, 0).multiply(determinant));
				} else {
					a = a.subtract(getIJ(i, 0).multiply(determinant));
				}
			}
		}

		return a;
	}

	@NotNull
	public Numeric ln() {
		throw new ArithmeticException();
	}

	@NotNull
	@Override
	public Numeric lg() {
		throw new ArithmeticException();
	}

	@NotNull
	public Numeric exp() {
		throw new ArithmeticException();
	}

	public Numeric conjugate() {
		final Builder<? extends AbstractMatrix> b = getBuilder();
		
		for (int i = 0; i < getRows(); i++) {
			for (int j = 0; j < getCols(); j++) {
				b.setIJ(i, j, this.getIJ(i, j).conjugate());
			}
		}
		
		return b.build();
	}

	public int compareTo(@NotNull AbstractMatrix matrix) {
		// todo serso:
		return mathEquals(matrix) ? 0 : 1;
		//return ArrayComparator.comparator.compare(vectors(), matrix.vectors());
	}

	public int compareTo(@NotNull Numeric that) {
		if (that instanceof AbstractMatrix) {
			return compareTo((AbstractMatrix) that);
		} else {
			throw new ArithmeticException();
		}
	}

	@NotNull
	public static Matrix identity(@NotNull MathContext mc, int dimension) {
		return identity(mc, dimension, dimension);
	}

	@NotNull
	public static Matrix identity(@NotNull MathContext mc, int rows, int cols) {
		final Builder<SparseMatrix> b = new SparseMatrix.Builder(mc, rows, cols);

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				if (i == j) {
					b.setIJ(i, j, Real.newInstance(mc, mc.ONE()));
				}
			}
		}

		return b.build();
	}

	@NotNull
	public static Matrix random(@NotNull MathContext mc, int dimension) {
		return random(mc, dimension, dimension);
	}

	@NotNull
	public static Matrix random(@NotNull MathContext mc, int rows, int cols) {
		final Builder<DenseMatrix> b = new DenseMatrix.Builder(mc, rows, cols);

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				b.setIJ(i, j, mc.randomReal());
			}
		}

		return b.build();
	}

	public String toString() {
		final StringBuilder result = new StringBuilder();

		result.append("[");
		for (int i = 0; i < getRows(); i++) {
			result.append("[");
			for (int j = 0; j < getCols(); j++) {
				result.append(getIJ(i, j)).append(j < getCols() - 1 ? ", " : "");
			}
			result.append("]").append(i < getRows() - 1 ? ",\n" : "");
		}
		result.append("]");

		return result.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Matrix)) return false;

		AbstractMatrix that = (AbstractMatrix) o;

		if (getCols() != that.getCols()) return false;
		if (getRows() != that.getRows()) return false;

		for (int i = 0; i < getRows(); i++) {
			for (int j = 0; j < getCols(); j++) {
				if (!this.getIJ(i, j).equals(that.getIJ(i, j))) {
					return false;
				}
			}
		}

		return true;
	}

	@Override
	public int hashCode() {
		int result = getRows();

		result = 31 * result + getCols();

		for (int i = 0; i < getRows(); i++) {
			for (int j = 0; j < getCols(); j++) {
				result = 31 * result + getIJ(i, j).hashCode();
			}
		}

		return result;
	}

	@Override
	public boolean mathEquals(INumeric<Numeric> that) {
		if (that instanceof Matrix) {
			return equals(that);
		} else if (that instanceof Vector) {
			final Vector v = (Vector) that;
			if (v.isTransposed() && v.getLength() == getCols() && getRows() == 1) {
				for (int i = 0; i < getCols(); i++) {
					if (v.getI(i).equals(this.getIJ(0, i))) {
						return false;
					}
				}
				return true;
			} else if (!v.isTransposed() && v.getLength() == getRows() && getCols() == 1) {
				for (int i = 0; i < getRows(); i++) {
					if (v.getI(i).equals(this.getIJ(i, 0))) {
						return false;
					}
				}
				return true;
			} else {
				return false;
			}
		} else {
			if (this.getRows() == 1 && this.getCols() == 1) {
				return this.getIJ(0, 0).equals(that);
			} else {
				return false;
			}
		}
	}

	/*
	 * **********************************************
	 * UNSUPPORTED OPERATIONS
	 * ***********************************************
	*/

	@NotNull
	@Override
	public Numeric sqrt() {
		throw new ArithmeticException();
	}

	@NotNull
	@Override
	public Numeric nThRoot(int n) {
		throw new ArithmeticException();
	}

	@NotNull
	@Override
	public Numeric sin() {
		throw new ArithmeticException();
	}

	@NotNull
	@Override
	public Numeric cos() {
		throw new ArithmeticException();
	}

	@NotNull
	@Override
	public Numeric tan() {
		throw new ArithmeticException();
	}

	@NotNull
	@Override
	public Numeric cot() {
		throw new ArithmeticException();
	}

	@NotNull
	@Override
	public Numeric asin() {
		throw new ArithmeticException();
	}

	@NotNull
	@Override
	public Numeric acos() {
		throw new ArithmeticException();
	}

	@NotNull
	@Override
	public Numeric atan() {
		throw new ArithmeticException();
	}

	@NotNull
	@Override
	public Numeric acot() {
		throw new ArithmeticException();
	}

	@NotNull
	@Override
	public Numeric sinh() {
		throw new ArithmeticException();
	}

	@NotNull
	@Override
	public Numeric cosh() {
		throw new ArithmeticException();
	}

	@NotNull
	@Override
	public Numeric tanh() {
		throw new ArithmeticException();
	}

	@NotNull
	@Override
	public Numeric coth() {
		throw new ArithmeticException();
	}

	@NotNull
	@Override
	public Numeric asinh() {
		throw new ArithmeticException();
	}

	@NotNull
	@Override
	public Numeric acosh() {
		throw new ArithmeticException();
	}

	@NotNull
	@Override
	public Numeric atanh() {
		throw new ArithmeticException();
	}

	@NotNull
	@Override
	public Numeric acoth() {
		throw new ArithmeticException();
	}
}

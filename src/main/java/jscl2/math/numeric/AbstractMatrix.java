package jscl2.math.numeric;

import jscl.math.NotDivisibleException;
import jscl2.MathContext;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractMatrix extends Numeric implements Matrix {

	private static final String MATRIX_DIMENSIONS_MUST_AGREE = "Matrix dimensions must agree!";

	protected final int rows, cols;

	protected final boolean transposed;

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

	@NotNull
	protected abstract AbstractMatrix copy();

	@NotNull
	protected abstract AbstractMatrix newInstance0(@NotNull Numeric[][] m);

	@Override
	public int getRows() {
		return transposed ? cols : rows;
	}

	@Override
	public int getCols() {
		return transposed ? rows : cols;
	}

	@NotNull
	public Numeric getIJ(int row, int col){
		if ( transposed ) {
			return getIJ0(col, row);
		} else {
			return getIJ0(row, col);
		}
	}

	@NotNull
	protected abstract Numeric getIJ0(int row, int col);

	protected void setIJ(int row, int col, @NotNull Numeric value){
		if ( transposed ) {
			setIJ0(col, row, value);
		} else {
			setIJ0(row, col, value);
		}
	}

	protected abstract void setIJ0(int row, int col, @NotNull Numeric value);


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
		final AbstractMatrix m = copy();

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				m.setIJ(i, j, this.getIJ(i, j).add(that.getIJ(i, j)));
			}
		}

		return m;
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
		final AbstractMatrix m = copy();

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				m.setIJ(i, j, this.getIJ(i, j).subtract(that.getIJ(i, j)));
			}
		}

		return m;
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
			return tryToScalar(((Vector) n));
		} else {
			return n;
		}
	}

	@NotNull
	private static Numeric tryToScalar(@NotNull Vector v) {
		if (v.getLength() == 1) {
			return v.elements()[0];
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
	public Numeric multiply(@NotNull Matrix that) {
		checkCrossDimensions(this, that);
		return tryToScalar(multiply0(that));
	}

	@NotNull
	protected AbstractMatrix multiply0(@NotNull Matrix that) {
		final AbstractMatrix m = newInstance0(new Numeric[this.rows][that.getCols()]);

		for (int i = 0; i < this.rows; i++) {
			for (int j = 0; j < that.getCols(); j++) {
				m.setIJ(i, j, ZERO());
				for (int k = 0; k < cols; k++) {
					m.setIJ(i, j, m.getIJ(i, j).add(this.getIJ(i, k).multiply(that.getIJ(k, j))));
				}
			}
		}

		return m;
	}

	@NotNull
	protected AbstractMatrix scalarMultiply(@NotNull Numeric that) {
		final AbstractMatrix m = copy();

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				m.setIJ(i, j, this.getIJ(i, j).multiply(that));
			}
		}

		return m;
	}

	@NotNull
	private Numeric multiply(@NotNull Vector that) {
		if (!that.isTransposed()) {
			if (this.cols != that.getLength()) {
				throw new ArithmeticException(MATRIX_DIMENSIONS_MUST_AGREE);
			}
		} else {
			if (this.rows != that.getLength()) {
				throw new ArithmeticException(MATRIX_DIMENSIONS_MUST_AGREE);
			}
		}

		final Vector v = that.newInstance(new Numeric[rows]);
		final Numeric[] vElements = v.getElements();
		final Numeric[] thatElements = that.getElements();
		for (int i = 0; i < rows; i++) {
			vElements[i] = ZERO();
			for (int k = 0; k < cols; k++) {
				vElements[i] = vElements[i].add(getIJ(i, k).multiply(thatElements[k]));
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
		} else {
			return scalarMultiply(that);
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
		final AbstractMatrix m = copy();

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				m.setIJ(i, j, this.getIJ(i, j).divide(that));
			}
		}

		return m;
	}


	@NotNull
	public AbstractMatrix negate() {
		AbstractMatrix m = copy();

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				m.setIJ(i, j, this.getIJ(i, j).negate());
			}
		}

		return m;
	}

	@NotNull
	@Override
	public Numeric abs() {
		throw new ArithmeticException();
	}

	public int signum() {
		// todo serso: strange signum definition for matrix
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
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
	public Numeric transpose() {
		AbstractMatrix m = newInstance0(new Numeric[cols][rows]);

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				m.setIJ(j, i, this.getIJ(i, j));
			}
		}

		return m;
	}

	@NotNull
	@Override
	public Numeric trace() {
		Numeric s = ZERO();
		for (int i = 0; i < rows; i++) {
			s = s.add(getIJ(i, i));
		}
		return s;
	}

	@NotNull
	public Numeric inverse() {
		AbstractMatrix m = copy();

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < rows; j++) {
				m.setIJ(i, j, inverseElement(i, j));
			}
		}

		return m.transpose().divide(determinant());
	}

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

	Numeric inverseElement(int k, int l) {
		final AbstractMatrix result = copy();

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < rows; j++) {
				if (i == k) {
					result.setIJ(i, j, j == l ? ONE() : ZERO());
				} else {
					result.setIJ(i, j, this.getIJ(i, j));
				}
			}
		}

		return result.determinant();
	}

	@Override
	@NotNull
	public Numeric determinant() {
		if (rows > 1) {
			Numeric a = ZERO();
			// todo serso:
			/*for (int i = 0; i < rows; i++) {
				if (getIJ(i, 0).signum() != 0) {
					AbstractMatrix m = newInstance0(new Numeric[rows - 1][rows - 1]);
					for (int j = 0; j < rows - 1; j++) {
						System.arraycopy(this.m[j < i ? j : j + 1], 1, m.m[j], 0, rows - 1);
					}
					if (i % 2 == 0) {
						a = a.add(this.m[i][0].multiply(m.determinant()));
					} else {
						a = a.subtract(this.m[i][0].multiply(m.determinant()));
					}
				}
			}*/
			return a;
		} else if (rows > 0) {
			return getIJ(0, 0);
		} else {
			return ZERO();
		}
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
		AbstractMatrix m = copy();
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				m.setIJ(i, j, this.getIJ(i, j).conjugate());
			}
		}
		return m;
	}

	public int compareTo(Matrix matrix) {
		// todo serso:
		return 0;
		//return ArrayComparator.comparator.compare(vectors(), matrix.vectors());
	}

/*	public int compareTo(@NotNull Numeric that) {
		if (that instanceof Matrix) {
			return compareTo((Matrix) that);
		} else {
			return that.compareTo(this);
		}
	}*/

	@NotNull
	public static Matrix identity(@NotNull MathContext mc, int dimension) {
		return identity(mc, dimension, dimension);
	}

	@NotNull
	public static Matrix identity(@NotNull MathContext mc, int rows, int cols) {
		final DenseMatrix m = DenseMatrix.newInstance(mc, new Numeric[rows][cols]);

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				if (i == j) {
					m.setIJ(i, j, Real.newInstance(mc, mc.ONE()));
				} else {
					m.setIJ(i, j, Real.newInstance(mc, mc.ZERO()));
				}
			}
		}

		return m;
	}

	public String toString() {
		final StringBuilder result = new StringBuilder();

		result.append("[");
		for (int i = 0; i < rows; i++) {
			result.append("[");
			for (int j = 0; j < cols; j++) {
				result.append(getIJ(i, j)).append(j < cols - 1 ? ", " : "");
			}
			result.append("]").append(i < rows - 1 ? ",\n" : "");
		}
		result.append("]");

		return result.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Matrix)) return false;

		AbstractMatrix that = (AbstractMatrix) o;

		if (cols != that.cols) return false;
		if (rows != that.rows) return false;

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; i++) {
				if (!this.getIJ(i, j).equals(that.getIJ(i, j))) {
					return false;
				}
			}
		}

		return true;
	}

	@Override
	public int hashCode() {
		int result = rows;
		result = 31 * result + cols;
		for (Numeric[] vectors : asArray()) {
			for (Numeric n : vectors) {
				result = 31 * result + n.hashCode();
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
			if (v.isTransposed() && v.getLength() == cols && rows == 1) {
				for (int i = 0; i < cols; i++) {
					if (v.elements()[i].equals(this.getIJ(0, i))) {
						return false;
					}
				}
				return true;
			} else if (!v.isTransposed() && v.getLength() == rows && cols == 1) {
				for (int i = 0; i < rows; i++) {
					if (v.elements()[i].equals(this.getIJ(i, 0))) {
						return false;
					}
				}
				return true;
			} else {
				return false;
			}
		} else {
			if (this.rows == 1 && this.cols == 1) {
				return this.getIJ(0, 0).equals(that);
			} else {
				return false;
			}
		}
	}
}

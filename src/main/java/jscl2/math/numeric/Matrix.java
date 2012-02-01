package jscl2.math.numeric;

import jscl.math.NotDivisibleException;
import jscl2.MathContext;
import org.jetbrains.annotations.NotNull;

public class Matrix extends AbstractNumeric {

	private static final String MATRIX_DIMENSIONS_MUST_AGREE = "Matrix dimensions must agree!";

	@NotNull
	private final AbstractNumeric m[][];

	private final int rows, cols;

	/*
			 * **********************************************
			 * CONSTRUCTORS
			 * ***********************************************
			 */

	private Matrix(@NotNull final MathContext mc, @NotNull AbstractNumeric m[][]) {
		super(mc);
		this.m = m;
		rows = m.length;
		cols = m.length > 0 ? m[0].length : 0;
	}

	@NotNull
	private Matrix newInstance0() {
		return new Matrix(getMathContext(), new AbstractNumeric[rows][cols]);
	}

	@NotNull
	private Matrix newInstance0(@NotNull AbstractNumeric[][] m) {
		return new Matrix(getMathContext(), m);
	}

	@NotNull
	public static Matrix newInstance(@NotNull MathContext mc, @NotNull AbstractNumeric m[][]) {
		assert m.length > 0 && m[0].length > 0;
		assert m.length > 1 || m[0].length > 1;
		return new Matrix(mc, m);
	}


	public AbstractNumeric[][] elements() {
		return m;
	}

	private void checkSameDimensions(@NotNull Matrix l, @NotNull Matrix r) {
		if (l.cols != r.cols || l.rows != r.rows) {
			throw new ArithmeticException(MATRIX_DIMENSIONS_MUST_AGREE);
		}
	}

	/*
	 * **********************************************
	 * ADDITION
	 * ***********************************************
	*/

	@NotNull
	public Matrix add(@NotNull Matrix that) {
		checkSameDimensions(this, that);

		final Matrix m = newInstance0();

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				m.m[i][j] = this.m[i][j].add(that.m[i][j]);
			}
		}

		return m;
	}

	@NotNull
	public AbstractNumeric add(@NotNull AbstractNumeric that) {
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

	public Matrix subtract(@NotNull Matrix that) {
		checkSameDimensions(this, that);

		final Matrix m = newInstance0();

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				m.m[i][j] = this.m[i][j].subtract(that.m[i][j]);
			}
		}

		return m;
	}

	@NotNull
	public AbstractNumeric subtract(@NotNull AbstractNumeric that) {
		if (that instanceof Matrix) {
			return subtract((Matrix) that);
		} else {
			throw new ArithmeticException(MATRIX_DIMENSIONS_MUST_AGREE);
		}
	}

	/*
	 * **********************************************
	 * MULTIPLICATION
	 * ***********************************************
	*/

	@NotNull
	private static AbstractNumeric tryToScalar(@NotNull Matrix m) {
		if ( m.rows == 1 && m.cols == 1 ) {
			return m.m[0][0];
		} else {
			return m;
		}
	}

	@NotNull
	private static AbstractNumeric tryToScalar(@NotNull AbstractNumeric n) {
		if ( n instanceof Matrix) {
			return tryToScalar(((Matrix) n));
		} else if ( n instanceof Vector ) {
			return tryToScalar(((Vector) n));
		} else {
			return n;
		}
	}

	@NotNull
	private static AbstractNumeric tryToScalar(@NotNull Vector v) {
		if ( v.getLength() == 1  ) {
			return v.elements()[0];
		} else {
			return v;
		}
	}

	private void checkCrossDimensions(@NotNull Matrix l, @NotNull Matrix r) {
		if (l.cols != r.rows) {
			throw new ArithmeticException(MATRIX_DIMENSIONS_MUST_AGREE);
		}
	}

	@NotNull
	public AbstractNumeric multiply(@NotNull Matrix that) {
		checkCrossDimensions(this, that);

		final Matrix m = new Matrix(getMathContext(), new AbstractNumeric[this.rows][that.cols]);
		for (int i = 0; i < this.rows; i++) {
			for (int j = 0; j < that.cols; j++) {
				m.m[i][j] = ZERO();
				for (int k = 0; k < cols; k++) {
					m.m[i][j] = m.m[i][j].add(this.m[i][k].multiply(that.m[k][j]));
				}
			}
		}

		return tryToScalar(m);
	}

	@NotNull
	private AbstractNumeric multiply(@NotNull Vector that) {
		if (!that.isTransposed()) {
			if (this.cols != that.getLength()) {
				throw new ArithmeticException(MATRIX_DIMENSIONS_MUST_AGREE);
			}
		} else {
			if (this.rows != that.getLength()) {
				throw new ArithmeticException(MATRIX_DIMENSIONS_MUST_AGREE);
			}
		}

		final Vector v = that.newInstance(new AbstractNumeric[rows]);
		final AbstractNumeric[] vElements = v.getElements();
		final AbstractNumeric[] thatElements = that.getElements();
		for (int i = 0; i < rows; i++) {
			vElements[i] = ZERO();
			for (int k = 0; k < cols; k++) {
				vElements[i] = vElements[i].add(m[i][k].multiply(thatElements[k]));
			}
		}

		return tryToScalar(v);
	}

	@NotNull
	private Matrix scalarMultiply(@NotNull AbstractNumeric that) {
		Matrix m = newInstance0();

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				m.m[i][j] = this.m[i][j].multiply(that);
			}
		}

		return m;
	}

	@NotNull
	public AbstractNumeric multiply(@NotNull AbstractNumeric that) {
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
	public AbstractNumeric divide(@NotNull AbstractNumeric that) throws NotDivisibleException {

		if (that instanceof Matrix) {
			return multiply(that.inverse());
		} else if (that instanceof Vector) {
			throw new ArithmeticException("Matrix dimensions must agree!");
		} else {
			return scalarDivide(that);
		}

	}

	@NotNull
	private Matrix scalarDivide(@NotNull AbstractNumeric that) {
		Matrix m = newInstance0();

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				m.m[i][j] = this.m[i][j].divide(that);
			}
		}

		return m;
	}


	@NotNull
	public AbstractNumeric negate() {
		Matrix m = newInstance0();

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				m.m[i][j] = this.m[i][j].negate();
			}
		}

		return m;
	}

	public int signum() {
		// todo serso: strange signum definition for matrix
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				int c = m[i][j].signum();
				if (c < 0) {
					return -1;
				} else if (c > 0) {
					return 1;
				}
			}
		}

		return 0;
	}
/*
	public Numeric[] vectors() {
		Vector v[] = new Vector[rows];
		for (int i = 0; i < rows; i++) {
			v[i] = new Vector(getMathContext(), new Numeric[cols], transposed);
			System.arraycopy(m[i], 0, v[i].elements, 0, cols);
		}
		return v;
	}*/

	public AbstractNumeric transpose() {
		Matrix m = newInstance(getMathContext(), new AbstractNumeric[cols][rows]);
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				m.m[j][i] = this.m[i][j];
			}
		}
		return m;
	}

	public AbstractNumeric trace() {
		AbstractNumeric s = ZERO();
		for (int i = 0; i < rows; i++) {
			s = s.add(m[i][i]);
		}
		return s;
	}

	@NotNull
	public AbstractNumeric inverse() {
		Matrix m = newInstance0();

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < rows; j++) {
				m.m[i][j] = inverseElement(i, j);
			}
		}

		return m.transpose().divide(determinant());
	}

	AbstractNumeric inverseElement(int k, int l) {
		final Matrix result = newInstance0();

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < rows; j++) {
				if (i == k) {
					result.m[i][j] = j == l ? ONE() : ZERO();
				} else {
					result.m[i][j] = this.m[i][j];
				}
			}
		}

		return result.determinant();
	}

	public AbstractNumeric determinant() {
		if (rows > 1) {
			AbstractNumeric a = ZERO();
			for (int i = 0; i < rows; i++) {
				if (m[i][0].signum() != 0) {
					Matrix m = newInstance0(new AbstractNumeric[rows - 1][rows - 1]);
					for (int j = 0; j < rows - 1; j++) {
						System.arraycopy(this.m[j < i ? j : j + 1], 1, m.m[j], 0, rows - 1);
					}
					if (i % 2 == 0) {
						a = a.add(this.m[i][0].multiply(m.determinant()));
					} else {
						a = a.subtract(this.m[i][0].multiply(m.determinant()));
					}
				}
			}
			return a;
		} else if (rows > 0) {
			return m[0][0];
		} else {
			return ZERO();
		}
	}

	@NotNull
	public AbstractNumeric ln() {
		throw new ArithmeticException();
	}

	@NotNull
	@Override
	public AbstractNumeric lg() {
		throw new ArithmeticException();
	}

	@NotNull
	public AbstractNumeric exp() {
		throw new ArithmeticException();
	}

	public AbstractNumeric conjugate() {
		Matrix m = newInstance0();
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				m.m[i][j] = this.m[i][j].conjugate();
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

	public static Matrix identity(@NotNull MathContext mc, int n, int p) {
		Matrix m = new Matrix(mc, new AbstractNumeric[n][p]);
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < p; j++) {
				if (i == j) {
					m.m[i][j] = Real.newInstance(mc, mc.fromLong(1L));
				} else {
					m.m[i][j] = Real.newInstance(mc, mc.fromLong(0L));
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
				result.append(m[i][j]).append(j < cols - 1 ? ", " : "");
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

		Matrix that = (Matrix) o;

		if (cols != that.cols) return false;
		if (rows != that.rows) return false;

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; i++) {
				if (!this.m[i][j].equals(that.m[i][j])) {
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
		for (AbstractNumeric[] vectors : m) {
			for (AbstractNumeric n : vectors) {
				result = 31 * result + n.hashCode();
			}
		}

		return result;
	}

	@Override
	public boolean mathEquals(INumeric<AbstractNumeric> that) {
		if (that instanceof Matrix) {
			return equals(that);
		} else if (that instanceof Vector) {
			final Vector v = (Vector) that;
			if (v.isTransposed() && v.getLength() == cols && rows == 1) {
				for (int i = 0; i < cols; i++) {
					if (v.elements()[i].equals(this.m[0][i])) {
						return false;
					}
				}
				return true;
			} else if (!v.isTransposed() && v.getLength() == rows && cols == 1) {
				for (int i = 0; i < rows; i++) {
					if (v.elements()[i].equals(this.m[i][0])) {
						return false;
					}
				}
				return true;
			} else {
				return false;
			}
		} else {
			if (this.rows == 1 && this.cols == 1) {
				return this.m[0][0].equals(that);
			} else {
				return false;
			}
		}
	}
}

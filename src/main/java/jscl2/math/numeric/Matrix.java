package jscl2.math.numeric;

import jscl.math.NotDivisibleException;
import jscl.util.ArrayComparator;
import jscl2.MathContext;
import jscl2.math.ArithmeticUtils;
import org.jetbrains.annotations.NotNull;

public class Matrix extends Numeric {

	@NotNull
	private final Numeric m[][];

	private final int rows, cols;

	/*
			 * **********************************************
			 * CONSTRUCTORS
			 * ***********************************************
			 */

	public Matrix(@NotNull final MathContext mathContext, @NotNull Numeric m[][]) {
		super(mathContext);
		this.m = m;
		rows = m.length;
		cols = m.length > 0 ? m[0].length : 0;
	}

	public Numeric[][] elements() {
		return m;
	}

	private void checkSameDimensions(@NotNull Matrix l, @NotNull Matrix r) {
		if (l.cols != r.cols || l.rows != r.rows) {
			throw new ArithmeticException("Matrix dimensions must agree!");
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

		final Matrix m = newInstance();

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				m.m[i][j] = this.m[i][j].add(that.m[i][j]);
			}
		}

		return m;
	}

	@NotNull
	public Numeric add(@NotNull Numeric that) {
		if (that instanceof Matrix) {
			return add((Matrix) that);
		} else {
			return ArithmeticUtils.add(this, that);
		}
	}

	/*
	 * **********************************************
	 * SUBTRACTION
	 * ***********************************************
	*/

	public Matrix subtract(@NotNull Matrix that) {
		checkSameDimensions(this, that);

		final Matrix m = newInstance();

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				m.m[i][j] = this.m[i][j].subtract(that.m[i][j]);
			}
		}

		return m;
	}

	@NotNull
	public Numeric subtract(@NotNull Numeric that) {
		if (that instanceof Matrix) {
			return subtract((Matrix) that);
		} else {
			return subtract(valueOf(that));
		}
	}

	/*
	 * **********************************************
	 * MULTIPLICATION
	 * ***********************************************
	*/

	private void checkCrossDimensions(@NotNull Matrix l, @NotNull Matrix r) {
		if (l.cols != r.rows) {
			throw new ArithmeticException("Matrix dimensions must agree!");
		}
	}

	public Matrix multiply(@NotNull Matrix that) {
		checkCrossDimensions(this, that);

		final Matrix m = newInstance(new Numeric[this.rows][that.cols]);
		for (int i = 0; i < this.rows; i++) {
			for (int j = 0; j < that.cols; j++) {
				m.m[i][j] = ZERO();
				for (int k = 0; k < cols; k++) {
					m.m[i][j] = m.m[i][j].add(this.m[i][k].multiply(that.m[k][j]));
				}
			}
		}

		return m;
	}

	@NotNull
	public Numeric multiply(@NotNull Numeric that) {
		if (that instanceof Matrix) {
			return multiply((Matrix) that);
		} else if (that instanceof Vector) {
			Vector v = ((Vector) that).newInstance(new Numeric[rows]);
			Vector v2 = (Vector) that;
			if (cols != v2.n) throw new ArithmeticException();
			for (int i = 0; i < rows; i++) {
				v.element[i] = ZERO();
				for (int k = 0; k < cols; k++) {
					v.element[i] = v.element[i].add(m[i][k].multiply(v2.element[k]));
				}
			}
			return v;
		} else {
			Matrix m = newInstance();
			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < cols; j++) {
					m.m[i][j] = this.m[i][j].multiply(that);
				}
			}
			return m;
		}
	}

	@NotNull
	public Numeric divide(@NotNull Numeric that) throws NotDivisibleException {

		if (that instanceof Matrix) {
			return multiply(that.inverse());
		} else if (that instanceof Vector) {
			throw new ArithmeticException();
		} else {
			Matrix m = newInstance();
			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < cols; j++) {
					m.m[i][j] = this.m[i][j].divide(that);
				}
			}
			return m;
		}

	}

	@NotNull
	public Numeric negate() {
		Matrix m = newInstance();
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				m.m[i][j] = this.m[i][j].negate();
			}
		}
		return m;
	}

	public int signum() {
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

	@NotNull
	public Numeric valueOf(@NotNull Numeric numeric) {
		if (numeric instanceof Matrix || numeric instanceof Vector) {
			throw new ArithmeticException();
		} else {
			final Matrix m = (Matrix) identity(getMathContext(), rows, cols).multiply(numeric);
			return newInstance(m.m);
		}
	}

	public Numeric[] vectors() {
		Vector v[] = new Vector[rows];
		for (int i = 0; i < rows; i++) {
			v[i] = new Vector(getMathContext(), new Numeric[cols]);
			for (int j = 0; j < cols; j++) {
				v[i].element[j] = m[i][j];
			}
		}
		return v;
	}

	public Numeric transpose() {
		Matrix m = newInstance(new Numeric[cols][rows]);
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				m.m[j][i] = this.m[i][j];
			}
		}
		return m;
	}

	public Numeric trace() {
		Numeric s = ZERO();
		for (int i = 0; i < rows; i++) {
			s = s.add(m[i][i]);
		}
		return s;
	}

	@NotNull
	public Numeric inverse() {
		Matrix m = newInstance();

		for (int i = 0; i < cols; i++) {
			for (int j = 0; j < rows; j++) {
				m.m[i][j] = inverseElement(i, j);
			}
		}

		return m.transpose().divide(determinant());
	}

	Numeric inverseElement(int k, int l) {
		final Matrix result = newInstance();

		for (int i = 0; i < cols; i++) {
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

	public Numeric determinant() {
		if (rows > 1) {
			Numeric a = ZERO();
			for (int i = 0; i < rows; i++) {
				if (m[i][0].signum() != 0) {
					Matrix m = newInstance(new Numeric[rows - 1][rows - 1]);
					for (int j = 0; j < rows - 1; j++) {
						for (int k = 0; k < rows - 1; k++) m.m[j][k] = this.m[j < i ? j : j + 1][k + 1];
					}
					if (i % 2 == 0) {
						a = a.add(this.m[i][0].multiply(m.determinant()));
					} else {
						a = a.subtract(this.m[i][0].multiply(m.determinant()));
					}
				}
			}
			return a;
		} else if (rows > 0) return m[0][0];
		else return ZERO();
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
		Matrix m = newInstance();
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				m.m[i][j] = this.m[i][j].conjugate();
			}
		}
		return m;
	}

	public int compareTo(Matrix matrix) {
		return ArrayComparator.comparator.compare(vectors(), matrix.vectors());
	}

	public int compareTo(@NotNull Numeric numeric) {
		if (numeric instanceof Matrix) {
			return compareTo((Matrix) numeric);
		} else {
			return compareTo(valueOf(numeric));
		}
	}

	@NotNull
	public static Matrix identity(@NotNull MathContext mc, int dimension) {
		return identity(mc, dimension, dimension);
	}

	public static Matrix identity(@NotNull MathContext mc, int n, int p) {
		Matrix m = new Matrix(mc, new Numeric[n][p]);
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < p; j++) {
				if (i == j) {
					m.m[i][j] = Real.newInstance(mc, mc.toRawNumber(1L));
				} else {
					m.m[i][j] = Real.newInstance(mc, mc.toRawNumber(0L));
				}
			}
		}
		return m;
	}

	public String toString() {
		final StringBuilder result = new StringBuilder();
		result.append("{");
		for (int i = 0; i < rows; i++) {
			result.append("{");
			for (int j = 0; j < cols; j++) {
				result.append(m[i][j]).append(j < cols - 1 ? ", " : "");
			}
			result.append("}").append(i < rows - 1 ? ",\n" : "");
		}
		result.append("}");
		return result.toString();
	}

	@NotNull
	protected Matrix newInstance() {
		return newInstance(new Numeric[rows][cols]);
	}

	@NotNull
	protected Matrix newInstance(@NotNull Numeric element[][]) {
		return new Matrix(getMathContext(), element);
	}
}

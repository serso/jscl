package jscl2.math.numeric.matrix;

import jscl.math.NotDivisibleException;
import jscl2.MathContext;
import jscl2.math.numeric.NumericNumber;
import jscl2.math.numeric.INumeric;
import jscl2.math.numeric.Numeric;
import jscl2.math.numeric.Real;
import org.jetbrains.annotations.NotNull;

/**
 * User: serso
 * Date: 2/9/12
 * Time: 6:23 PM
 */
public abstract class NumericVector extends Numeric implements Vector {

	private static final String VECTOR_DIMENSIONS_MUST_AGREE = "Vector dimensions must agree!";

	protected final int length;

	protected final boolean transposed;


	/*
	* **********************************************
	* BUILDER
	* ***********************************************
	*/

	public static abstract class AbstractBuilder<T extends Vector> implements Vector.Builder<T> {

		protected final int length;

		protected final boolean transposed;

		@NotNull
		protected final MathContext mc;

		private boolean built = false;

		protected AbstractBuilder(@NotNull MathContext mc, int length, final boolean transposed) {
			if (length <= 0) {
				throw new IllegalArgumentException("Number of elements in vector must be positive!");
			} else if (length <= 1) {
				throw new IllegalArgumentException("Vector dimension must be more than 1 - use Number instead!");
			}

			this.length = length;
			this.mc = mc;
			this.transposed = transposed;
		}

		@NotNull
		@Override
		public final T build() {
			this.built = true;
			return build0();
		}

		@Override
		public final void setI(int index, @NotNull NumericNumber value) {
			if ( built ) {
				throw new IllegalStateException("Vector already built - no changes can be done!");
			}
			setI0(index, value);
		}

		protected abstract void setI0(int index, @NotNull NumericNumber value);

		@NotNull
		protected abstract T build0();
	}

	/*
	* **********************************************
	* CONSTRUCTORS
	* ***********************************************
	*/

	protected NumericVector(@NotNull MathContext mathContext, int length, boolean transposed) {
		super(mathContext);
		this.transposed = transposed;
		this.length = length;
	}

	/**
	 * Method must return vector builder of current vector type with specified dimension
	 *
	 *
	 * @param length number of elements in vector
	 * @param transposed transposed vector or not
	 * @return builder of vector of current type
	 */
	@NotNull
	protected abstract Vector.Builder<? extends NumericVector> getBuilder(int length, boolean transposed);

	/**
	 * @return builder for matrix with same dimensions as this matrix
	 */
	@NotNull
	private Vector.Builder<? extends NumericVector> getBuilder() {
		return getBuilder(length, transposed);
	}

	@NotNull
	public NumericVector add(@NotNull Vector that) {
		checkSameDimensions(that);

		final Builder<? extends NumericVector> b = getBuilder();

		for (int i = 0; i < length; i++) {
			b.setI(i, this.getI(i).add(that.getI(i)));
		}

		return b.build();
	}

	@NotNull
	public Numeric add(@NotNull Numeric that) {
		if (that instanceof Vector) {
			return add((Vector) that);
		} else {
			return that.add(this);
		}
	}

	@NotNull
	public NumericVector subtract(@NotNull Vector that) {
		checkSameDimensions(that);

		final Builder<? extends NumericVector> b = getBuilder();

		for (int i = 0; i < length; i++) {
			b.setI(i, this.getI(i).subtract(that.getI(i)));
		}

		return b.build();
	}

	@NotNull
	public Numeric subtract(@NotNull Numeric that) {
		if (that instanceof Vector) {
			return subtract((Vector) that);
		} else {
			return that.subtract(this);
		}
	}

	@NotNull
	public Numeric multiply(@NotNull Numeric that) {
		if (that instanceof Vector) {
			return multiply((Vector) that);
		} else if (that instanceof Matrix) {
			return ((Matrix) that).transpose().multiply(this);
		} else if (that instanceof NumericNumber) {
			return multiply((NumericNumber) that);
		} else {
			throw new ArithmeticException();
		}
	}

	@NotNull
	private NumericVector multiply(@NotNull NumericNumber that) {
		final Builder<? extends NumericVector> b = getBuilder();
		for (int i = 0; i < length; i++) {
			b.setI(i, this.getI(i).multiply(that));
		}
		return b.build();
	}

	@NotNull
	public NumericNumber multiply(@NotNull Vector that) {
		checkCrossDimensions(that);

		NumericNumber result = ZERO();
		for (int i = 0; i < length; i++) {
			result = result.add(this.getI(i).multiply(that.getI(i)));
		}

		return result;
	}

	@NotNull
	public Numeric divide(@NotNull Numeric that) throws NotDivisibleException {
		if (that instanceof Vector) {
			throw new ArithmeticException();
		} else if (that instanceof Matrix) {
			return multiply(that.inverse());
		} else if (that instanceof NumericNumber) {
			return divide((NumericNumber)that);
		} else {
			throw new ArithmeticException();
		}
	}

	@NotNull
	private NumericVector divide(@NotNull NumericNumber that) {
		final Builder<? extends NumericVector> b = getBuilder();

		for (int i = 0; i < length; i++) {
			b.setI(i, this.getI(i).divide(that));
		}

		return b.build();
	}

	@NotNull
	@Override
	public Real abs() {
		throw new ArithmeticException();
	}

	@NotNull
	@Override
	public Real norm() {
		Real result = ZERO();

		for (int i = 0; i < this.length; i++) {
			final NumericNumber el = this.getI(i);
			final Real norm = el.norm();
			if (norm.more(result)) {
				result = norm;
			}

		}

		return result;
	}

	@NotNull
	public NumericVector negate() {
		final Builder<? extends NumericVector> b = getBuilder();
		for (int i = 0; i < length; i++) {
			b.setI(i, this.getI(i).negate());
		}
		return b.build();
	}

	public int signum() {
		for (int i = 0; i < length; i++) {
			int c = getI(i).signum();
			if (c < 0) {
				return -1;
			} else if (c > 0) {
				return 1;
			}
		}
		return 0;
	}

	@NotNull
	@Override
	public Numeric sgn() {
		throw new ArithmeticException();
	}

	protected void checkSameDimensions(@NotNull Vector that) {
		if (this.isTransposed() != that.isTransposed()) {
			throw new ArithmeticException(VECTOR_DIMENSIONS_MUST_AGREE);
		}

		if (this.length != that.getLength()) {
			throw new ArithmeticException(VECTOR_DIMENSIONS_MUST_AGREE);
		}
	}

	private void checkCrossDimensions(@NotNull Vector that) {
		if (this.isTransposed() == that.isTransposed()) {
			throw new ArithmeticException(VECTOR_DIMENSIONS_MUST_AGREE);
		}

		if (this.length != that.getLength()) {
			throw new ArithmeticException(VECTOR_DIMENSIONS_MUST_AGREE);
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

	@NotNull
	@Override
	public Numeric inverse() {
		throw new ArithmeticException();
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

	public Numeric conjugate() {
		final Builder<? extends NumericVector> b = getBuilder();
		for (int i = 0; i < length; i++) {
			b.setI(i, this.getI(i).conjugate());
		}
		return b.build();
	}

	public String toString() {
		final StringBuilder result = new StringBuilder();

		result.append("[");

		for (int i = 0; i < length; i++) {
			result.append(getI(i)).append(i < length - 1 ? ", " : "");
		}

		result.append("]");

		return result.toString();
	}

	@Override
	public boolean isTransposed() {
		return transposed;
	}

	@Override
	public int getLength() {
		return length;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Vector)) return false;

		Vector vector = (Vector) o;

		if (length != vector.getLength()) return false;
		if (transposed != vector.isTransposed()) return false;
		for (int i = 0; i < vector.getLength(); i++) {
			if (!getI(i).equals(vector.getI(i))) {
				return false;
			}
		}

		return true;
	}

	@Override
	public int hashCode() {
		int result = length;
		result = 31 * result + (transposed ? 1 : 0);
		for (int i = 0; i < length; i++) {
				result = 31 * result + this.getI(i).hashCode();
		}
		return result;
	}

	@Override
	public boolean mathEquals(INumeric<Numeric> that) {
		if (that instanceof Vector) {
			return equals(that);
		} else if (that instanceof Matrix) {
			return that.mathEquals(this);
		} else if (this.length == 1) {
			return this.getI(0).mathEquals(that);
		} else {
			return false;
		}
	}

	@Override
	public int compareTo(Numeric o) {
		// todo serso:
		return 0;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@NotNull
	public static Vector unity(@NotNull MathContext mc, int dimension) {
		final Builder<SparseVector> b = new SparseVector.Builder(mc, dimension);

		b.setI(0, Real.ONE(mc));

		return b.build();
	}
}

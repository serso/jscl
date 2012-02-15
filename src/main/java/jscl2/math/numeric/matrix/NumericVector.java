package jscl2.math.numeric.matrix;

import jscl.math.NotDivisibleException;
import jscl2.JsclMathContext;
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
public abstract class NumericVector extends Numeric implements Vector<NumericVector> {

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
		protected final JsclMathContext mc;

		private boolean built = false;

		protected AbstractBuilder(@NotNull JsclMathContext mc, int length, final boolean transposed) {
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

		@Override
		public int getLength() {
			return this.length;
		}

		@Override
		public String toString() {
			return NumericVector.toString(this);
		}
	}

	/*
	* **********************************************
	* CONSTRUCTORS
	* ***********************************************
	*/

	protected NumericVector(@NotNull JsclMathContext mathContext, int length, boolean transposed) {
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

	/*
	* **********************************************
	* OPERATIONS
	* ***********************************************
	*/

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

	public Numeric conjugate() {
		final Builder<? extends NumericVector> b = getBuilder();
		for (int i = 0; i < length; i++) {
			b.setI(i, this.getI(i).conjugate());
		}
		return b.build();
	}

	/*
	* ********************************
	* 		VECTOR ARITHMETIC OPERATIONS
	* ********************************
	*/

	/*
	* ********************************
	* 		ADDITION
	* ********************************
	*/

	@NotNull
	public NumericVector add(@NotNull NumericVector that) {
		checkSameDimensions(that);

		final Builder<? extends NumericVector> b = getBuilder();

		for (int i = 0; i < length; i++) {
			b.setI(i, this.getI(i).add(that.getI(i)));
		}

		return b.build();
	}

	@NotNull
	public Numeric add(@NotNull Numeric that) {
		if (that instanceof NumericVector) {
			return add((NumericVector) that);
		} else {
			return that.add(this);
		}
	}

	/*
	* ********************************
	* 		SUBTRACTION
	* ********************************
	*/

	@NotNull
	public NumericVector subtract(@NotNull NumericVector that) {
		checkSameDimensions(that);

		final Builder<? extends NumericVector> b = getBuilder();

		for (int i = 0; i < length; i++) {
			b.setI(i, this.getI(i).subtract(that.getI(i)));
		}

		return b.build();
	}

	@NotNull
	public Numeric subtract(@NotNull Numeric that) {
		if (that instanceof NumericVector) {
			return subtract((NumericVector) that);
		} else {
			return that.subtract(this);
		}
	}

	/*
	* ********************************
	* 		MULTIPLICATION
	* ********************************
	*/

	@NotNull
	public Numeric multiply(@NotNull Numeric that) {
		if (that instanceof NumericVector) {
			return multiply((NumericVector) that);
		} else if (that instanceof Matrix) {
			return ((Matrix) that).transpose().multiply(this);
		} else if (that instanceof NumericNumber) {
			return multiply((NumericNumber) that);
		} else {
			throw new ArithmeticException();
		}
	}

	@Override
	@NotNull
	public NumericVector multiply(@NotNull NumericNumber that) {
		final Builder<? extends NumericVector> b = getBuilder();
		for (int i = 0; i < length; i++) {
			b.setI(i, this.getI(i).multiply(that));
		}
		return b.build();
	}

	@NotNull
	public NumericNumber multiply(@NotNull NumericVector that) {
		checkCrossDimensions(that);

		NumericNumber result = ZERO();
		for (int i = 0; i < length; i++) {
			result = result.add(this.getI(i).multiply(that.getI(i)));
		}

		return result;
	}

	/*
	* ********************************
	* 		DIVISION
	* ********************************
	*/

	@NotNull
	public Numeric divide(@NotNull Numeric that) throws NotDivisibleException {
		if (that instanceof Matrix) {
			return multiply(that.inverse());
		} else if (that instanceof NumericNumber) {
			return divide((NumericNumber) that);
		} else {
			throw new ArithmeticException();
		}
	}

	@Override
	@NotNull
	public NumericVector divide(@NotNull NumericNumber that) {
		final Builder<? extends NumericVector> b = getBuilder();

		for (int i = 0; i < length; i++) {
			b.setI(i, this.getI(i).divide(that));
		}

		return b.build();
	}

	/*
	* ********************************
	* 		CHECKERS
	* ********************************
	*/

	protected void checkSameDimensions(@NotNull Vector that) {
		if (this.isTransposed() != that.isTransposed()) {
			throw new DimensionMustAgreeException(this, that);
		}

		if (this.length != that.getLength()) {
			throw new DimensionMustAgreeException(this, that);
		}
	}

	private void checkCrossDimensions(@NotNull Vector that) {
		if (this.isTransposed() == that.isTransposed()) {
			throw new DimensionMustAgreeException(this, that);
		}

		if (!this.isTransposed()) {
			throw new DimensionMustAgreeException(this, that);
		}

		if (this.length != that.getLength()) {
			throw new DimensionMustAgreeException(this, that);
		}
	}

	/*
	* ********************************
	* 		UNSUPPORTED OPERATIONS
	* ********************************
	*/
	@NotNull
	@Override
	public Numeric sgn() {
		throw new ArithmeticException();
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

	@NotNull
	public String toString() {
		return toString(this);
	}

	public static String toString(@NotNull CommonVectorInterface v) {
		final StringBuilder result = new StringBuilder();

		result.append("[");

		final int length = v.getLength();
		for (int i = 0; i < length; i++) {
			result.append(v.getI(i)).append(i < length - 1 ? ", " : "");
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

	/*
	* **********************************************
	* STATIC METHOD
	* ***********************************************
	*/

	@NotNull
	public static NumericVector random(@NotNull JsclMathContext mathContext, int length) {
		final Builder<? extends NumericVector> b = new SparseVector.Builder(mathContext,  length);

		return random(mathContext, length, b);
	}

	// package protected for tests
	@NotNull
	static <T extends NumericVector> T random(@NotNull JsclMathContext mathContext, int length, @NotNull Builder<T> b) {
		for (int i = 0; i < length; i++) {
			  b.setI(i, mathContext.randomReal());
		}

		return b.build();
	}

	@NotNull
	public static NumericVector unity(@NotNull JsclMathContext mc, int dimension) {
		final Builder<SparseVector> b = new SparseVector.Builder(mc, dimension);

		b.setI(0, Real.ONE(mc));

		return b.build();
	}

	@NotNull
	public static NumericVector zero(@NotNull JsclMathContext mc, int dimension) {
		return new SparseVector.Builder(mc, dimension).build();
	}
}

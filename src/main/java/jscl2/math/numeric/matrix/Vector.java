package jscl2.math.numeric.matrix;

import jscl.math.NotDivisibleException;
import jscl2.MathContext;
import jscl2.math.numeric.INumeric;
import jscl2.math.numeric.Numeric;
import jscl2.math.numeric.Real;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class Vector extends Numeric {

	private static final String VECTOR_DIMENSIONS_MUST_AGREE = "Vector dimensions must agree!";

	@NotNull
	private final Numeric elements[];
	private final int length;
	private final boolean transposed;

	private Vector(@NotNull MathContext mathContext, @NotNull Numeric[] elements) {
		this(mathContext, elements, false);
	}

	private Vector(@NotNull MathContext mathContext, @NotNull Numeric elements[], boolean transposed) {
		super(mathContext);
		this.elements = elements;
		this.transposed = transposed;
		this.length = elements.length;
	}

	@NotNull
	public static Vector newInstance(@NotNull MathContext mathContext, @NotNull Numeric[] elements) {
		assert elements.length > 1;
		return new Vector(mathContext, elements);
	}

	@NotNull
	public static Vector newInstance(@NotNull MathContext mathContext, @NotNull Numeric[] elements, boolean transposed) {
		assert elements.length > 1;
		return new Vector(mathContext, elements, transposed);
	}

		@NotNull
	protected Vector newInstance() {
		return newInstance(new Numeric[length]);
	}

	@NotNull
	protected Vector newInstance(@NotNull Numeric element[]) {
		return new Vector(getMathContext(), element, transposed);
	}

	public Numeric[] elements() {
		return elements;
	}

	public Vector add(@NotNull Vector vector) {
		checkSameDimensions(vector);

		final Vector v = newInstance();

		for (int i = 0; i < length; i++) {
			v.elements[i] = elements[i].add(vector.elements[i]);
		}

		return v;
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
	public Vector subtract(@NotNull Vector that) {
		checkSameDimensions(that);

		final Vector v = newInstance();

		for (int i = 0; i < length; i++) {
			v.elements[i] = elements[i].subtract(that.elements[i]);
		}

		return v;
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
			return scalarProduct((Vector) that);
		} else if (that instanceof Matrix) {
			return ((Matrix) that).transpose().multiply(this);
		} else {
			final Vector v = newInstance();
			for (int i = 0; i < length; i++) {
				v.elements[i] = elements[i].multiply(that);
			}
			return v;
		}
	}

	@NotNull
	public Numeric divide(@NotNull Numeric that) throws NotDivisibleException {
		if (that instanceof Vector) {
			throw new ArithmeticException();
		} else if (that instanceof Matrix) {
			return multiply(that.inverse());
		} else {
			Vector v = newInstance();
			for (int i = 0; i < length; i++) {
				v.elements[i] = elements[i].divide(that);
			}
			return v;
		}
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

		for (Numeric el : elements) {
			final Real norm = el.norm();
				if ( result.more(norm) ) {
					result = norm;
				}
		}

		return result;
	}

	@NotNull
	public Numeric negate() {
		Vector v = newInstance();
		for (int i = 0; i < length; i++) v.elements[i] = elements[i].negate();
		return v;
	}

	public int signum() {
		for (int i = 0; i < length; i++) {
			int c = elements[i].signum();
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

	public Numeric magnitude2() {
		return scalarProduct(this);
	}

	@NotNull
	public Numeric scalarProduct(@NotNull Vector that) {
		checkCrossDimensions(that);

		Numeric a = ZERO();
		for (int i = 0; i < length; i++) {
			a = a.add(elements[i].multiply(that.elements[i]));
		}
		return a;
	}

	private void checkSameDimensions(@NotNull Vector that) {
		if ( this.isTransposed() != that.isTransposed() ) {
			throw new ArithmeticException(VECTOR_DIMENSIONS_MUST_AGREE);
		}

		if ( this.length != that.length ) {
			throw new ArithmeticException(VECTOR_DIMENSIONS_MUST_AGREE);
		}
	}

	private void checkCrossDimensions(@NotNull Vector that) {
		if ( this.isTransposed() == that.isTransposed() ) {
			throw new ArithmeticException(VECTOR_DIMENSIONS_MUST_AGREE);
		}

		if ( this.length != that.length ) {
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
		Vector v = newInstance();
		for (int i = 0; i < length; i++) v.elements[i] = elements[i].conjugate();
		return v;
	}

/*	public int compareTo(Vector vector) {
		return ArrayComparator.comparator.compare(element, vector.element);
	}*/

/*	public int compareTo(@NotNull Numeric that) {
		if (that instanceof Vector) {
			return compareTo((Vector) that);
		} else {
			return that.compareTo(this);
		}
	}*/

	public static Vector unity(@NotNull MathContext mc, int dimension) {
		Vector v = new Vector(mc, new Numeric[dimension]);
		for (int i = 0; i < v.length; i++) {
			if (i == 0) v.elements[i] = Real.newInstance(mc, mc.fromLong(1L));
			else v.elements[i] = Real.newInstance(mc, mc.fromLong(0L));
		}
		return v;
	}

	public String toString() {
		final StringBuilder result = new StringBuilder();

		result.append("[");

		for (int i = 0; i < length; i++) {
			result.append(elements[i]).append(i < length - 1 ? ", " : "");
		}

		result.append("]");

		return result.toString();
	}

	public boolean isTransposed() {
		return transposed;
	}

	public int getLength() {
		return length;
	}

	@NotNull
	Numeric[] getElements() {
		return elements;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Vector)) return false;

		Vector vector = (Vector) o;

		if (length != vector.length) return false;
		if (transposed != vector.transposed) return false;
		if (!Arrays.equals(elements, vector.elements)) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = elements != null ? Arrays.hashCode(elements) : 0;
		result = 31 * result + length;
		result = 31 * result + (transposed ? 1 : 0);
		return result;
	}

	@Override
	public boolean mathEquals(INumeric<Numeric> that) {
		if ( that instanceof Vector ) {
			return equals(that);
		} else if ( that instanceof Matrix) {
			return that.mathEquals(this);
		} else if ( this.length == 1 ) {
			return this.elements[0].mathEquals(that);
		} else {
			return false;
		}
	}

	@Override
	public int compareTo(Numeric o) {
		// todo serso:
		return 0;  //To change body of implemented methods use File | Settings | File Templates.
	}
}

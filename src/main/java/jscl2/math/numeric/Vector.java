package jscl2.math.numeric;

import jscl.math.NotDivisibleException;
import jscl2.MathContext;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class Vector extends AbstractNumeric {

	private static final String VECTOR_DIMENSIONS_MUST_AGREE = "Vector dimensions must agree!";

	@NotNull
	private final AbstractNumeric elements[];
	private final int length;
	private final boolean transposed;

	private Vector(@NotNull MathContext mathContext, @NotNull AbstractNumeric[] elements) {
		this(mathContext, elements, false);
	}

	private Vector(@NotNull MathContext mathContext, @NotNull AbstractNumeric elements[], boolean transposed) {
		super(mathContext);
		this.elements = elements;
		this.transposed = transposed;
		this.length = elements.length;
	}

	@NotNull
	public static Vector newInstance(@NotNull MathContext mathContext, @NotNull AbstractNumeric[] elements) {
		assert elements.length > 1;
		return new Vector(mathContext, elements);
	}

	@NotNull
	public static Vector newInstance(@NotNull MathContext mathContext, @NotNull AbstractNumeric[] elements, boolean transposed) {
		assert elements.length > 1;
		return new Vector(mathContext, elements, transposed);
	}

		@NotNull
	protected Vector newInstance() {
		return newInstance(new AbstractNumeric[length]);
	}

	@NotNull
	protected Vector newInstance(@NotNull AbstractNumeric element[]) {
		return new Vector(getMathContext(), element, transposed);
	}

	public AbstractNumeric[] elements() {
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
	public AbstractNumeric add(@NotNull AbstractNumeric that) {
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
	public AbstractNumeric subtract(@NotNull AbstractNumeric that) {
		if (that instanceof Vector) {
			return subtract((Vector) that);
		} else {
			return that.subtract(this);
		}
	}

	@NotNull
	public AbstractNumeric multiply(@NotNull AbstractNumeric that) {
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
	public AbstractNumeric divide(@NotNull AbstractNumeric that) throws NotDivisibleException {
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
	public AbstractNumeric negate() {
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

	public AbstractNumeric magnitude2() {
		return scalarProduct(this);
	}

	@NotNull
	public AbstractNumeric scalarProduct(@NotNull Vector that) {
		checkCrossDimensions(that);

		AbstractNumeric a = ZERO();
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
		Vector v = new Vector(mc, new AbstractNumeric[dimension]);
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
	AbstractNumeric[] getElements() {
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
	public boolean mathEquals(INumeric<AbstractNumeric> that) {
		if ( that instanceof Vector ) {
			return equals(that);
		} else if ( that instanceof Matrix ) {
			return that.mathEquals(this);
		} else if ( this.length == 1 ) {
			return this.elements[0].mathEquals(that);
		} else {
			return false;
		}
	}
}

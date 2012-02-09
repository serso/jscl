package jscl2.math.numeric.matrix;

import jscl.math.NotDivisibleException;
import jscl2.MathContext;
import jscl2.math.numeric.AbstractNumber;
import jscl2.math.numeric.INumeric;
import jscl2.math.numeric.Numeric;
import jscl2.math.numeric.Real;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class SparseVector extends AbstractVector implements Vector {

	private static final String VECTOR_DIMENSIONS_MUST_AGREE = "Vector dimensions must agree!";

	@NotNull
	private final AbstractNumber elements[];
	private final int length;
	private final boolean transposed;

	private SparseVector(@NotNull MathContext mathContext, @NotNull AbstractNumber[] elements) {
		this(mathContext, elements, false);
	}

	private SparseVector(@NotNull MathContext mathContext, @NotNull AbstractNumber elements[], boolean transposed) {
		super(mathContext);
		this.elements = elements;
		this.transposed = transposed;
		this.length = elements.length;
	}

	@NotNull
	public static SparseVector newInstance(@NotNull MathContext mathContext, @NotNull AbstractNumber[] elements) {
		assert elements.length > 1;
		return new SparseVector(mathContext, elements);
	}

	@NotNull
	public static SparseVector newInstance(@NotNull MathContext mathContext, @NotNull AbstractNumber[] elements, boolean transposed) {
		assert elements.length > 1;
		return new SparseVector(mathContext, elements, transposed);
	}

		@NotNull
	protected SparseVector newInstance() {
		return newInstance(new AbstractNumber[length]);
	}

	@NotNull
	protected SparseVector newInstance(@NotNull AbstractNumber element[]) {
		return new SparseVector(getMathContext(), element, transposed);
	}

	public AbstractNumber[] elements() {
		return elements;
	}

	@NotNull
	@Override
	public AbstractNumber getI(int i) {
		return elements[i];
	}

	@NotNull
	public SparseVector add(@NotNull Vector vector) {
		checkSameDimensions(vector);

		final SparseVector v = newInstance();

		for (int i = 0; i < length; i++) {
			v.elements[i] = elements[i].add(vector.getI(i));
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
	public SparseVector subtract(@NotNull Vector that) {
		checkSameDimensions(that);

		final SparseVector v = newInstance();

		for (int i = 0; i < length; i++) {
			v.elements[i] = elements[i].subtract(that.getI(i));
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
			return multiply((Vector) that);
		} else if (that instanceof Matrix) {
			return ((Matrix) that).transpose().multiply(this);
		} else {
			// todo serso: class cast
			final SparseVector v = newInstance();
			for (int i = 0; i < length; i++) {
				v.elements[i] = getI(i).multiply((AbstractNumber) that);
			}
			return v;
		}
	}

	@NotNull
	public AbstractNumber multiply(@NotNull Vector that) {
		checkCrossDimensions(that);

		AbstractNumber result = ZERO();
		for (int i = 0; i < length; i++) {
			result = result.add(elements[i].multiply(that.getI(i)));
		}
		return result;
	}

	@NotNull
	public Numeric divide(@NotNull Numeric that) throws NotDivisibleException {
		if (that instanceof Vector) {
			throw new ArithmeticException();
		} else if (that instanceof Matrix) {
			return multiply(that.inverse());
		} else {
			SparseVector v = newInstance();
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
		SparseVector v = newInstance();
		for (int i = 0; i < length; i++) {
			v.elements[i] = elements[i].negate();
		}
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

/*	public Numeric magnitude2() {
		return multiply(this);
	}*/

	private void checkSameDimensions(@NotNull Vector that) {
		if ( this.isTransposed() != that.isTransposed() ) {
			throw new ArithmeticException(VECTOR_DIMENSIONS_MUST_AGREE);
		}

		if ( this.length != that.getLength() ) {
			throw new ArithmeticException(VECTOR_DIMENSIONS_MUST_AGREE);
		}
	}

	private void checkCrossDimensions(@NotNull Vector that) {
		if ( this.isTransposed() == that.isTransposed() ) {
			throw new ArithmeticException(VECTOR_DIMENSIONS_MUST_AGREE);
		}

		if ( this.length != that.getLength() ) {
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
		SparseVector v = newInstance();
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
		SparseVector v = new SparseVector(mc, new AbstractNumber[dimension]);
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

	@Override
	public boolean isTransposed() {
		return transposed;
	}

	@Override
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

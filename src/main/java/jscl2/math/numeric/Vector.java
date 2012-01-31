package jscl2.math.numeric;

import jscl.math.NotDivisibleException;
import jscl.util.ArrayComparator;
import jscl2.MathContext;
import jscl2.math.ArithmeticUtils;
import org.jetbrains.annotations.NotNull;

public class Vector extends Numeric {

	protected final Numeric element[];
	protected final int n;

	public Vector(@NotNull MathContext mathContext, Numeric element[]) {
		super(mathContext);
		this.element = element;
		n = element.length;
	}

	public Numeric[] elements() {
		return element;
	}

	public Vector add(@NotNull Vector vector) {
		Vector v = newInstance();

		for (int i = 0; i < n; i++) {
			v.element[i] = element[i].add(vector.element[i]);
		}

		return v;
	}

	@NotNull
	public Numeric add(@NotNull Numeric that) {
		if (that instanceof Vector) {
			return add((Vector) that);
		} else {
			return ArithmeticUtils.add(this, that);
		}
	}

	public Vector subtract(Vector vector) {
		Vector v = newInstance();
		for (int i = 0; i < n; i++) {
			v.element[i] = element[i].subtract(vector.element[i]);
		}
		return v;
	}

	@NotNull
	public Numeric subtract(@NotNull Numeric that) {
		if (that instanceof Vector) {
			return subtract((Vector) that);
		} else {
			return ArithmeticUtils.subtract(this, that);
		}
	}

	@NotNull
	public Numeric multiply(@NotNull Numeric that) {
		if (that instanceof Vector) {
			return scalarProduct((Vector) that);
		} else if (that instanceof Matrix) {
			return ((Matrix) that).transpose().multiply(this);
		} else {
			Vector v = newInstance();
			for (int i = 0; i < n; i++) v.element[i] = element[i].multiply(that);
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
			for (int i = 0; i < n; i++) {
				v.element[i] = element[i].divide(that);
			}
			return v;
		}
	}

	@NotNull
	public Numeric negate() {
		Vector v = newInstance();
		for (int i = 0; i < n; i++) v.element[i] = element[i].negate();
		return v;
	}

	public int signum() {
		for (int i = 0; i < n; i++) {
			int c = element[i].signum();
			if (c < 0) {
				return -1;
			} else if (c > 0) {
				return 1;
			}
		}
		return 0;
	}

	public Numeric magnitude2() {
		return scalarProduct(this);
	}

	public Numeric scalarProduct(Vector vector) {
		Numeric a = ZERO();
		for (int i = 0; i < n; i++) {
			a = a.add(element[i].multiply(vector.element[i]));
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
		Vector v = newInstance();
		for (int i = 0; i < n; i++) v.element[i] = element[i].conjugate();
		return v;
	}

	public int compareTo(Vector vector) {
		return ArrayComparator.comparator.compare(element, vector.element);
	}

	public int compareTo(@NotNull Numeric that) {
		if (that instanceof Vector) {
			return compareTo((Vector) that);
		} else {
			return ArithmeticUtils.compare(this, that);
		}
	}

	public static Vector unity(@NotNull MathContext mc, int dimension) {
		Vector v = new Vector(mc, new Numeric[dimension]);
		for (int i = 0; i < v.n; i++) {
			if (i == 0) v.element[i] = Real.newInstance(mc, mc.toRawNumber(1L));
			else v.element[i] = Real.newInstance(mc, mc.toRawNumber(0L));
		}
		return v;
	}

	public String toString() {
		final StringBuilder result = new StringBuilder();

		result.append("[");

		for (int i = 0; i < n; i++) {
			result.append(element[i]).append(i < n - 1 ? ", " : "");
		}

		result.append("]");

		return result.toString();
	}

	@NotNull
	protected Vector newInstance() {
		return newInstance(new Numeric[n]);
	}

	@NotNull
	protected Vector newInstance(@NotNull Numeric element[]) {
		return new Vector(getMathContext(), element);
	}
}

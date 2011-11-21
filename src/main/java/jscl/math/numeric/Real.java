package jscl.math.numeric;

import org.jetbrains.annotations.NotNull;

public final class Real extends Numeric {

	public static final Real ZERO = new Real(0d);
	public static final Real ONE = new Real(1d);
	public static final Real TWO = new Real(2d);

	private final double content;

	Real(double val) {
		content = val;
	}

	public Real add(@NotNull Real that) {
		return new Real(content + that.content);
	}

	@NotNull
	public Numeric add(@NotNull Numeric that) {
		if (that instanceof Real) {
			return add((Real) that);
		} else {
			return that.valueOf(this).add(that);
		}
	}

	public Real subtract(Real that) {
		return new Real(content - that.content);
	}

	@NotNull
	public Numeric subtract(@NotNull Numeric that) {
		if (that instanceof Real) {
			return subtract((Real) that);
		} else {
			return that.valueOf(this).subtract(that);
		}
	}

	public Real multiply(Real that) {
		return new Real(content * that.content);
	}

	@NotNull
	public Numeric multiply(@NotNull Numeric that) {
		if (that instanceof Real) {
			return multiply((Real) that);
		} else {
			return that.multiply(this);
		}
	}

	public Real divide(Real that) throws ArithmeticException {
		return new Real(content / that.content);
	}

	@NotNull
	public Numeric divide(@NotNull Numeric that) throws ArithmeticException {
		if (that instanceof Real) {
			return divide((Real) that);
		} else {
			return that.valueOf(this).divide(that);
		}
	}

	@NotNull
	public Numeric negate() {
		return new Real(-content);
	}

	public int signum() {
		return signum(content);
	}

	public static int signum(double value) {
		return value == 0. ? 0 : (value < 0. ? -1 : 1);
	}

	@NotNull
	public Numeric ln() {
		return new Real(Math.log(content));
	}

	@NotNull
	public Numeric lg() {
		return new Real(Math.log10(content));
	}

	@NotNull
	public Numeric exp() {
		return new Real(Math.exp(content));
	}

	@NotNull
	public Numeric inverse() {
		return new Real(1. / content);
	}

	public Numeric pow(Real that) {
		if (signum() < 0) {
			return Complex.valueOf(content, 0).pow(that);
		} else {
			return new Real(Math.pow(content, that.content));
		}
	}

	public Numeric pow(@NotNull Numeric numeric) {
		if (numeric instanceof Real) {
			return pow((Real) numeric);
		} else {
			return numeric.valueOf(this).pow(numeric);
		}
	}

	@NotNull
	public Numeric sqrt() {
		if (signum() < 0) {
			return Complex.valueOf(0, 1).multiply(negate().sqrt());
		} else {
			return new Real(Math.sqrt(content));
		}
	}

	@NotNull
	public Numeric nThRoot(int n) {
		if (signum() < 0) {
			return n % 2 == 0 ? sqrt().nThRoot(n / 2) : negate().nThRoot(n).negate();
		} else {
			return super.nThRoot(n);
		}
	}

	public Numeric conjugate() {
		return this;
	}

	@NotNull
	public Numeric acos() {
		return new Real(radToDefault(Math.acos(content)));
	}

	@NotNull
	public Numeric asin() {
		return new Real(radToDefault(Math.asin(content)));
	}

	@NotNull
	public Numeric atan() {
		return radToDefault(atanRad());
	}

	@NotNull
	private Real atanRad() {
		return new Real(Math.atan(content));
	}

	private final static Real PI_DIV_BY_2_RAD = Real.valueOf(Math.PI).divide(TWO);

	@NotNull
	@Override
	public Numeric acot() {
		return radToDefault(PI_DIV_BY_2_RAD.subtract(atanRad()));
	}

	@NotNull
	public Numeric cos() {
		return new Real(Math.cos(defaultToRad(content)));
	}

	@NotNull
	public Numeric sin() {
		return new Real(Math.sin(defaultToRad(content)));
	}

	@NotNull
	public Numeric tan() {
		return new Real(Math.tan(defaultToRad(content)));
	}

	@NotNull
	@Override
	public Numeric cot() {
		return Real.ONE.divide(tan());
	}

	public Real valueOf(Real value) {
		return new Real(value.content);
	}

	@NotNull
	public Numeric valueOf(@NotNull Numeric numeric) {
		if (numeric instanceof Real) {
			return valueOf((Real) numeric);
		} else throw new ArithmeticException();
	}

	public double doubleValue() {
		return content;
	}

	public int compareTo(@NotNull Real that) {
		return Double.compare(this.content, that.content);
	}

	public int compareTo(Numeric numeric) {
		if (numeric instanceof Real) {
			return compareTo((Real) numeric);
		} else {
			return numeric.valueOf(this).compareTo(numeric);
		}
	}

	public static Real valueOf(double value) {
		if (value == 0d) {
			return ZERO;
		} else if ( value == 1d ) {
			return ONE;
		} else if ( value == 2d ) {
			return TWO;
		} else {
			return new Real(value);
		}
	}

	public String toString() {
		return Double.toString(content);
	}

	@NotNull
	public Complex toComplex() {
		return new Complex(this.content, 0.);
	}
}

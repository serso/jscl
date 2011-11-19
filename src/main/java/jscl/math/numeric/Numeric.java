package jscl.math.numeric;

import jscl.math.Arithmetic;

public abstract class Numeric implements Arithmetic, Comparable {
	public abstract Numeric add(Numeric numeric);

	public Numeric subtract(Numeric numeric) {
		return add(numeric.negate());
	}

	public abstract Numeric multiply(Numeric numeric);

	public abstract Numeric divide(Numeric numeric) throws ArithmeticException;

	public Arithmetic add(Arithmetic arithmetic) {
		return add((Numeric) arithmetic);
	}

	public Arithmetic subtract(Arithmetic arithmetic) {
		return subtract((Numeric) arithmetic);
	}

	public Arithmetic multiply(Arithmetic arithmetic) {
		return multiply((Numeric) arithmetic);
	}

	public Arithmetic divide(Arithmetic arithmetic) throws ArithmeticException {
		return divide((Numeric) arithmetic);
	}

	public Numeric pow(int exponent) {
		Numeric result = JsclDouble.valueOf(1);

		for (int i = 0; i < exponent; i++) {
			result = result.multiply(this);
		}

		return result;
	}

	public Numeric abs() {
		return signum() < 0 ? negate() : this;
	}

	public abstract Numeric negate();

	public abstract int signum();

	public Numeric sgn() {
		return divide(abs());
	}

	public abstract Numeric ln();

	public abstract Numeric lg();

	public abstract Numeric exp();

	public Numeric inverse() {
		return JsclDouble.valueOf(1).divide(this);
	}

	public Numeric pow(Numeric numeric) {
		if (numeric.signum() == 0) {
			return JsclDouble.valueOf(1);
		} else if (numeric.compareTo(JsclDouble.valueOf(1)) == 0) {
			return this;
		} else {
			return numeric.multiply(ln()).exp();
		}
	}

	public Numeric sqrt() {
		return nthrt(2);
	}

	public Numeric nthrt(int n) {
		return pow(JsclDouble.valueOf(1. / n));
	}

	public static Numeric root(int subscript, Numeric parameter[]) {
		throw new ArithmeticException();
	}

	public abstract Numeric conjugate();

	public Numeric acos() {
		return add(JsclDouble.valueOf(-1).add(pow(2)).sqrt()).ln().multiply(Complex.valueOf(0, 1));
	}

	public Numeric asin() {
		return multiply(Complex.valueOf(0, 1)).negate().add(JsclDouble.valueOf(1).subtract(pow(2)).sqrt()).ln().multiply(Complex.valueOf(0, 1));
	}

	public Numeric atan() {
		return Complex.valueOf(0, 1).multiply(Complex.valueOf(0, 1).add(this).divide(Complex.valueOf(0, 1).subtract(this)).ln()).divide(JsclDouble.valueOf(2));
	}

	public Numeric acot() {
		return Complex.valueOf(0, 1).multiply(Complex.valueOf(0, 1).add(this).divide(Complex.valueOf(0, 1).subtract(this)).negate().ln()).divide(JsclDouble.valueOf(2));
	}

	public Numeric cos() {
		return JsclDouble.valueOf(1).add(multiply(Complex.valueOf(0, 1)).exp().pow(2)).divide(JsclDouble.valueOf(2).multiply(multiply(Complex.valueOf(0, 1)).exp()));
	}

	public Numeric sin() {
		return Complex.valueOf(0, 1).subtract(multiply(Complex.valueOf(0, 1)).exp().pow(2).multiply(Complex.valueOf(0, 1))).divide(JsclDouble.valueOf(2).multiply(multiply(Complex.valueOf(0, 1)).exp()));
	}

	public Numeric tan() {
		return Complex.valueOf(0, 1).subtract(multiply(Complex.valueOf(0, 1)).exp().pow(2).multiply(Complex.valueOf(0, 1))).divide(JsclDouble.valueOf(1).add(multiply(Complex.valueOf(0, 1)).exp().pow(2)));
	}

	public Numeric cot() {
		return Complex.valueOf(0, 1).add(Complex.valueOf(0, 1).multiply(Complex.valueOf(0, 1).multiply(this).exp().pow(2))).divide(JsclDouble.valueOf(1).subtract(Complex.valueOf(0, 1).multiply(this).exp().pow(2))).negate();
	}

	public Numeric acosh() {
		return add(JsclDouble.valueOf(-1).add(pow(2)).sqrt()).ln();
	}

	public Numeric asinh() {
		return add(JsclDouble.valueOf(1).add(pow(2)).sqrt()).ln();
	}

	public Numeric atanh() {
		return JsclDouble.valueOf(1).add(this).divide(JsclDouble.valueOf(1).subtract(this)).ln().divide(JsclDouble.valueOf(2));
	}

	public Numeric acoth() {
		return JsclDouble.valueOf(1).add(this).divide(JsclDouble.valueOf(1).subtract(this)).negate().ln().divide(JsclDouble.valueOf(2));
	}

	public Numeric cosh() {
		return JsclDouble.valueOf(1).add(exp().pow(2)).divide(JsclDouble.valueOf(2).multiply(exp()));
	}

	public Numeric sinh() {
		return JsclDouble.valueOf(1).subtract(exp().pow(2)).divide(JsclDouble.valueOf(2).multiply(exp())).negate();
	}

	public Numeric tanh() {
		return JsclDouble.valueOf(1).subtract(exp().pow(2)).divide(JsclDouble.valueOf(1).add(exp().pow(2))).negate();
	}

	public Numeric coth() {
		return JsclDouble.valueOf(1).add(exp().pow(2)).divide(JsclDouble.valueOf(1).subtract(exp().pow(2))).negate();
	}

	public abstract Numeric valueOf(Numeric numeric);

	public abstract int compareTo(Numeric numeric);

	public int compareTo(Object o) {
		return compareTo((Numeric) o);
	}

	public boolean equals(Object obj) {
		if (obj instanceof Numeric) {
			return compareTo((Numeric) obj) == 0;
		} else return false;
	}


}

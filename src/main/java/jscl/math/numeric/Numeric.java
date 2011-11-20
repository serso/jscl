package jscl.math.numeric;

import jscl.math.Arithmetic;
import org.jetbrains.annotations.NotNull;

public abstract class Numeric implements Arithmetic<Numeric>, INumeric<Numeric>, Comparable {

	/*@NotNull
	public Numeric subtract(@NotNull Numeric numeric) {
		return add(numeric.negate());
	}*/

	@Override
	@NotNull
	public Numeric pow(int exponent) {
		Numeric result = JsclDouble.ONE;

		for (int i = 0; i < exponent; i++) {
			result = result.multiply(this);
		}

		return result;
	}

	@Override
	@NotNull
	public Numeric abs() {
		return signum() < 0 ? negate() : this;
	}

	@NotNull
	@Override
	public Numeric sgn() {
		return divide(abs());
	}

	@NotNull
	@Override
	public Numeric inverse() {
		return JsclDouble.ONE.divide(this);
	}

	public Numeric pow(Numeric numeric) {
		if (numeric.signum() == 0) {
			return JsclDouble.ONE;
		} else if (numeric.compareTo(JsclDouble.ONE) == 0) {
			return this;
		} else {
			return numeric.multiply(ln()).exp();
		}
	}

	@NotNull
	@Override
	public Numeric sqrt() {
		return nthrt(2);
	}

	@NotNull
	@Override
	public Numeric nthrt(int n) {
		return pow(JsclDouble.valueOf(1. / n));
	}

	public static Numeric root(int subscript, Numeric parameter[]) {
		throw new ArithmeticException();
	}

	public abstract Numeric conjugate();

	@NotNull
	@Override
	public Numeric acos() {
		return add(JsclDouble.valueOf(-1).add(pow(2)).sqrt()).ln().multiply(Complex.valueOf(0, 1));
	}

	@NotNull
	@Override
	public Numeric asin() {
		return multiply(Complex.valueOf(0, 1)).negate().add(JsclDouble.ONE.subtract(pow(2)).sqrt()).ln().multiply(Complex.valueOf(0, 1));
	}

	@NotNull
	@Override
	public Numeric atan() {
		return Complex.valueOf(0, 1).multiply(Complex.valueOf(0, 1).add(this).divide(Complex.valueOf(0, 1).subtract(this)).ln()).divide(JsclDouble.valueOf(2));
	}

	@NotNull
	@Override
	public Numeric acot() {
		return Complex.valueOf(0, 1).multiply(Complex.valueOf(0, 1).add(this).divide(Complex.valueOf(0, 1).subtract(this)).negate().ln()).divide(JsclDouble.valueOf(2));
	}

	@NotNull
	@Override
	public Numeric cos() {
		return JsclDouble.ONE.add(multiply(Complex.valueOf(0, 1)).exp().pow(2)).divide(JsclDouble.valueOf(2).multiply(multiply(Complex.valueOf(0, 1)).exp()));
	}

	@NotNull
	@Override
	public Numeric sin() {
		return Complex.valueOf(0, 1).subtract(multiply(Complex.valueOf(0, 1)).exp().pow(2).multiply(Complex.valueOf(0, 1))).divide(JsclDouble.valueOf(2).multiply(multiply(Complex.valueOf(0, 1)).exp()));
	}

	@NotNull
	@Override
	public Numeric tan() {
		return Complex.valueOf(0, 1).subtract(multiply(Complex.valueOf(0, 1)).exp().pow(2).multiply(Complex.valueOf(0, 1))).divide(JsclDouble.ONE.add(multiply(Complex.valueOf(0, 1)).exp().pow(2)));
	}

	@NotNull
	@Override
	public Numeric cot() {
		return Complex.valueOf(0, 1).add(Complex.valueOf(0, 1).multiply(Complex.valueOf(0, 1).multiply(this).exp().pow(2))).divide(JsclDouble.ONE.subtract(Complex.valueOf(0, 1).multiply(this).exp().pow(2))).negate();
	}

	@NotNull
	@Override
	public Numeric acosh() {
		return add(JsclDouble.valueOf(-1).add(pow(2)).sqrt()).ln();
	}

	@NotNull
	@Override
	public Numeric asinh() {
		return add(JsclDouble.ONE.add(pow(2)).sqrt()).ln();
	}

	@NotNull
	@Override
	public Numeric atanh() {
		return JsclDouble.ONE.add(this).divide(JsclDouble.ONE.subtract(this)).ln().divide(JsclDouble.valueOf(2));
	}

	@NotNull
	@Override
	public Numeric acoth() {
		return JsclDouble.ONE.add(this).divide(JsclDouble.ONE.subtract(this)).negate().ln().divide(JsclDouble.valueOf(2));
	}

	@NotNull
	@Override
	public Numeric cosh() {
		return JsclDouble.ONE.add(exp().pow(2)).divide(JsclDouble.valueOf(2).multiply(exp()));
	}

	@NotNull
	@Override
	public Numeric sinh() {
		return JsclDouble.ONE.subtract(exp().pow(2)).divide(JsclDouble.valueOf(2).multiply(exp())).negate();
	}

	@NotNull
	@Override
	public Numeric tanh() {
		return JsclDouble.ONE.subtract(exp().pow(2)).divide(JsclDouble.ONE.add(exp().pow(2))).negate();
	}

	@NotNull
	@Override
	public Numeric coth() {
		return JsclDouble.ONE.add(exp().pow(2)).divide(JsclDouble.ONE.subtract(exp().pow(2))).negate();
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

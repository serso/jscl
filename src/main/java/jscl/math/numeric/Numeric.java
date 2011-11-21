package jscl.math.numeric;

import jscl.AngleUnits;
import jscl.JsclMathEngine;
import jscl.math.Arithmetic;
import org.jetbrains.annotations.NotNull;

import static jscl.math.numeric.Complex.ONE_I;
import static jscl.math.numeric.JsclDouble.ONE;
import static jscl.math.numeric.JsclDouble.TWO;
import static jscl.math.numeric.Numeric.radToDefault;

public abstract class Numeric implements Arithmetic<Numeric>, INumeric<Numeric>, Comparable {

	/*@NotNull
	public Numeric subtract(@NotNull Numeric numeric) {
		return add(numeric.negate());
	}*/

	@Override
	@NotNull
	public Numeric pow(int exponent) {
		Numeric result = ONE;

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
		return ONE.divide(this);
	}

	public Numeric pow(Numeric numeric) {
		if (numeric.signum() == 0) {
			return ONE;
		} else if (numeric.compareTo(ONE) == 0) {
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

	protected static double defaultToRad(double value) {
		return JsclMathEngine.instance.getDefaultAngleUnits().transform(AngleUnits.rad, value);
	}

	protected  static double radToDefault(double value) {
		return AngleUnits.rad.transform(JsclMathEngine.instance.getDefaultAngleUnits(), value);
	}

	protected  static Numeric defaultToRad(@NotNull Numeric value) {
		return JsclMathEngine.instance.getDefaultAngleUnits().transform(AngleUnits.rad, value);
	}

	protected  static Numeric radToDefault(@NotNull Numeric value) {
		return AngleUnits.rad.transform(JsclMathEngine.instance.getDefaultAngleUnits(), value);
	}

	@NotNull
	@Override
	public Numeric sinh() {
		final Numeric thisRad = defaultToRad(this);

		// e = exp(2x)
		final Numeric e = thisRad.exp().pow(2);

		// e1 = 2exp(x)
		final Numeric e1 = TWO.multiply(thisRad.exp());

		// result = -[1 - exp(2x)]/[2exp(x)]
		return ONE.subtract(e).divide(e1).negate();
	}

	@NotNull
	@Override
	public Numeric cosh() {
		final Numeric thisExpRad = defaultToRad(this).exp();

		// e = exp(2x)
		final Numeric e = thisExpRad.pow(2);

		// e1 = 2exp(x)
		final Numeric e1 = TWO.multiply(thisExpRad);

		// result = [ 1 + exp(2x )] / 2exp(x)
		return ONE.add(e).divide(e1);
	}


	@NotNull
	@Override
	public Numeric tanh() {
		// e = exp(2x)
		final Numeric e = defaultToRad(this).exp().pow(2);

		// result = - (1 - exp(2x)) / (1 + exp(2x))
		return ONE.subtract(e).divide(ONE.add(e)).negate();
	}

	@NotNull
	@Override
	public Numeric coth() {
		// e = exp(2x)
		final Numeric e = defaultToRad(this).exp().pow(2);

		// result = - (1 + exp(2x)) / (1 - exp(2x))
		return ONE.add(e).divide(ONE.subtract(e)).negate();
	}

	@NotNull
	@Override
	public Numeric sin() {
		// e = exp(i)
		final Numeric e = defaultToRad(this).multiply(ONE_I).exp();
		// result = [i - i * exp(i)] / [2exp(i)]
		return ONE_I.subtract(e.multiply(ONE_I)).divide(TWO.multiply(e));
	}

	@NotNull
	@Override
	public Numeric cos() {
		// e = exp(ix)
		final Numeric e = defaultToRad(this).multiply(ONE_I).exp();
		// e1 = exp(2ix)
		final Numeric e1 = e.pow(2);

		return ONE.add(e1).divide(TWO.multiply(e));
	}

	@NotNull
	@Override
	public Numeric acos() {
		// e = √(-1 + x^2)
		final Numeric e = JsclDouble.valueOf(-1).add(this.pow(2)).sqrt();

		// result = x + i * ln[ √(-1 + x^2) ]
		return this.add(e).ln().multiply(ONE_I);
	}

	@NotNull
	@Override
	public Numeric asin() {
		// e = √(1 - x^2)
		final Numeric e = ONE.subtract(this.pow(2)).sqrt();
		// result = iln[-xi + √(1 - x^2)]
		return this.multiply(ONE_I).negate().add(e).ln().multiply(ONE_I);
	}

	@NotNull
	@Override
	public Numeric atan() {
		// e = ln[(i + x)/(i-x)]
		final Numeric e = ONE_I.add(this).divide(ONE_I.subtract(this)).ln();
		// result = iln[(i + x)/(i-x)]/2
		return ONE_I.multiply(e).divide(TWO);
	}

	@NotNull
	@Override
	public Numeric acot() {
		// e = ln[-(i + x)/(i-x)]		
		final Numeric e = ONE_I.add(this).divide(ONE_I.subtract(this)).negate().ln();
		// result = iln[-(i + x)/(i-x)]/2
		return ONE_I.multiply(e).divide(TWO);
	}

	@NotNull
	@Override
	public Numeric tan() {
		// e = exp(2xi)
		final Numeric e = this.multiply(ONE_I).exp().pow(2);

		final Numeric e1 = e.multiply(ONE_I);
		return ONE_I.subtract(e1).divide(ONE.add(e));
	}

	@NotNull
	@Override
	public Numeric cot() {
		// e = exp(2xi)
		final Numeric e = ONE_I.multiply(this).exp().pow(2);

		return ONE_I.add(ONE_I.multiply(e)).divide(ONE.subtract(e)).negate();
	}

	@NotNull
	@Override
	public Numeric acosh() {
		return add(JsclDouble.valueOf(-1).add(pow(2)).sqrt()).ln();
	}

	@NotNull
	@Override
	public Numeric asinh() {
		return add(ONE.add(pow(2)).sqrt()).ln();
	}

	@NotNull
	@Override
	public Numeric atanh() {
		return ONE.add(this).divide(ONE.subtract(this)).ln().divide(TWO);
	}

	@NotNull
	@Override
	public Numeric acoth() {
		return ONE.add(this).divide(ONE.subtract(this)).negate().ln().divide(TWO);
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

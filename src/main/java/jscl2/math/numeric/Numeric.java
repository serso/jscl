package jscl2.math.numeric;

import jscl2.AngleUnit;
import jscl2.MathContext;
import jscl2.math.RawNumber;
import org.jetbrains.annotations.NotNull;

import static jscl2.math.numeric.Real.ONE;
import static jscl2.math.numeric.Real.TWO;

public abstract class Numeric implements INumeric<Numeric>, Comparable {

	@NotNull
	private final MathContext mathContext;

	@NotNull
	protected MathContext getMathContext() {
		return mathContext;
	}

	protected Numeric(@NotNull MathContext mathContext) {
		this.mathContext = mathContext;
	}

	@NotNull
	public Numeric subtract(@NotNull Numeric numeric) {
		return add(numeric.negate());
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

	@NotNull
	@Override
	public Numeric pow(int exponent) {
		Numeric result = ONE;

		for (int i = 0; i < exponent; i++) {
			result = result.multiply(this);
		}

		return result;
	}

	public Numeric pow(@NotNull Numeric numeric) {
		if (numeric.signum() == 0) {
			return ONE;
		} else if (numeric.compareTo(ONE) == 0) {
			return this;
		} else {
			return numeric.multiply(this.ln()).exp();
		}
	}

	@NotNull
	@Override
	public Numeric sqrt() {
		return nThRoot(2);
	}

	@NotNull
	@Override
	public Numeric nThRoot(int n) {
		return pow(Real.valueOf(1. / n));
	}

	public static Numeric root(int subscript, Numeric parameter[]) {
		throw new ArithmeticException();
	}

	public abstract Numeric conjugate();

	/*
	 * ******************************************************************************************
	 * <p/>
	 * CONVERSION FUNCTIONS (rad to default angle units and vice versa)
	 * <p/>
	 * *******************************************************************************************
	 */

	@NotNull
	protected RawNumber defaultToRad(@NotNull RawNumber value) {
		return getMathContext().getAngleUnits().transform(AngleUnit.rad, value);
	}

	@NotNull
	protected RawNumber radToDefault(@NotNull RawNumber value) {
		return AngleUnit.rad.transform(this.mathContext.getAngleUnits(), value);
	}

	@NotNull
	protected Numeric defaultToRad(@NotNull Numeric value) {
		return this.mathContext.getAngleUnits().transform(AngleUnit.rad, value);
	}

	@NotNull
	protected Numeric radToDefault(@NotNull Numeric value) {
		return AngleUnit.rad.transform(this.mathContext.getAngleUnits(), value);
	}

	/*
	 * ******************************************************************************************
	 * <p/>
	 * TRIGONOMETRIC FUNCTIONS
	 * <p/>
	 * *******************************************************************************************
	 */

	@NotNull
	@Override
	public Numeric sin() {
		// e = exp(i)
		final Numeric e = defaultToRad(this).multiply(mathContext.I()).exp();
		// result = [i - i * exp(i)] / [2exp(i)]
		return mathContext.I().subtract(e.multiply(mathContext.I())).divide(TWO.multiply(e));
	}

	@NotNull
	@Override
	public Numeric cos() {
		// e = exp(ix)
		final Numeric e = defaultToRad(this).multiply(mathContext.I()).exp();
		// e1 = exp(2ix)
		final Numeric e1 = e.pow(2);

		// result = [ 1 + exp(2ix) ] / (2 *exp(ix))
		return ONE.add(e1).divide(TWO.multiply(e));
	}

	@NotNull
	@Override
	public Numeric tan() {
		// e = exp(2xi)
		final Numeric e = defaultToRad(this).multiply(mathContext.I()).exp().pow(2);

		// e1 = i * exp(2xi)
		final Numeric e1 = e.multiply(mathContext.I());

		// result = (i - i * exp(2xi)) / ( 1 + exp(2xi) )
		return mathContext.I().subtract(e1).divide(ONE.add(e));
	}

	@NotNull
	@Override
	public Numeric cot() {
		// e = exp(2xi)
		final Numeric e = mathContext.I().multiply(defaultToRad(this)).exp().pow(2);

		// result = - (i + i * exp(2ix)) / ( 1 - exp(2xi))
		return mathContext.I().add(mathContext.I().multiply(e)).divide(ONE.subtract(e)).negate();
	}

	/**
	 * ******************************************************************************************
	 * <p/>
	 * INVERSE TRIGONOMETRIC FUNCTIONS
	 * <p/>
	 * *******************************************************************************************
	 */

	@NotNull
	@Override
	public Numeric asin() {
		// e = √(1 - x^2)
		final Numeric e = ONE.subtract(this.pow(2)).sqrt();
		// result = -iln[xi + √(1 - x^2)]
		return radToDefault(this.multiply(mathContext.I()).add(e).ln().multiply(mathContext.I().negate()));
	}

	@NotNull
	@Override
	public Numeric acos() {
		// e = √(-1 + x^2) = i √(1 - x^2)
		final Numeric e = mathContext.I().multiply(Real.ONE.subtract(this.pow(2)).sqrt());

		// result = -i * ln[ x + √(-1 + x^2) ]
		return radToDefault(this.add(e).ln().multiply(mathContext.I().negate()));
	}

	@NotNull
	@Override
	public Numeric atan() {
		// e = ln[(i + x)/(i-x)]
		final Numeric e = mathContext.I().add(this).divide(mathContext.I().subtract(this)).ln();
		// result = iln[(i + x)/(i-x)]/2
		return radToDefault(mathContext.I().multiply(e).divide(TWO));
	}

	@NotNull
	@Override
	public Numeric acot() {
		// e = ln[-(i + x)/(i-x)]
		final Numeric e = mathContext.I().add(this).divide(mathContext.I().subtract(this)).negate().ln();
		// result = iln[-(i + x)/(i-x)]/2
		return radToDefault(mathContext.I().multiply(e).divide(TWO));
	}

	/**
	 * ******************************************************************************************
	 * <p/>
	 * HYPERBOLIC TRIGONOMETRIC FUNCTIONS
	 * <p/>
	 * *******************************************************************************************
	 */

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

	/**
	 * ******************************************************************************************
	 * <p/>
	 * INVERSE HYPERBOLIC TRIGONOMETRIC FUNCTIONS
	 * <p/>
	 * *******************************************************************************************
	 */

	@NotNull
	@Override
	public Numeric asinh() {
		// e = √( 1 + x ^ 2 )
		final Numeric e = ONE.add(this.pow(2)).sqrt();

		// result = ln [ x + √( 1 + x ^ 2 ) ]
		return radToDefault(this.add(e).ln());
	}

	@NotNull
	@Override
	public Numeric acosh() {
		// e = √(x ^ 2 - 1)
		final Numeric e = Real.valueOf(-1).add(this.pow(2)).sqrt();

		// result = ln( x + √(x ^ 2 - 1) )
		return radToDefault(this.add(e).ln());
	}

	@NotNull
	@Override
	public Numeric atanh() {
		// e = 1 - x
		final Numeric e = ONE.subtract(this);

		// result = ln [ ( 1 + x ) / ( 1 - x ) ] / 2
		return radToDefault(ONE.add(this).divide(e).ln().divide(TWO));
	}

	@NotNull
	@Override
	public Numeric acoth() {
		// e = 1 - x
		final Numeric e = ONE.subtract(this);

		// result = ln [ - (1 + x) / (1 - x) ] / 2
		return radToDefault(ONE.add(this).divide(e).negate().ln().divide(TWO));
	}

	public abstract int compareTo(@NotNull Numeric numeric);

	public int compareTo(Object o) {
		return compareTo((Numeric) o);
	}

	public boolean equals(Object obj) {
		return obj instanceof Numeric && compareTo((Numeric) obj) == 0;
	}

	@NotNull
	protected String toString(@NotNull final RawNumber value) {
		return this.mathContext.format(value, this.mathContext.getNumeralBase());
	}
}

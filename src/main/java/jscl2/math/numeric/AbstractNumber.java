package jscl2.math.numeric;

import jscl2.AngleUnit;
import jscl2.MathContext;
import jscl2.math.RawNumber;
import org.jetbrains.annotations.NotNull;

/**
 * User: serso
 * Date: 2/1/12
 * Time: 11:18 PM
 */
public abstract class AbstractNumber extends Numeric {

	protected AbstractNumber(@NotNull MathContext mathContext) {
		super(mathContext);
	}

	public Numeric pow(@NotNull Numeric numeric) {
		if (numeric.signum() == 0) {
			return ONE();
		} else if (numeric.mathEquals(ONE())) {
			return this;
		} else {
			return numeric.multiply(this.ln()).exp();
		}
	}

	@NotNull
	@Override
	public Numeric sgn() {
		return divide(abs());
	}

	@NotNull
	@Override
	public Numeric sqrt() {
		return nThRoot(2);
	}

	@NotNull
	@Override
	public Numeric nThRoot(int n) {
		return pow(ONE().divide(Real.newInstance(getMathContext(), mc.fromLong(n))));
	}

	@NotNull
	@Override
	public Real norm() {
		return abs();
	}

	/*
			 * ******************************************************************************************
			 * <p/>
			 * CONVERSION FUNCTIONS (rad to default angle units and vice versa)
			 * <p/>
			 * *******************************************************************************************
			 */

	@NotNull
	protected RawNumber defaultToRad(@NotNull RawNumber value) {
		return getMathContext().getAngleUnits().transform(getMathContext(), AngleUnit.rad, value);
	}

	@NotNull
	protected RawNumber radToDefault(@NotNull RawNumber value) {
		return AngleUnit.rad.transform(getMathContext(), this.mc.getAngleUnits(), value);
	}

	@NotNull
	protected Numeric defaultToRad(@NotNull Numeric value) {
		return this.mc.getAngleUnits().transform(mc, AngleUnit.rad, value);
	}

	@NotNull
	protected Numeric radToDefault(@NotNull Numeric value) {
		return AngleUnit.rad.transform(mc, this.mc.getAngleUnits(), value);
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
		// e = exp(i
		final Numeric e = defaultToRad(this).multiply(I()).exp();
		// result = [i - i * exp(i)] / [2exp(i)]
		return I().subtract(e.multiply(I())).divide(TWO().multiply(e));
	}

	@NotNull
	@Override
	public Numeric cos() {
		// e = exp(ix)
		final Numeric e = defaultToRad(this).multiply(I()).exp();
		// e1 = exp(2ix)
		final Numeric e1 = e.pow(2);

		// result = [ 1 + exp(2ix) ] / (2 *exp(ix))
		return ONE().add(e1).divide(TWO().multiply(e));
	}

	@NotNull
	@Override
	public Numeric tan() {
		// e = exp(2xi)
		final Numeric e = defaultToRad(this).multiply(I()).exp().pow(2);

		// e1 = i * exp(2xi)
		final Numeric e1 = e.multiply(I());

		// result = (i - i * exp(2xi)) / ( 1 + exp(2xi) )
		return I().subtract(e1).divide(ONE().add(e));
	}

	@NotNull
	@Override
	public Numeric cot() {
		// e = exp(2xi)
		final Numeric e = I().multiply(defaultToRad(this)).exp().pow(2);

		// result = - (i + i * exp(2ix)) / ( 1 - exp(2xi))
		return I().add(I().multiply(e)).divide(ONE().subtract(e)).negate();
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
		final Numeric e = ONE().subtract(this.pow(2)).sqrt();
		// result = -iln[xi + √(1 - x^2)]
		return radToDefault(this.multiply(I()).add(e).ln().multiply(I().negate()));
	}

	@NotNull
	@Override
	public Numeric acos() {
		// e = √(-1 + x^2) = i √(1 - x^2)
		final Numeric e = I().multiply(ONE().subtract(this.pow(2)).sqrt());

		// result = -i * ln[ x + √(-1 + x^2) ]
		return radToDefault(this.add(e).ln().multiply(I().negate()));
	}

	@NotNull
	@Override
	public Numeric atan() {
		// e = ln[(i + x)/(i-x)]
		final Numeric e = I().add(this).divide(I().subtract(this)).ln();
		// result = iln[(i + x)/(i-x)]/2
		return radToDefault(I().multiply(e).divide(TWO()));
	}

	@NotNull
	@Override
	public Numeric acot() {
		// e = ln[-(i + x)/(i-x)]
		final Numeric e = I().add(this).divide(I().subtract(this)).negate().ln();
		// result = iln[-(i + x)/(i-x)]/2
		return radToDefault(I().multiply(e).divide(TWO()));
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
		final Numeric e1 = TWO().multiply(thisRad.exp());

		// result = -[1 - exp(2x)]/[2exp(x)]
		return ONE().subtract(e).divide(e1).negate();
	}

	@NotNull
	@Override
	public Numeric cosh() {
		final Numeric thisExpRad = defaultToRad(this).exp();

		// e = exp(2x)
		final Numeric e = thisExpRad.pow(2);

		// e1 = 2exp(x)
		final Numeric e1 = TWO().multiply(thisExpRad);

		// result = [ 1 + exp(2x )] / 2exp(x)
		return ONE().add(e).divide(e1);
	}


	@NotNull
	@Override
	public Numeric tanh() {
		// e = exp(2x)
		final Numeric e = defaultToRad(this).exp().pow(2);

		// result = - (1 - exp(2x)) / (1 + exp(2x))
		return ONE().subtract(e).divide(ONE().add(e)).negate();
	}

	@NotNull
	@Override
	public Numeric coth() {
		// e = exp(2x)
		final Numeric e = defaultToRad(this).exp().pow(2);

		// result = - (1 + exp(2x)) / (1 - exp(2x))
		return ONE().add(e).divide(ONE().subtract(e)).negate();
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
		final Numeric e = ONE().add(this.pow(2)).sqrt();

		// result = ln [ x + √( 1 + x ^ 2 ) ]
		return radToDefault(this.add(e).ln());
	}

	@NotNull
	@Override
	public Numeric acosh() {
		// e = √(x ^ 2 - 1)
		final Numeric e = ONE().negate().add(this.pow(2)).sqrt();

		// result = ln( x + √(x ^ 2 - 1) )
		return radToDefault(this.add(e).ln());
	}

	@NotNull
	@Override
	public Numeric atanh() {
		// e = 1 - x
		final Numeric e = ONE().subtract(this);

		// result = ln [ ( 1 + x ) / ( 1 - x ) ] / 2
		return radToDefault(ONE().add(this).divide(e).ln().divide(TWO()));
	}

	@NotNull
	@Override
	public Numeric acoth() {
		// e = 1 - x
		final Numeric e = ONE().subtract(this);

		// result = ln [ - (1 + x) / (1 - x) ] / 2
		return radToDefault(ONE().add(this).divide(e).negate().ln().divide(TWO()));
	}
}

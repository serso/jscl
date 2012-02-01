package jscl2.math.numeric;

import jscl2.MathContext;
import org.jetbrains.annotations.NotNull;

/**
 * User: serso
 * Date: 2/1/12
 * Time: 11:18 PM
 */
public abstract class AbstractNumber extends AbstractNumeric {

	protected AbstractNumber(@NotNull MathContext mathContext) {
		super(mathContext);
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
	public AbstractNumeric sin() {
		// e = exp(i
		final AbstractNumeric e = defaultToRad(this).multiply(I()).exp();
		// result = [i - i * exp(i)] / [2exp(i)]
		return I().subtract(e.multiply(I())).divide(TWO().multiply(e));
	}

	@NotNull
	@Override
	public AbstractNumeric cos() {
		// e = exp(ix)
		final AbstractNumeric e = defaultToRad(this).multiply(I()).exp();
		// e1 = exp(2ix)
		final AbstractNumeric e1 = e.pow(2);

		// result = [ 1 + exp(2ix) ] / (2 *exp(ix))
		return ONE().add(e1).divide(TWO().multiply(e));
	}

	@NotNull
	@Override
	public AbstractNumeric tan() {
		// e = exp(2xi)
		final AbstractNumeric e = defaultToRad(this).multiply(I()).exp().pow(2);

		// e1 = i * exp(2xi)
		final AbstractNumeric e1 = e.multiply(I());

		// result = (i - i * exp(2xi)) / ( 1 + exp(2xi) )
		return I().subtract(e1).divide(ONE().add(e));
	}

	@NotNull
	@Override
	public AbstractNumeric cot() {
		// e = exp(2xi)
		final AbstractNumeric e = I().multiply(defaultToRad(this)).exp().pow(2);

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
	public AbstractNumeric asin() {
		// e = √(1 - x^2)
		final AbstractNumeric e = ONE().subtract(this.pow(2)).sqrt();
		// result = -iln[xi + √(1 - x^2)]
		return radToDefault(this.multiply(I()).add(e).ln().multiply(I().negate()));
	}

	@NotNull
	@Override
	public AbstractNumeric acos() {
		// e = √(-1 + x^2) = i √(1 - x^2)
		final AbstractNumeric e = I().multiply(ONE().subtract(this.pow(2)).sqrt());

		// result = -i * ln[ x + √(-1 + x^2) ]
		return radToDefault(this.add(e).ln().multiply(I().negate()));
	}

	@NotNull
	@Override
	public AbstractNumeric atan() {
		// e = ln[(i + x)/(i-x)]
		final AbstractNumeric e = I().add(this).divide(I().subtract(this)).ln();
		// result = iln[(i + x)/(i-x)]/2
		return radToDefault(I().multiply(e).divide(TWO()));
	}

	@NotNull
	@Override
	public AbstractNumeric acot() {
		// e = ln[-(i + x)/(i-x)]
		final AbstractNumeric e = I().add(this).divide(I().subtract(this)).negate().ln();
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
	public AbstractNumeric sinh() {
		final AbstractNumeric thisRad = defaultToRad(this);

		// e = exp(2x)
		final AbstractNumeric e = thisRad.exp().pow(2);

		// e1 = 2exp(x)
		final AbstractNumeric e1 = TWO().multiply(thisRad.exp());

		// result = -[1 - exp(2x)]/[2exp(x)]
		return ONE().subtract(e).divide(e1).negate();
	}

	@NotNull
	@Override
	public AbstractNumeric cosh() {
		final AbstractNumeric thisExpRad = defaultToRad(this).exp();

		// e = exp(2x)
		final AbstractNumeric e = thisExpRad.pow(2);

		// e1 = 2exp(x)
		final AbstractNumeric e1 = TWO().multiply(thisExpRad);

		// result = [ 1 + exp(2x )] / 2exp(x)
		return ONE().add(e).divide(e1);
	}


	@NotNull
	@Override
	public AbstractNumeric tanh() {
		// e = exp(2x)
		final AbstractNumeric e = defaultToRad(this).exp().pow(2);

		// result = - (1 - exp(2x)) / (1 + exp(2x))
		return ONE().subtract(e).divide(ONE().add(e)).negate();
	}

	@NotNull
	@Override
	public AbstractNumeric coth() {
		// e = exp(2x)
		final AbstractNumeric e = defaultToRad(this).exp().pow(2);

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
	public AbstractNumeric asinh() {
		// e = √( 1 + x ^ 2 )
		final AbstractNumeric e = ONE().add(this.pow(2)).sqrt();

		// result = ln [ x + √( 1 + x ^ 2 ) ]
		return radToDefault(this.add(e).ln());
	}

	@NotNull
	@Override
	public AbstractNumeric acosh() {
		// e = √(x ^ 2 - 1)
		final AbstractNumeric e = ONE().negate().add(this.pow(2)).sqrt();

		// result = ln( x + √(x ^ 2 - 1) )
		return radToDefault(this.add(e).ln());
	}

	@NotNull
	@Override
	public AbstractNumeric atanh() {
		// e = 1 - x
		final AbstractNumeric e = ONE().subtract(this);

		// result = ln [ ( 1 + x ) / ( 1 - x ) ] / 2
		return radToDefault(ONE().add(this).divide(e).ln().divide(TWO()));
	}

	@NotNull
	@Override
	public AbstractNumeric acoth() {
		// e = 1 - x
		final AbstractNumeric e = ONE().subtract(this);

		// result = ln [ - (1 + x) / (1 - x) ] / 2
		return radToDefault(ONE().add(this).divide(e).negate().ln().divide(TWO()));
	}
}

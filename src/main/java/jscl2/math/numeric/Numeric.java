package jscl2.math.numeric;

import jscl2.JsclMathContext;
import jscl2.math.RawNumber;
import org.jetbrains.annotations.NotNull;

public abstract class Numeric implements INumeric<Numeric> {

	@NotNull
	protected final JsclMathContext mc;

	protected Numeric(@NotNull JsclMathContext mc) {
		this.mc = mc;
	}

	@NotNull
	protected JsclMathContext getMathContext() {
		return mc;
	}

	@NotNull
	protected Real ZERO() {
		return Real.ZERO(mc);
	}

	@NotNull
	protected Real ONE() {
		return Real.ONE(mc);
	}

	@NotNull
	protected Real TWO() {
		return Real.TWO(mc);
	}

	@NotNull
	protected Complex I() {
		return Complex.I(mc);
	}

	@NotNull
	@Override
	public Numeric pow(int exponent) {
		Numeric result = ONE();

		for (int i = 0; i < exponent; i++) {
			result = result.multiply(this);
		}

		return result;
	}

	@NotNull
	protected String toString(@NotNull final RawNumber value) {
		return this.mc.format(value);
	}

	@Override
	public boolean more(@NotNull Numeric that) {
		return this.compareTo(that) > 0;
	}

	@Override
	public boolean moreOrEquals(@NotNull Numeric that) {
		return this.compareTo(that) >= 0;
	}

	@Override
	public boolean less(@NotNull Numeric that) {
		return this.compareTo(that) < 0;
	}

	@Override
	public boolean lessOrEquals(@NotNull Numeric that) {
		return this.compareTo(that) <= 0;
	}
}

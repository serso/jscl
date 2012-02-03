package jscl2.math.numeric;

import jscl2.AngleUnit;
import jscl2.MathContext;
import jscl2.math.RawNumber;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractNumeric implements INumeric<AbstractNumeric> {

	@NotNull
	private final MathContext mathContext;

	@NotNull
	protected MathContext getMathContext() {
		return mathContext;
	}

	protected AbstractNumeric(@NotNull MathContext mathContext) {
		this.mathContext = mathContext;
	}
	
	@NotNull
	protected Real ZERO() {
		return Real.ZERO(getMathContext());
	}
	
	@NotNull
	protected Real ONE() {
		return Real.ONE(getMathContext());
	}
	
	@NotNull
	protected Real TWO() {
		return Real.TWO(getMathContext());
	}

	@NotNull
	protected Complex I() {
		return Complex.I(getMathContext());
	}

	@NotNull
	@Override
	public AbstractNumeric inverse() {
		return ONE().divide(this);
	}

	@NotNull
	@Override
	public AbstractNumeric pow(int exponent) {
		AbstractNumeric result = ONE();

		for (int i = 0; i < exponent; i++) {
			result = result.multiply(this);
		}

		return result;
	}

	public AbstractNumeric pow(@NotNull AbstractNumeric that) {
		if (that.signum() == 0) {
			return ONE();
		} else if (that.mathEquals(ONE())) {
			return this;
		} else {
			return that.multiply(this.ln()).exp();
		}
	}

	@NotNull
	@Override
	public AbstractNumeric sqrt() {
		return nThRoot(2);
	}

	@NotNull
	@Override
	public AbstractNumeric nThRoot(int n) {
		return pow(ONE().divide(Real.newInstance(getMathContext(), getMathContext().fromLong(n))));
	}

	public static AbstractNumeric root(int subscript, AbstractNumeric parameter[]) {
		throw new ArithmeticException();
	}

	public abstract AbstractNumeric conjugate();

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
		return AngleUnit.rad.transform(getMathContext(), this.mathContext.getAngleUnits(), value);
	}

	@NotNull
	protected AbstractNumeric defaultToRad(@NotNull AbstractNumeric value) {
		return this.mathContext.getAngleUnits().transform(mathContext, AngleUnit.rad, value);
	}

	@NotNull
	protected AbstractNumeric radToDefault(@NotNull AbstractNumeric value) {
		return AngleUnit.rad.transform(mathContext, this.mathContext.getAngleUnits(), value);
	}


	/*public abstract int compareTo(@NotNull Numeric numeric);*/

	/*public boolean equals(Object that) {
		return that instanceof Numeric && compareTo((Numeric) that) == 0;
	}*/

	@NotNull
	protected String toString(@NotNull final RawNumber value) {
		return this.mathContext.format(value, this.mathContext.getNumeralBase());
	}
}

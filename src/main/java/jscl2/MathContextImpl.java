package jscl2;

import jscl2.math.RawNumber;
import jscl2.math.RawNumberType;
import jscl2.math.numeric.Complex;
import jscl2.math.numeric.Real;
import org.jetbrains.annotations.NotNull;

/**
 * User: serso
 * Date: 1/31/12
 * Time: 11:14 AM
 */
public class MathContextImpl implements MathContext {

	@NotNull
	private final AngleUnit angleUnit;

	@NotNull
	private final NumeralBase numeralBase;

	@NotNull
	private final RawNumberType rawNumberType;

	private MathContextImpl(@NotNull AngleUnit angleUnit,
							@NotNull NumeralBase numeralBase,
							@NotNull RawNumberType rawNumberType) {
		this.angleUnit = angleUnit;
		this.numeralBase = numeralBase;
		this.rawNumberType = rawNumberType;
	}

	public static MathContext defaultInstance() {
		return new MathContextImpl(AngleUnit.deg, NumeralBase.dec, RawNumberType.DOUBLE);
	}

	public static MathContext newInstance(@NotNull final AngleUnit au,
										  @NotNull final NumeralBase nb,
										  @NotNull final RawNumberType rnt) {
		return new MathContextImpl(au, nb, rnt);
	}

	@NotNull
	@Override
	public AngleUnit getAngleUnits() {
		return angleUnit;
	}

	@NotNull
	@Override
	public NumeralBase getNumeralBase() {
		return numeralBase;
	}

	@NotNull
	@Override
	public String format(@NotNull RawNumber value) throws NumeralBaseException {
		// todo serso:
		return String.valueOf(value.asDouble());
	}

	@NotNull
	@Override
	public String format(@NotNull RawNumber value, @NotNull NumeralBase nb) throws NumeralBaseException {
		// todo serso:
		return String.valueOf(value.asDouble());
	}

	@NotNull
	@Override
	public Real newReal(long value) {
		return Real.newInstance(this, rawNumberType.toRawNumber(value));
	}

	@NotNull
	@Override
	public RawNumber getPI() {
		return this.rawNumberType.getPI();
	}

	@NotNull
	@Override
	public RawNumber toRawNumber(double value) {
		return this.rawNumberType.toRawNumber(value);
	}

	@NotNull
	@Override
	public RawNumber toRawNumber(long value) {
		return this.rawNumberType.toRawNumber(value);
	}

	@NotNull
	@Override
	public Real newReal(double value) {
		return Real.newInstance(this, rawNumberType.toRawNumber(value));
	}

	@NotNull
	@Override
	public Complex newComplex(long real, long imaginary) {
		return Complex.newInstance(this, rawNumberType.toRawNumber(real), rawNumberType.toRawNumber(imaginary));
	}
}

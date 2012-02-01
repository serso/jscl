package jscl2;

import jscl2.math.RawNumber;
import jscl2.math.RawNumberCreator;
import jscl2.math.numeric.Complex;
import jscl2.math.numeric.Real;
import org.jetbrains.annotations.NotNull;

/**
 * User: serso
 * Date: 1/30/12
 * Time: 2:01 PM
 */
public interface MathContext extends RawNumberCreator {

	@NotNull
	AngleUnit getAngleUnits();

	@NotNull
	NumeralBase getNumeralBase();

	@NotNull
	String format(@NotNull RawNumber value) throws NumeralBaseException;

	@NotNull
	String format(@NotNull RawNumber value, @NotNull NumeralBase nb) throws NumeralBaseException;

	@NotNull
	Real newReal(long value);

	@NotNull
	Real newReal(double value);

	@NotNull
	Complex newComplex(long real, long imaginary);
}

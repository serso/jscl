package jscl2;

import jscl2.NumeralBaseException;
import jscl2.AngleUnit;
import jscl2.NumeralBase;
import jscl2.math.RawNumber;
import jscl2.math.numeric.Complex;
import org.jetbrains.annotations.NotNull;

/**
 * User: serso
 * Date: 1/30/12
 * Time: 2:01 PM
 */
public interface MathContext {

	@NotNull
	RawNumber get1();

	@NotNull
	RawNumber get2();

	@NotNull
	RawNumber get0();

	@NotNull
	Complex I();

	@NotNull
	AngleUnit getAngleUnits();

	@NotNull
	NumeralBase getNumeralBase();

	@NotNull
	String format(@NotNull RawNumber value) throws NumeralBaseException;

	@NotNull
	String format(@NotNull RawNumber value, @NotNull NumeralBase nb) throws NumeralBaseException;

	@NotNull
	RawNumber getPI();
}

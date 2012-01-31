package jscl2.math;

import jscl2.math.numeric.Complex;
import org.jetbrains.annotations.NotNull;

/**
 * User: serso
 * Date: 1/31/12
 * Time: 11:14 AM
 */
public interface RawNumberHelper {

	@NotNull
	RawNumber getPI();

	@NotNull
	RawNumber toRawNumber(double value);

	@NotNull
	RawNumber toRawNumber(long value);

}

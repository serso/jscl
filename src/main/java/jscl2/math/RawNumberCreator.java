package jscl2.math;

import org.jetbrains.annotations.NotNull;

/**
 * User: serso
 * Date: 1/31/12
 * Time: 11:14 AM
 */
public interface RawNumberCreator {

	@NotNull
	RawNumber getPI();

	@NotNull
	RawNumber fromDouble(double value);

	@NotNull
	RawNumber fromLong(long value);

	@NotNull
	RawNumber ZERO();

	@NotNull
	RawNumber ONE();
}

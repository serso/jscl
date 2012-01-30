package jscl2.math;

import jscl.math.NotDivisibleException;
import jscl.math.function.Comparison;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

/**
 * User: serso
 * Date: 1/30/12
 * Time: 1:41 PM
 */
public interface RawNumber extends Arithmetic<RawNumber>, Comparable<RawNumber> {

	@NotNull
	RawNumber addDouble(@NotNull Double that);

	@NotNull
	RawNumber subtractDouble(@NotNull Double that);

	@NotNull
	RawNumber multiplyDouble(@NotNull Double that);

	@NotNull
	RawNumber divideDouble(@NotNull Double that) throws NotDivisibleException;

	@NotNull
	RawNumber negate();

	int signum();

	@NotNull
	RawNumber sqrt();

	@NotNull
	RawNumber atan2(@NotNull RawNumber that);

	@NotNull
	RawNumber log();

	@NotNull
	RawNumber log10();

	@NotNull
	RawNumber sin();

	@NotNull
	RawNumber cos();

	@NotNull
	RawNumber exp();

	boolean isZero();

	boolean isOne();

	boolean positive();

	boolean negative();

	@NotNull
	RawNumber pow(@NotNull RawNumber that);

	@NotNull
	RawNumber acos();

	boolean isNaN();

	@NotNull
	RawNumber asin();

	@NotNull
	RawNumber atan();

	@NotNull
	RawNumber tan();

	double asDouble();

	@NotNull
	BigDecimal asBigDecimal();
}

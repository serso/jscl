package jscl2.math;

import jscl.math.NotDivisibleException;
import org.jetbrains.annotations.NotNull;

/**
 * User: serso
 * Date: 1/30/12
 * Time: 3:04 PM
 */
public class DoubleRawNumber implements RawNumber {

	@NotNull
	private final Double value;

	DoubleRawNumber(@NotNull Double value) {
		this.value = value;
	}

	@NotNull
	public static DoubleRawNumber newInstance(@NotNull Double value) {
		return new DoubleRawNumber(value);
	}

	@NotNull
	@Override
	public RawNumber addDouble(@NotNull Double that) {
		return newInstance(this.value + that);
	}

	@NotNull
	@Override
	public RawNumber subtractDouble(@NotNull Double that) {
		return newInstance(this.value - that);
	}

	@NotNull
	@Override
	public RawNumber multiplyDouble(@NotNull Double that) {
		return newInstance(this.value * that);
	}

	@NotNull
	@Override
	public RawNumber divideDouble(@NotNull Double that) throws NotDivisibleException {
		return newInstance(this.value / that);
	}

	@NotNull
	@Override
	public RawNumber negate() {
		return newInstance(-this.value);
	}

	@Override
	public int signum() {
		return this.value > 0d ? 1 : (this.value < 0d ? -1 : 0);
	}

	@NotNull
	@Override
	public RawNumber sqrt() {
		return newInstance(Math.sqrt(this.value));
	}

	@NotNull
	@Override
	public RawNumber atan2(@NotNull RawNumber that) {
		return newInstance(Math.atan2(this.value, that.asDouble()));
	}

	@NotNull
	@Override
	public RawNumber log() {
		return newInstance(Math.log(this.value));
	}

	@NotNull
	@Override
	public RawNumber log10() {
		return newInstance(Math.log10(this.value));
	}

	@NotNull
	@Override
	public RawNumber sin() {
		return newInstance(Math.sin(this.value));
	}

	@NotNull
	@Override
	public RawNumber cos() {
		return newInstance(Math.cos(this.value));
	}

	@NotNull
	@Override
	public RawNumber exp() {
		return newInstance(Math.exp(this.value));
	}

	@Override
	public boolean isZero() {
		return signum() == 0;
	}

	@Override
	public boolean isOne() {
		return this.value == 1d;
	}

	@Override
	public boolean positive() {
		return signum() > 0;
	}

	@Override
	public boolean negative() {
		return signum() < 0;
	}

	@NotNull
	@Override
	public RawNumber pow(@NotNull RawNumber that) {
		return newInstance(Math.pow(this.value, that.asDouble()));
	}

	@NotNull
	@Override
	public RawNumber acos() {
		return newInstance(Math.acos(this.value));
	}

	@Override
	public boolean isNaN() {
		return Double.isNaN(this.value);
	}

	@NotNull
	@Override
	public RawNumber asin() {
		return newInstance(Math.asin(this.value));
	}

	@NotNull
	@Override
	public RawNumber atan() {
		return newInstance(Math.atan(this.value));
	}

	@NotNull
	@Override
	public RawNumber tan() {
		return newInstance(Math.tan(this.value));
	}

	@NotNull
	@Override
	public RawNumber add(@NotNull RawNumber that) {
		return newInstance(this.value + that.asDouble());
	}

	@NotNull
	@Override
	public RawNumber subtract(@NotNull RawNumber that) {
		return newInstance(this.value - that.asDouble());
	}

	@NotNull
	@Override
	public RawNumber multiply(@NotNull RawNumber that) {
		return newInstance(this.value * that.asDouble());
	}

	@NotNull
	@Override
	public RawNumber divide(@NotNull RawNumber that) throws NotDivisibleException {
		return newInstance(this.value / that.asDouble());
	}

	@Override
	public int compareTo(@NotNull RawNumber that) {
		// todo serso:
		return 0;
	}

	@Override
	public double asDouble() {
		return value;
	}
}

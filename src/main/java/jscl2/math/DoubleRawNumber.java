package jscl2.math;

import jscl.math.NotDivisibleException;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

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
	public DoubleRawNumber negate() {
		return newInstance(-this.value);
	}

	@Override
	public int signum() {
		return this.value > 0d ? 1 : (this.value < 0d ? -1 : 0);
	}

	@NotNull
	@Override
	public DoubleRawNumber sqrt() {
		return newInstance(Math.sqrt(this.value));
	}

	@NotNull
	@Override
	public DoubleRawNumber atan2(@NotNull RawNumber that) {
		return newInstance(Math.atan2(this.value, that.asDouble()));
	}

	@NotNull
	@Override
	public DoubleRawNumber log() {
		return newInstance(Math.log(this.value));
	}

	@NotNull
	@Override
	public DoubleRawNumber log10() {
		return newInstance(Math.log10(this.value));
	}

	@NotNull
	@Override
	public DoubleRawNumber sin() {
		return newInstance(Math.sin(this.value));
	}

	@NotNull
	@Override
	public DoubleRawNumber cos() {
		return newInstance(Math.cos(this.value));
	}

	@NotNull
	@Override
	public DoubleRawNumber exp() {
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
	public DoubleRawNumber pow(@NotNull RawNumber that) {
		return newInstance(Math.pow(this.value, that.asDouble()));
	}

	@NotNull
	@Override
	public DoubleRawNumber acos() {
		return newInstance(Math.acos(this.value));
	}

	@Override
	public boolean isNaN() {
		return Double.isNaN(this.value);
	}

	@NotNull
	@Override
	public DoubleRawNumber asin() {
		return newInstance(Math.asin(this.value));
	}

	@NotNull
	@Override
	public DoubleRawNumber atan() {
		return newInstance(Math.atan(this.value));
	}

	@NotNull
	@Override
	public DoubleRawNumber tan() {
		return newInstance(Math.tan(this.value));
	}

	@NotNull
	@Override
	public DoubleRawNumber add(@NotNull RawNumber that) {
		return newInstance(this.value + that.asDouble());
	}

	@NotNull
	@Override
	public DoubleRawNumber subtract(@NotNull RawNumber that) {
		return newInstance(this.value - that.asDouble());
	}

	@NotNull
	@Override
	public DoubleRawNumber multiply(@NotNull RawNumber that) {
		return newInstance(this.value * that.asDouble());
	}

	@NotNull
	@Override
	public DoubleRawNumber divide(@NotNull RawNumber that) throws NotDivisibleException {
		return newInstance(this.value / that.asDouble());
	}

	@Override
	public int compareTo(@NotNull RawNumber that) {
		return this.value.compareTo(that.asDouble());
	}

	@Override
	public double asDouble() {
		return value;
	}

	@NotNull
	@Override
	public BigDecimal asBigDecimal() {
		return BigDecimal.valueOf(value);
	}
}

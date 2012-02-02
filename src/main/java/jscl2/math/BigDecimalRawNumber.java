package jscl2.math;

import jscl.math.NotDivisibleException;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

/**
 * User: serso
 * Date: 1/30/12
 * Time: 3:13 PM
 */
public class BigDecimalRawNumber implements RawNumber {

	@NotNull
	private final BigDecimal value;

	BigDecimalRawNumber(@NotNull BigDecimal value) {
		this.value = value;
	}

	@NotNull
	public static BigDecimalRawNumber newInstance(@NotNull BigDecimal value) {
		return new BigDecimalRawNumber(value);
	}

	@NotNull
	public static BigDecimalRawNumber newInstance(long value) {
		return new BigDecimalRawNumber(BigDecimal.valueOf(value));
	}

	@NotNull
	public static BigDecimalRawNumber newInstance(double value) {
		return new BigDecimalRawNumber(BigDecimal.valueOf(value));
	}

	@NotNull
	public static BigDecimalRawNumber newInstance(@NotNull DoubleRawNumber value) {
		return new BigDecimalRawNumber(BigDecimal.valueOf(value.asDouble()));
	}

	@NotNull
	private BigDecimal toBigDecimal(double value) {
		return BigDecimal.valueOf(value);
	}

	@NotNull
	private BigDecimal toBigDecimal(long value) {
		return BigDecimal.valueOf(value);
	}

	@NotNull
	@Override
	public RawNumber negate() {
		return newInstance(this.value.negate());
	}

	@Override
	public int signum() {
		return this.value.signum();
	}

	@NotNull
	@Override
	public RawNumber sqrt() {
		return newInstance(DoubleRawNumber.newInstance(this.value.doubleValue()).sqrt());
	}

	@NotNull
	@Override
	public RawNumber atan2(@NotNull RawNumber that) {
		return newInstance(DoubleRawNumber.newInstance(this.value.doubleValue()).atan2(that));
	}

	@NotNull
	@Override
	public RawNumber log() {
		return newInstance(DoubleRawNumber.newInstance(this.value.doubleValue()).log());
	}

	@NotNull
	@Override
	public RawNumber log10() {
		return newInstance(DoubleRawNumber.newInstance(this.value.doubleValue()).log10());
	}

	@NotNull
	@Override
	public RawNumber sin() {
		return newInstance(DoubleRawNumber.newInstance(this.value.doubleValue()).sin());
	}

	@NotNull
	@Override
	public RawNumber cos() {
		return newInstance(DoubleRawNumber.newInstance(this.value.doubleValue()).cos());
	}

	@NotNull
	@Override
	public RawNumber exp() {
		return newInstance(DoubleRawNumber.newInstance(this.value.doubleValue()).exp());
	}

	@Override
	public boolean isZero() {
		return BigDecimal.ZERO.equals(this.value);
	}

	@Override
	public boolean isOne() {
		return BigDecimal.ONE.equals(this.value);
	}

	@Override
	public boolean positive() {
		return this.value.signum() > 0;
	}

	@Override
	public boolean negative() {
		return this.value.signum() < 0;
	}

	@NotNull
	@Override
	public RawNumber pow(@NotNull RawNumber that) {
		return newInstance(DoubleRawNumber.newInstance(this.value.doubleValue()).pow(that));
	}

	@NotNull
	@Override
	public RawNumber acos() {
		return newInstance(DoubleRawNumber.newInstance(this.value.doubleValue()).acos());
	}

	@Override
	public boolean isNaN() {
		return false;
	}

	@NotNull
	@Override
	public RawNumber asin() {
		return newInstance(DoubleRawNumber.newInstance(this.value.doubleValue()).asin());
	}

	@NotNull
	@Override
	public RawNumber atan() {
		return newInstance(DoubleRawNumber.newInstance(this.value.doubleValue()).atan());
	}

	@NotNull
	@Override
	public RawNumber tan() {
		return newInstance(DoubleRawNumber.newInstance(this.value.doubleValue()).tan());
	}

	@Override
	public double asDouble() {
		return this.value.doubleValue();
	}

	@NotNull
	@Override
	public BigDecimal asBigDecimal() {
		return value;
	}

	@NotNull
	@Override
	public RawNumber add(@NotNull RawNumber that) {
		return newInstance(this.value.add(that.asBigDecimal()));
	}

	@NotNull
	@Override
	public RawNumber subtract(@NotNull RawNumber that) {
		return newInstance(this.value.subtract(that.asBigDecimal()));
	}

	@NotNull
	@Override
	public RawNumber multiply(@NotNull RawNumber that) {
		return newInstance(this.value.multiply(that.asBigDecimal()));
	}

	@NotNull
	@Override
	public RawNumber divide(@NotNull RawNumber that) throws NotDivisibleException {
		return newInstance(this.value.divide(that.asBigDecimal()));
	}

	@Override
	public int compareTo(RawNumber o) {
		return this.value.compareTo(o.asBigDecimal());
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		BigDecimalRawNumber that = (BigDecimalRawNumber) o;

		if (!value.equals(that.value)) return false;

		return true;
	}

	@Override
	public int hashCode() {
		return value.hashCode();
	}
}

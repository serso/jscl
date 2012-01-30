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

	public static BigDecimalRawNumber newInstance(@NotNull BigDecimal value) {
		return new BigDecimalRawNumber(value);
	}

	@NotNull
	private BigDecimal toBigDecimal( @NotNull Double value ) {
		return new BigDecimal(value);
	}

	@NotNull
	@Override
	public RawNumber addDouble(@NotNull Double that) {
		return add(newInstance(toBigDecimal(that)));
	}

	@NotNull
	@Override
	public RawNumber subtractDouble(@NotNull Double that) {
		return subtract(newInstance(toBigDecimal(that)));
	}

	@NotNull
	@Override
	public RawNumber multiplyDouble(@NotNull Double that) {
		return multiply(newInstance(toBigDecimal(that)));
	}

	@NotNull
	@Override
	public RawNumber divideDouble(@NotNull Double that) throws NotDivisibleException {
		return divide(newInstance(toBigDecimal(that)));
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
		return newInstance(this.value.);
	}

	@NotNull
	@Override
	public RawNumber atan2(@NotNull RawNumber that) {
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@NotNull
	@Override
	public RawNumber log() {
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@NotNull
	@Override
	public RawNumber log10() {
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@NotNull
	@Override
	public RawNumber sin() {
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@NotNull
	@Override
	public RawNumber cos() {
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@NotNull
	@Override
	public RawNumber exp() {
		return this.value.;
	}

	@Override
	public boolean isZero() {
		return false;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public boolean isOne() {
		return false;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public boolean positive() {
		return false;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public boolean negative() {
		return false;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@NotNull
	@Override
	public RawNumber pow(@NotNull RawNumber that) {
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@NotNull
	@Override
	public RawNumber acos() {
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public boolean isNaN() {
		return this.value.;
	}

	@NotNull
	@Override
	public RawNumber asin() {
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@NotNull
	@Override
	public RawNumber atan() {
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@NotNull
	@Override
	public RawNumber tan() {
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public double asDouble() {
		return this.value.doubleValue();
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
		// todo serso:
		return 0;  //To change body of implemented methods use File | Settings | File Templates.
	}
}

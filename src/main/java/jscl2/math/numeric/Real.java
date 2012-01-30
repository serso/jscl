package jscl2.math.numeric;

import jscl.math.NotDivisibleException;
import jscl2.MathContext;
import jscl2.math.ArithmeticUtils;
import jscl2.math.RawNumber;
import org.jetbrains.annotations.NotNull;

public final class Real extends Numeric {

	@NotNull
	private final RawNumber content;

	/*
	 * **********************************************
	 * CONSTRUCTORS
	 * ***********************************************
	 */

	Real(@NotNull final MathContext mathContext,
		 @NotNull RawNumber content) {
		super(mathContext);
		this.content = content;
	}

	/*
	 * **********************************************
	 * ADDITION
	 * ***********************************************
	 */

	@NotNull
	public Real add(@NotNull Real that) {
		return new Real(getMathContext(), content.add(that.content));
	}

	@NotNull
	public Numeric add(@NotNull Numeric that) {
		if (that instanceof Real) {
			return add((Real) that);
		} else {
			return ArithmeticUtils.add(this, that);
		}
	}

	/*
	 * **********************************************
	 * SUBTRACTION
	 * ***********************************************
	 */

	@NotNull
	public Real subtract(@NotNull Real that) {
		return new Real(getMathContext(), content.subtract(that.content));
	}

	@NotNull
	public Numeric subtract(@NotNull Numeric that) {
		if (that instanceof Real) {
			return subtract((Real) that);
		} else {
			return ArithmeticUtils.subtract(this, that);
		}
	}

	/*
	 * **********************************************
	 * MULTIPLICATION
	 * ***********************************************
	 */

	@NotNull
	public Real multiply(@NotNull Real that) {
		return new Real(getMathContext(), content.multiply(that.content));
	}

	@NotNull
	public Numeric multiply(@NotNull Numeric that) {
		if (that instanceof Real) {
			return multiply((Real) that);
		} else {
			return that.multiply(this);
		}
	}

	/*
	 * **********************************************
	 * DIVISION
	 * ***********************************************
	 */

	@NotNull
	public Real divide(@NotNull Real that) throws ArithmeticException {
		return new Real(getMathContext(), content.divide(that.content));
	}

	@NotNull
	public Numeric divide(@NotNull Numeric that) throws NotDivisibleException {
		if (that instanceof Real) {
			return divide((Real) that);
		} else {
			return ArithmeticUtils.divide(this, that);
		}
	}

	@NotNull
	public Numeric negate() {
		return new Real(getMathContext(), content.negate());
	}

	public int signum() {
		return content.signum();
	}

	@NotNull
	public Numeric ln() {
		if (signum() >= 0) {
			return new Real(getMathContext(), content.log());
		} else {
			return new Complex(getMathContext(), content.negate().log(), getMathContext().getPI());
		}
	}

	@NotNull
	public Numeric lg() {
		if (signum() >= 0) {
			return new Real(getMathContext(), content.log10());
		} else {
			return new Complex(getMathContext(), content.negate().log10(), getMathContext().getPI());
		}
	}

	@NotNull
	public Numeric exp() {
		return new Real(getMathContext(), content.exp());
	}

	@NotNull
	public Numeric inverse() {
		return new Real(getMathContext(), getMathContext().get1().divide(content));
	}

	public Numeric pow(@NotNull Real that) {
		if (signum() < 0) {
			return Complex.newInstance(getMathContext(), content, getMathContext().get0()).pow(that);
		} else {
			return new Real(getMathContext(), content.pow(that.content));
		}
	}

	public Numeric pow(@NotNull Numeric that) {
		if (that instanceof Real) {
			return pow((Real) that);
		} else {
			return ArithmeticUtils.pow(this, that);
		}
	}

	@NotNull
	public Numeric sqrt() {
		if (signum() < 0) {
			return Complex.newInstance(getMathContext(), getMathContext().get0(), getMathContext().get1()).multiply(negate().sqrt());
		} else {
			return new Real(getMathContext(), content.sqrt());
		}
	}

	@NotNull
	public Numeric nThRoot(int n) {
		if (signum() < 0) {
			return n % 2 == 0 ? sqrt().nThRoot(n / 2) : negate().nThRoot(n).negate();
		} else {
			return super.nThRoot(n);
		}
	}

	public Numeric conjugate() {
		return this;
	}

	@NotNull
	public Numeric acos() {
		final Real result = new Real(getMathContext(), radToDefault(content.acos()));
		if (result.content.isNaN()) {
			return super.acos();
		}
		return result;
	}

	@NotNull
	public Numeric asin() {
		final Real result = new Real(getMathContext(), radToDefault(content.asin()));
		if (result.content.isNaN()) {
			return super.asin();
		}
		return result;
	}

	@NotNull
	public Numeric atan() {
		final Real result = new Real(getMathContext(), radToDefault(atanRad()));
		if (result.content.isNaN()) {
			return super.atan();
		}
		return result;
	}

	@NotNull
	private RawNumber atanRad() {
		return content.atan();
	}

	@NotNull
	@Override
	public Numeric acot() {

		final RawNumber PI_DIV_BY_2_RAD = getMathContext().getPI().divide(getMathContext().get2());
		final Real result = new Real(getMathContext(), radToDefault(PI_DIV_BY_2_RAD.subtract(atanRad())));
		if (result.content.isNaN()) {
			return super.acot();
		}
		return result;
	}

	@NotNull
	public Numeric cos() {
		return new Real(getMathContext(), defaultToRad(content).cos());
	}

	@NotNull
	public Numeric sin() {
		return new Real(getMathContext(), defaultToRad(content).sin());
	}

	@NotNull
	public Numeric tan() {
		return new Real(getMathContext(), defaultToRad(content).tan());
	}

	@NotNull
	@Override
	public Numeric cot() {
		return Real.valueOf(getMathContext(), getMathContext().get1()).divide(tan());
	}

	public Real valueOf(Real value) {
		return new Real(getMathContext(), value.content);
	}

	@NotNull
	public Numeric valueOf(@NotNull Numeric numeric) {
		if (numeric instanceof Real) {
			return valueOf((Real) numeric);
		} else throw new ArithmeticException();
	}

	public int compareTo(@NotNull Real that) {
		return this.content.compareTo(that.content);
	}

	public int compareTo(@NotNull Numeric that) {
		if (that instanceof Real) {
			return compareTo((Real) that);
		} else {
			return ArithmeticUtils.compare(this, that);
		}
	}

	public static Real valueOf(@NotNull MathContext mathContext, @NotNull RawNumber value) {
		return new Real(mathContext, value);
	}

	@NotNull
	RawNumber getContent() {
		return content;
	}

	public String toString() {
		return toString(content);
	}

	@NotNull
	public Complex toComplex() {
		return new Complex(getMathContext(), this.content, getMathContext().get0());
	}
}

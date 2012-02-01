package jscl2.math.numeric;

import jscl.math.NotDivisibleException;
import jscl2.MathContext;
import jscl2.math.RawNumber;
import org.jetbrains.annotations.NotNull;

public final class Real extends AbstractNumber {

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

	@NotNull
	public static Real newInstance(@NotNull final MathContext mathContext,
		 @NotNull RawNumber content) {
		return new Real(mathContext, content);
	}

	@NotNull
	public static Real ZERO(@NotNull MathContext mc) {
		return mc.newReal(0L);
	}

	@NotNull
	public static Real ONE(@NotNull MathContext mc) {
		return mc.newReal(1L);
	}

	@NotNull
	public static Real TWO(@NotNull MathContext mc) {
		return mc.newReal(2L);
	}

	@Override
	@NotNull
	public AbstractNumeric abs() {
		return signum() < 0 ? negate() : this;
	}

	@NotNull
	@Override
	public AbstractNumeric sgn() {
		return divide(abs());
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
	public AbstractNumeric add(@NotNull AbstractNumeric that) {
		if (that instanceof Real) {
			return add((Real) that);
		} else {
			return that.add(this);
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
	public AbstractNumeric subtract(@NotNull AbstractNumeric that) {
		if (that instanceof Real) {
			return subtract((Real) that);
		} else {
			return that.subtract(this);
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
	public AbstractNumeric multiply(@NotNull AbstractNumeric that) {
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
	public AbstractNumeric divide(@NotNull AbstractNumeric that) throws NotDivisibleException {
		if (that instanceof Real) {
			return divide((Real) that);
		} else {
			return that.divide(this);
		}
	}

	@NotNull
	public AbstractNumeric negate() {
		return new Real(getMathContext(), content.negate());
	}

	public int signum() {
		return content.signum();
	}

	@NotNull
	public AbstractNumeric ln() {
		if (signum() >= 0) {
			return new Real(getMathContext(), content.log());
		} else {
			return new Complex(getMathContext(), content.negate().log(), getMathContext().getPI());
		}
	}

	@NotNull
	public AbstractNumeric lg() {
		if (signum() >= 0) {
			return new Real(getMathContext(), content.log10());
		} else {
			return new Complex(getMathContext(), content.negate().log10(), getMathContext().getPI());
		}
	}

	@NotNull
	public AbstractNumeric exp() {
		return new Real(getMathContext(), content.exp());
	}

	@NotNull
	public AbstractNumeric inverse() {
		return new Real(getMathContext(), getMathContext().fromLong(1L).divide(content));
	}

	public AbstractNumeric pow(@NotNull Real that) {
		if (signum() < 0) {
			return Complex.newInstance(getMathContext(), content, getMathContext().fromLong(0L)).pow(that);
		} else {
			return new Real(getMathContext(), content.pow(that.content));
		}
	}

	public AbstractNumeric pow(@NotNull AbstractNumeric that) {
		if (that instanceof Real) {
			return pow((Real) that);
		} else {
			return that.pow(this);
		}
	}

	@NotNull
	public AbstractNumeric sqrt() {
		if (signum() < 0) {
			return Complex.newInstance(getMathContext(), getMathContext().fromLong(0L), getMathContext().fromLong(1L)).multiply(negate().sqrt());
		} else {
			return new Real(getMathContext(), content.sqrt());
		}
	}

	@NotNull
	public AbstractNumeric nThRoot(int n) {
		if (signum() < 0) {
			return n % 2 == 0 ? sqrt().nThRoot(n / 2) : negate().nThRoot(n).negate();
		} else {
			return super.nThRoot(n);
		}
	}

	public AbstractNumeric conjugate() {
		return this;
	}

	@NotNull
	public AbstractNumeric acos() {
		final Real result = new Real(getMathContext(), radToDefault(content.acos()));
		if (result.content.isNaN()) {
			return super.acos();
		}
		return result;
	}

	@NotNull
	public AbstractNumeric asin() {
		final Real result = new Real(getMathContext(), radToDefault(content.asin()));
		if (result.content.isNaN()) {
			return super.asin();
		}
		return result;
	}

	@NotNull
	public AbstractNumeric atan() {
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
	public AbstractNumeric acot() {

		final RawNumber PI_DIV_BY_2_RAD = getMathContext().getPI().divide(getMathContext().fromLong(2L));
		final Real result = new Real(getMathContext(), radToDefault(PI_DIV_BY_2_RAD.subtract(atanRad())));
		if (result.content.isNaN()) {
			return super.acot();
		}
		return result;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Real)) return false;

		Real real = (Real) o;

		if (!content.equals(real.content)) return false;

		return true;
	}

	@Override
	public int hashCode() {
		return content.hashCode();
	}

	@Override
	public boolean mathEquals(INumeric<AbstractNumeric> that) {
		return equals(that);
	}

	@NotNull
	public AbstractNumeric cos() {
		return new Real(getMathContext(), defaultToRad(content).cos());
	}

	@NotNull
	public AbstractNumeric sin() {
		return new Real(getMathContext(), defaultToRad(content).sin());
	}

	@NotNull
	public AbstractNumeric tan() {
		return new Real(getMathContext(), defaultToRad(content).tan());
	}

	@NotNull
	@Override
	public AbstractNumeric cot() {
		return Real.newInstance(getMathContext(), getMathContext().fromLong(1L)).divide(tan());
	}

	public Real valueOf(Real value) {
		return new Real(getMathContext(), value.content);
	}

	@NotNull
	public AbstractNumeric valueOf(@NotNull AbstractNumeric numeric) {
		if (numeric instanceof Real) {
			return valueOf((Real) numeric);
		} else throw new ArithmeticException();
	}

	public int compareTo(@NotNull Real that) {
		return this.content.compareTo(that.content);
	}

/*
	public int compareTo(@NotNull Numeric that) {
		if (that instanceof Real) {
			return compareTo((Real) that);
		} else {
			return that.compareTo(this);
		}
	}
*/

	@NotNull
	RawNumber getContent() {
		return content;
	}

	public String toString() {
		return toString(content);
	}

	@NotNull
	public Complex toComplex() {
		return new Complex(getMathContext(), this.content, getMathContext().fromLong(0L));
	}
}

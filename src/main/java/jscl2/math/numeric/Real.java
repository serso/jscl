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
		return new Real(mc, mc.ZERO());
	}

	@NotNull
	public static Real ONE(@NotNull MathContext mc) {
		return new Real(mc, mc.ONE());
	}

	@NotNull
	public static Real TWO(@NotNull MathContext mc) {
		return mc.newReal(2L);
	}

	@Override
	@NotNull
	public Real abs() {
		return signum() < 0 ? negate() : this;
	}

	/*
	 * **********************************************
	 * ADDITION
	 * ***********************************************
	 */

	@NotNull
	public Real add(@NotNull Real that) {
		return new Real(mc, content.add(that.content));
	}

	@NotNull
	public AbstractNumber add(@NotNull Numeric that) {
		if (that instanceof AbstractNumber) {
			return add((AbstractNumber) that);
		} else {
			throw new ArithmeticException();
		}
	}

	@NotNull
	@Override
	public AbstractNumber add(@NotNull AbstractNumber that) {
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
		return new Real(mc, content.subtract(that.content));
	}

	@NotNull
	public AbstractNumber subtract(@NotNull Numeric that) {
		if (that instanceof AbstractNumber) {
			return subtract((AbstractNumber) that);
		} else {
			throw new ArithmeticException();
		}
	}

	@NotNull
	@Override
	public AbstractNumber subtract(@NotNull AbstractNumber that) {
		if (that instanceof Real) {
			return subtract((Real) that);
		} else {
			// todo serso: optimize
			return that.subtract(this).negate();
		}
	}

	/*
		 * **********************************************
		 * MULTIPLICATION
		 * ***********************************************
		 */

	@NotNull
	public Real multiply(@NotNull Real that) {
		return new Real(mc, content.multiply(that.content));
	}

	@NotNull
	public AbstractNumber multiply(@NotNull Numeric that) {
		if (that instanceof AbstractNumber) {
			return multiply((AbstractNumber) that);
		} else {
			throw new ArithmeticException();
		}
	}

	@NotNull
	@Override
	public AbstractNumber multiply(@NotNull AbstractNumber that) {
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
		return new Real(mc, content.divide(that.content));
	}

	@NotNull
	public AbstractNumber divide(@NotNull Numeric that) throws NotDivisibleException {
		if (that instanceof AbstractNumber) {
			return divide((AbstractNumber) that);
		} else {
			throw new NotDivisibleException();
		}
	}

	@NotNull
	@Override
	public AbstractNumber divide(@NotNull AbstractNumber that) {
		if (that instanceof Real) {
			return divide((Real) that);
		} else {
			return ONE().divide(this).multiply(that);
		}
	}

	@NotNull
	public Real negate() {
		return new Real(mc, content.negate());
	}

	public int signum() {
		return content.signum();
	}

	@NotNull
	public Numeric ln() {
		if (signum() >= 0) {
			return new Real(mc, content.log());
		} else {
			return new Complex(mc, content.negate().log(), mc.getPI());
		}
	}

	@NotNull
	public Numeric lg() {
		if (signum() >= 0) {
			return new Real(mc, content.log10());
		} else {
			return new Complex(mc, content.negate().log10(), mc.getPI());
		}
	}

	@NotNull
	public Numeric exp() {
		return new Real(mc, content.exp());
	}

	@NotNull
	public Numeric inverse() {
		return new Real(mc, mc.fromLong(1L).divide(content));
	}

	public Numeric pow(@NotNull Real that) {
		if (signum() < 0) {
			return Complex.newInstance(mc, content, mc.fromLong(0L)).pow(that);
		} else {
			return new Real(mc, content.pow(that.content));
		}
	}

	public Numeric pow(@NotNull Numeric that) {
		if (that instanceof Real) {
			return pow((Real) that);
		} else if (that instanceof AbstractNumber) {
			return ((AbstractNumber) that).pow(this);
		} else {
			throw new ArithmeticException();
		}
	}

	@NotNull
	public Numeric sqrt() {
		if (signum() < 0) {
			return I().multiply(this.negate().sqrt());
		} else {
			return new Real(mc, content.sqrt());
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

	@NotNull
	public Real conjugate() {
		return this;
	}

	@NotNull
	public Numeric acos() {
		final Real result = new Real(mc, radToDefault(content.acos()));
		if (result.content.isNaN()) {
			return super.acos();
		}
		return result;
	}

	@NotNull
	public Numeric asin() {
		final Real result = new Real(mc, radToDefault(content.asin()));
		if (result.content.isNaN()) {
			return super.asin();
		}
		return result;
	}

	@NotNull
	public Numeric atan() {
		final Real result = new Real(mc, radToDefault(atanRad()));
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

		final RawNumber PI_DIV_BY_2_RAD = mc.getPI().divide(mc.fromLong(2L));
		final Real result = new Real(mc, radToDefault(PI_DIV_BY_2_RAD.subtract(atanRad())));
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
	public boolean mathEquals(INumeric<Numeric> that) {
		return equals(that);
	}

	@NotNull
	public Numeric cos() {
		return new Real(mc, defaultToRad(content).cos());
	}

	@NotNull
	public Numeric sin() {
		return new Real(mc, defaultToRad(content).sin());
	}

	@NotNull
	public Numeric tan() {
		return new Real(mc, defaultToRad(content).tan());
	}

	@NotNull
	@Override
	public Numeric cot() {
		return Real.newInstance(mc, mc.fromLong(1L)).divide(tan());
	}

	public Real valueOf(Real value) {
		return new Real(mc, value.content);
	}

	@NotNull
	public Numeric valueOf(@NotNull Numeric numeric) {
		if (numeric instanceof Real) {
			return valueOf((Real) numeric);
		} else throw new ArithmeticException();
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
		return new Complex(mc, this.content, mc.fromLong(0L));
	}

	public int compareTo(@NotNull Real that) {
		return this.content.compareTo(that.content);
	}

	@Override
	public int compareTo(Numeric that) {
		if ( that instanceof Real) {
			return this.compareTo((Real)that);
		} else {
			return that.compareTo(this);
		}
	}
}

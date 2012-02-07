package jscl2.math.numeric;

import jscl.math.NotDivisibleException;
import jscl2.MathContext;
import jscl2.math.RawNumber;
import org.jetbrains.annotations.NotNull;

public final class Complex extends AbstractNumber {

	@NotNull
	private final RawNumber real;

	@NotNull
	private final RawNumber imaginary;

	/*
	 ************************************************
	 * 					CONSTRUCTORS
	 ************************************************
	 */

	Complex(@NotNull final MathContext mathContext,
			@NotNull RawNumber real,
			@NotNull RawNumber imaginary) {
		super(mathContext);
		this.real = real;
		this.imaginary = imaginary;
	}

	@NotNull
	public static Complex newInstance(@NotNull final MathContext mathContext,
									  @NotNull Real real) {
		return new Complex(mathContext, real.getContent(), real.getMathContext().fromLong(0L));
	}

	@NotNull
	public static Complex newInstance(@NotNull final MathContext mathContext,
									  @NotNull RawNumber real,
									  @NotNull RawNumber imaginary) {
		return new Complex(mathContext, real, imaginary);
	}

	@NotNull
	public static Complex I(@NotNull final MathContext mc) {
		return new Complex(mc, mc.ZERO(), mc.ONE());
	}

	/*
	 *************************************************
	 * 					ADDITION
	 ************************************************
	 */

	@NotNull
	public Complex add(@NotNull Complex that) {
		return new Complex(getMathContext(), real.add(that.real), imaginary.add(that.imaginary));
	}

	@NotNull
	public Complex add(@NotNull Real that) {
		return new Complex(getMathContext(), real.add(that.getContent()), imaginary);
	}

	@NotNull
	public Complex add(@NotNull Numeric that) {
		if (that instanceof AbstractNumber) {
			return add((AbstractNumber) that);
		} else {
			throw new ArithmeticException();
		}
	}

	@NotNull
	@Override
	public Complex add(@NotNull AbstractNumber that) {
		if (that instanceof Complex) {
			return add((Complex) that);
		} else if (that instanceof Real) {
			return add((Real) that);
		} else {
			throw new ArithmeticException();
		}
	}

	/*
		 * **********************************************
		 * SUBTRACTION
		 * ***********************************************
		 */

	@NotNull
	public Complex subtract(@NotNull Complex that) {
		return new Complex(getMathContext(), real.subtract(that.real), imaginary.subtract(that.imaginary));
	}

	@NotNull
	public Complex subtract(@NotNull Real that) {
		return new Complex(getMathContext(), real.subtract(that.getContent()), imaginary);
	}

	@NotNull
	public Complex subtract(@NotNull Numeric that) {
		if (that instanceof AbstractNumber) {
			return subtract((Complex) that);
		} else {
			throw new ArithmeticException();
		}
	}

	@NotNull
	@Override
	public Complex subtract(@NotNull AbstractNumber that) {
		if (that instanceof Complex) {
			return subtract((Complex) that);
		} else if (that instanceof Real) {
			return subtract((Real) that);
		} else {
			throw new ArithmeticException();
		}
	}

	/*
		 * **********************************************
		 * MULTIPLICATION
		 * ***********************************************
		 */

	@NotNull
	public Complex multiply(@NotNull RawNumber that) {
		return new Complex(getMathContext(), real.multiply(that), imaginary.multiply(that));
	}

	@NotNull
	public Complex multiply(@NotNull Complex that) {
		return new Complex(getMathContext(), real.multiply(that.real).subtract(imaginary.multiply(that.imaginary)), real.multiply(that.imaginary).add(imaginary.multiply(that.real)));
	}

	@NotNull
	public Complex multiply(@NotNull Real that) {
		return multiply(that.getContent());
	}

	@NotNull
	@Override
	public Complex multiply(@NotNull AbstractNumber that) {
		if (that instanceof Complex) {
			return multiply((Complex) that);
		} else if (that instanceof Real) {
			return multiply((Real) that);
		} else {
			throw new ArithmeticException();
		}
	}

	@NotNull
	public Numeric multiply(@NotNull Numeric that) {
		if (that instanceof AbstractNumber) {
			return multiply((AbstractNumber) that);
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
	public Complex divide(@NotNull Complex that) throws ArithmeticException {
		return multiply((Complex) that.inverse());
	}

	@NotNull
	public Complex divide(@NotNull Real that) {
		return new Complex(getMathContext(), real.divide(that.getContent()), imaginary.divide(that.getContent()));
	}

	@NotNull
	public Complex divide(@NotNull RawNumber that) {
		return new Complex(getMathContext(), real.divide(that), imaginary.divide(that));
	}

	@NotNull
	public Complex divide(@NotNull Numeric that) throws NotDivisibleException {
		if (that instanceof AbstractNumber) {
			return divide((AbstractNumber) that);
		} else {
			throw new NotDivisibleException();
		}
	}

	@NotNull
	@Override
	public Complex divide(@NotNull AbstractNumber that) {
		if (that instanceof Complex) {
			return divide((Complex) that);
		} else if (that instanceof Real) {
			return divide((Real) that);
		} else {
			throw new NotDivisibleException();
		}
	}

	/**
	 * **********************************************
	 * OTHER
	 * ***********************************************
	 */

	@NotNull
	public Complex negate() {
		return new Complex(getMathContext(), real.negate(), imaginary.negate());
	}

	@NotNull
	@Override
	public Real abs() {
		final Numeric real2 = new Real(getMathContext(), real).pow(2);
		final Numeric imag2 = new Real(getMathContext(), imaginary).pow(2);

		final Numeric sum = real2.add(imag2);
		return (Real)sum.sqrt();
	}

	public int signum() {
		int result;

		int signum = real.signum();
		if (signum > 0) {
			result = 1;
		} else if (signum < 0) {
			result = -1;
		} else {
			result = imaginary.signum();
		}

		return result;
	}

	@NotNull
	public RawNumber magnitude() {
		return real.multiply(real).add(imaginary.multiply(imaginary)).sqrt();
	}

	@NotNull
	public RawNumber magnitude2() {
		return real.multiply(real).add(imaginary.multiply(imaginary));
	}

	@NotNull
	public RawNumber angle() {
		return imaginary.atan2(real);
	}

	@NotNull
	public Numeric ln() {
		if (signum() == 0) {
			return ZERO().ln();
		} else {
			return new Complex(getMathContext(), magnitude().log(), angle());
		}
	}

	@NotNull
	public Numeric lg() {
		if (signum() == 0) {
			return ZERO().lg();
		} else {
			return new Complex(getMathContext(), magnitude().log10(), angle());
		}
	}

	@NotNull
	public Numeric exp() {
		return new Complex(getMathContext(), defaultToRad(imaginary).cos(), defaultToRad(imaginary).sin()).multiply(real.exp());
	}

	@NotNull
	public Numeric inverse() {
		return conjugate().divide(magnitude2());
	}

	@NotNull
	public Complex conjugate() {
		return new Complex(getMathContext(), real, imaginary.negate());
	}

	@NotNull
	public RawNumber getReal() {
		return real;
	}

	@NotNull
	public RawNumber getImaginary() {
		return imaginary;
	}

	public int compareTo(@NotNull Complex that) {
		int result = this.imaginary.compareTo(that.imaginary);
		if (result < 0) {
			return -1;
		} else if (result > 0) {
			return 1;
		} else {
			return this.real.compareTo(that.real);
		}
	}


/*	public int compareTo(@NotNull Numeric that) {
		if (that instanceof Complex) {
			return compareTo((Complex) that);
		} else if (that instanceof Real) {
			return compareTo(newInstance(getMathContext(), (Real) that));
		} else {
			return that.compareTo(this);
		}
	}*/
/*
	@NotNull
	public Complex valueOf(@NotNull Numeric numeric) {
		if (numeric instanceof Complex) {
			return copyOf((Complex) numeric);
		} else if (numeric instanceof Real) {
			return newInstance((Real) numeric);
		} else {
			throw new ArithmeticException();
		}
	}*/


	public String toString() {
		final StringBuilder result = new StringBuilder();

		if (imaginary.isZero()) {
			result.append(toString(real));
		} else {
			if (!real.isZero()) {
				result.append(toString(real));
				if (imaginary.positive()) {
					result.append("+");
				}
			}

			if (!imaginary.isOne()) {
				if (imaginary.negate().isOne()) {
					result.append("-");
				} else {
					result.append(toString(imaginary));
					result.append("*");
				}
			}
			result.append("i");
		}

		return result.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Complex)) return false;

		Complex complex = (Complex) o;

		if (!imaginary.equals(complex.imaginary)) return false;
		if (!real.equals(complex.real)) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = real.hashCode();
		result = 31 * result + imaginary.hashCode();
		return result;
	}

	@Override
	public boolean mathEquals(INumeric<Numeric> that) {
		if ( that instanceof Complex ) {
			return equals(that);
		} else if ( that instanceof Real ) {
			// if real number has come => check if current complex number is real and compare
			if ( isReal() ) {
				if ( this.real.equals(((Real) that).getContent()) ) {
					return true;
				}
			}
		} else {
			return that.mathEquals(this);
		}

		return false;
	}

	public boolean isReal() {
		return this.imaginary.isZero();
	}

	@Override
	public int compareTo(@NotNull Numeric that) {
		if ( that instanceof Complex) {
			return this.compareTo((Complex)that);
		} else if ( that instanceof Real ) {
			return this.compareTo(newInstance(mc, ((Real) that)));
		} else {
			return that.compareTo(this);
		}
	}
}

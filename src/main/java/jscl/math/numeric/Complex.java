package jscl.math.numeric;

import org.jetbrains.annotations.NotNull;

public final class Complex extends Numeric {
	double real, imag;

	Complex(double real, double imag) {
		this.real = real;
		this.imag = imag;
	}

	public Complex add(Complex complex) {
		return new Complex(real + complex.real, imag + complex.imag);
	}

	@NotNull
	public Numeric add(@NotNull Numeric numeric) {
		if (numeric instanceof Complex) {
			return add((Complex) numeric);
		} else if (numeric instanceof JsclDouble) {
			return add(valueOf(numeric));
		} else {
			return numeric.valueOf(this).add(numeric);
		}
	}

	public Complex subtract(Complex complex) {
		return new Complex(real - complex.real, imag - complex.imag);
	}

	@NotNull
	public Numeric subtract(@NotNull Numeric numeric) {
		if (numeric instanceof Complex) {
			return subtract((Complex) numeric);
		} else if (numeric instanceof JsclDouble) {
			return subtract(valueOf(numeric));
		} else {
			return numeric.valueOf(this).subtract(numeric);
		}
	}

	public Complex multiply(Complex complex) {
		return new Complex(real * complex.real - imag * complex.imag, real * complex.imag + imag * complex.real);
	}

	@NotNull
	public Numeric multiply(@NotNull Numeric numeric) {
		if (numeric instanceof Complex) {
			return multiply((Complex) numeric);
		} else if (numeric instanceof JsclDouble) {
			return multiply(valueOf(numeric));
		} else {
			return numeric.multiply(this);
		}
	}

	public Complex divide(Complex complex) throws ArithmeticException {
		return multiply((Complex) complex.inverse());
	}

	@NotNull
	public Numeric divide(@NotNull Numeric numeric) throws ArithmeticException {
		if (numeric instanceof Complex) {
			return divide((Complex) numeric);
		} else if (numeric instanceof JsclDouble) {
			return divide(valueOf(numeric));
		} else {
			return numeric.valueOf(this).divide(numeric);
		}
	}

	@NotNull
	public Numeric negate() {
		return new Complex(-real, -imag);
	}

	@NotNull
	@Override
	public Numeric abs() {
		final Numeric realSquare = new JsclDouble(real).pow(2);
		final Numeric imagSquare = new JsclDouble(imag).pow(2);
		final Numeric sum = realSquare.add(imagSquare);
		return sum.sqrt();
	}

	public int signum() {
		int result;

		if (real > .0) {
			result = 1;
		} else if (real < .0) {
			result = -1;
		} else {
			result = JsclDouble.signum(imag);
		}

		return result;
	}

	public Complex valueof(Complex complex) {
		return new Complex(complex.real, complex.imag);
	}

	public Numeric valueOf(Numeric numeric) {
		if (numeric instanceof Complex) {
			return valueof((Complex) numeric);
		} else if (numeric instanceof JsclDouble) {
			JsclDouble d = (JsclDouble) numeric;
			return d.toComplex();
		} else throw new ArithmeticException();
	}

	public double magnitude() {
		return Math.sqrt(real * real + imag * imag);
	}

	public double magnitude2() {
		return real * real + imag * imag;
	}

	public double angle() {
		return Math.atan2(imag, real);
	}

	@NotNull
	public Numeric ln() {
		if (signum() == 0) {
			return JsclDouble.ZERO.ln();
		} else {
			return new Complex(Math.log(magnitude()), angle());
		}
	}

	@NotNull
	public Numeric lg() {
		if (signum() == 0) {
			return JsclDouble.ZERO.lg();
		} else {
			return new Complex(Math.log10(magnitude()), angle());
		}
	}

	@NotNull
	public Numeric exp() {
		return new Complex(Math.cos(imag), Math.sin(imag)).multiply(Math.exp(real));
	}

	@NotNull
	public Numeric inverse() {
		return ((Complex) conjugate()).divide(magnitude2());
	}

	Complex multiply(double d) {
		return new Complex(real * d, imag * d);
	}

	Complex divide(double d) {
		return new Complex(real / d, imag / d);
	}

	public Numeric conjugate() {
		return new Complex(real, -imag);
	}

	public double realPart() {
		return real;
	}

	public double imaginaryPart() {
		return imag;
	}

	public int compareTo(Complex complex) {
		if (imag < complex.imag) return -1;
		else if (imag > complex.imag) return 1;
		else if (imag == complex.imag) {
			if (real < complex.real) return -1;
			else if (real > complex.real) return 1;
			else if (real == complex.real) return 0;
			else throw new ArithmeticException();
		} else throw new ArithmeticException();
	}

	public int compareTo(Numeric numeric) {
		if (numeric instanceof Complex) {
			return compareTo((Complex) numeric);
		} else if (numeric instanceof JsclDouble) {
			return compareTo(valueOf(numeric));
		} else {
			return numeric.valueOf(this).compareTo(numeric);
		}
	}

	public static Complex valueOf(double real, double imag) {
		return new Complex(real, imag);
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		if (imag == 0.) {
			buffer.append(real);
		} else {
			if (real == 0.) ;
			else {
				buffer.append(real);
				if (imag <= 0.) ;
				else buffer.append("+");
			}
			if (imag == 1.) ;
			else if (imag == -1.) buffer.append("-");
			else {
				buffer.append(imag);
				buffer.append("*");
			}
			buffer.append("√(-1)");
		}
		return buffer.toString();
	}
}

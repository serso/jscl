package jscl.math.numeric;

import jscl.AngleUnits;
import jscl.JsclMathEngine;
import org.jetbrains.annotations.NotNull;

public final class JsclDouble extends Numeric {

	public static final JsclDouble ZERO = new JsclDouble(0d);
	public static final JsclDouble ONE = new JsclDouble(1d);
	public static final JsclDouble TWO = new JsclDouble(2d);

	public static final JsclDouble PI_DIV_2 = JsclDouble.valueOf(Math.PI).divide(TWO);

	private final double content;

	JsclDouble(double val) {
		content = val;
	}

	public JsclDouble add(@NotNull JsclDouble that) {
		return new JsclDouble(content + that.content);
	}

	@NotNull
	public Numeric add(@NotNull Numeric that) {
		if (that instanceof JsclDouble) {
			return add((JsclDouble) that);
		} else {
			return that.valueOf(this).add(that);
		}
	}

	public JsclDouble subtract(JsclDouble that) {
		return new JsclDouble(content - that.content);
	}

	@NotNull
	public Numeric subtract(@NotNull Numeric that) {
		if (that instanceof JsclDouble) {
			return subtract((JsclDouble) that);
		} else {
			return that.valueOf(this).subtract(that);
		}
	}

	public JsclDouble multiply(JsclDouble that) {
		return new JsclDouble(content * that.content);
	}

	@NotNull
	public Numeric multiply(@NotNull Numeric that) {
		if (that instanceof JsclDouble) {
			return multiply((JsclDouble) that);
		} else {
			return that.multiply(this);
		}
	}

	public JsclDouble divide(JsclDouble that) throws ArithmeticException {
		return new JsclDouble(content / that.content);
	}

	@NotNull
	public Numeric divide(@NotNull Numeric that) throws ArithmeticException {
		if (that instanceof JsclDouble) {
			return divide((JsclDouble) that);
		} else {
			return that.valueOf(this).divide(that);
		}
	}

	@NotNull
	public Numeric negate() {
		return new JsclDouble(-content);
	}

	public int signum() {
		return signum(content);
	}

	public static int signum(double value) {
		return value == 0. ? 0 : (value < 0. ? -1 : 1);
	}

	@NotNull
	public Numeric ln() {
		return new JsclDouble(Math.log(content));
	}

	@NotNull
	public Numeric lg() {
		return new JsclDouble(Math.log10(content));
	}

	@NotNull
	public Numeric exp() {
		return new JsclDouble(Math.exp(content));
	}

	@NotNull
	public Numeric inverse() {
		return new JsclDouble(1. / content);
	}

	public Numeric pow(JsclDouble that) {
		if (signum() < 0) {
			return Complex.valueOf(content, 0).pow(that);
		} else {
			return new JsclDouble(Math.pow(content, that.content));
		}
	}

	public Numeric pow(Numeric numeric) {
		if (numeric instanceof JsclDouble) {
			return pow((JsclDouble) numeric);
		} else {
			return numeric.valueOf(this).pow(numeric);
		}
	}

	@NotNull
	public Numeric sqrt() {
		if (signum() < 0) {
			return Complex.valueOf(0, 1).multiply(negate().sqrt());
		} else {
			return new JsclDouble(Math.sqrt(content));
		}
	}

	@NotNull
	public Numeric nthrt(int n) {
		if (signum() < 0) {
			return n % 2 == 0 ? sqrt().nthrt(n / 2) : negate().nthrt(n).negate();
		} else {
			return super.nthrt(n);
		}
	}

	public Numeric conjugate() {
		return this;
	}

	public static double in(double value) {
		return JsclMathEngine.instance.getDefaultAngleUnits().transform(AngleUnits.rad, value);
	}

	public static double out(double value) {
		return AngleUnits.rad.transform(JsclMathEngine.instance.getDefaultAngleUnits(), value);
	}

	public static Numeric in(Numeric value) {
		return JsclMathEngine.instance.getDefaultAngleUnits().transform(AngleUnits.rad, value);
	}

	public static Numeric out(Numeric value) {
		return AngleUnits.rad.transform(JsclMathEngine.instance.getDefaultAngleUnits(), value);
	}

	@NotNull
	public Numeric acos() {
		return new JsclDouble(out(Math.acos(content)));
	}

	@NotNull
	public Numeric asin() {
		return new JsclDouble(out(Math.asin(content)));
	}

	@NotNull
	public Numeric atan() {
		return out(atanRad());
	}

	@NotNull
	private JsclDouble atanRad() {
		return new JsclDouble(Math.atan(content));
	}

	@NotNull
	@Override
	public Numeric acot() {
		return out(PI_DIV_2.subtract(atanRad()));
	}

	@NotNull
	public Numeric cos() {
		return new JsclDouble(Math.cos(in(content)));
	}

	@NotNull
	public Numeric sin() {
		return new JsclDouble(Math.sin(in(content)));
	}

	@NotNull
	public Numeric tan() {
		return new JsclDouble(Math.tan(in(content)));
	}

	@NotNull
	@Override
	public Numeric cot() {
		return JsclDouble.ONE.divide(tan());
	}

	public JsclDouble valueOf(JsclDouble value) {
		return new JsclDouble(value.content);
	}

	public Numeric valueOf(Numeric numeric) {
		if (numeric instanceof JsclDouble) {
			return valueOf((JsclDouble) numeric);
		} else throw new ArithmeticException();
	}

	public double doubleValue() {
		return content;
	}

	public int compareTo(@NotNull JsclDouble that) {
		return Double.compare(this.content, that.content);
	}

	public int compareTo(Numeric numeric) {
		if (numeric instanceof JsclDouble) {
			return compareTo((JsclDouble) numeric);
		} else {
			return numeric.valueOf(this).compareTo(numeric);
		}
	}

	public static JsclDouble valueOf(double value) {
		if (value == 0d) {
			return ZERO;
		} else if ( value == 1d ) {
			return ONE;
		} else if ( value == 2d ) {
			return TWO;
		} else {
			return new JsclDouble(value);
		}
	}

	public String toString() {
		return Double.toString(content);
	}

	@NotNull
	public Complex toComplex() {
		return new Complex(this.content, 0.);
	}
}

package jscl.math.numeric;

public final class JsclDouble extends Numeric {
	double content;

	JsclDouble(double val) {
		content = val;
	}

	public JsclDouble add(JsclDouble dble) {
		return new JsclDouble(content + dble.content);
	}

	public Numeric add(Numeric numeric) {
		if (numeric instanceof JsclDouble) {
			return add((JsclDouble) numeric);
		} else {
			return numeric.valueOf(this).add(numeric);
		}
	}

	public JsclDouble subtract(JsclDouble dble) {
		return new JsclDouble(content - dble.content);
	}

	public Numeric subtract(Numeric numeric) {
		if (numeric instanceof JsclDouble) {
			return subtract((JsclDouble) numeric);
		} else {
			return numeric.valueOf(this).subtract(numeric);
		}
	}

	public JsclDouble multiply(JsclDouble dble) {
		return new JsclDouble(content * dble.content);
	}

	public Numeric multiply(Numeric numeric) {
		if (numeric instanceof JsclDouble) {
			return multiply((JsclDouble) numeric);
		} else {
			return numeric.multiply(this);
		}
	}

	public JsclDouble divide(JsclDouble dble) throws ArithmeticException {
		return new JsclDouble(content / dble.content);
	}

	public Numeric divide(Numeric numeric) throws ArithmeticException {
		if (numeric instanceof JsclDouble) {
			return divide((JsclDouble) numeric);
		} else {
			return numeric.valueOf(this).divide(numeric);
		}
	}

	public Numeric negate() {
		return new JsclDouble(-content);
	}

	public int signum() {
		return signum(content);
	}

	public static  int signum(double value) {
		return value == 0. ? 0 : (value < 0. ? -1 : 1);
	}

	public Numeric ln() {
		return new JsclDouble(Math.log(content));
	}

	public Numeric lg() {
		return new JsclDouble(Math.log10(content));
	}

	public Numeric exp() {
		return new JsclDouble(Math.exp(content));
	}

	public Numeric inverse() {
		return new JsclDouble(1. / content);
	}

	public Numeric pow(JsclDouble dble) {
		if (signum() < 0) {
			return Complex.valueOf(content, 0).pow(dble);
		} else {
			return new JsclDouble(Math.pow(content, dble.content));
		}
	}

	public Numeric pow(Numeric numeric) {
		if (numeric instanceof JsclDouble) {
			return pow((JsclDouble) numeric);
		} else {
			return numeric.valueOf(this).pow(numeric);
		}
	}

	public Numeric sqrt() {
		if (signum() < 0) {
			return Complex.valueOf(0, 1).multiply(negate().sqrt());
		} else {
			return new JsclDouble(Math.sqrt(content));
		}
	}

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

	public Numeric acos() {
		return new JsclDouble(Math.acos(content));
	}

	public Numeric asin() {
		return new JsclDouble(Math.asin(content));
	}

	public Numeric atan() {
		return new JsclDouble(Math.atan(content));
	}

	public Numeric cos() {
		return new JsclDouble(Math.cos(content));
	}

	public Numeric sin() {
		return new JsclDouble(Math.sin(content));
	}

	public Numeric tan() {
		return new JsclDouble(Math.tan(content));
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

	public int compareTo(JsclDouble dble) {
		if (content < dble.content) return -1;
		else if (content > dble.content) return 1;
		else if (content == dble.content) return 0;
		else throw new ArithmeticException();
	}

	public int compareTo(Numeric numeric) {
		if (numeric instanceof JsclDouble) {
			return compareTo((JsclDouble) numeric);
		} else {
			return numeric.valueOf(this).compareTo(numeric);
		}
	}

	public static JsclDouble valueOf(double val) {
		return new JsclDouble(val);
	}

	public String toString() {
		return new Double(content).toString();
	}
}

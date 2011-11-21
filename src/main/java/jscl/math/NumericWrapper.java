package jscl.math;

import jscl.math.function.Constant;
import jscl.math.function.ConstantsRegistry;
import jscl.math.function.IConstant;
import jscl.math.numeric.*;
import jscl.mathml.MathML;
import org.jetbrains.annotations.NotNull;

public final class NumericWrapper extends Generic implements INumeric<NumericWrapper> {

	private final Numeric content;

	public NumericWrapper(JsclInteger integer) {
		content = JsclDouble.valueOf(integer.content().doubleValue());
	}

	public NumericWrapper(Rational rational) {
		content = JsclDouble.valueOf(rational.numerator().doubleValue() / rational.denominator().doubleValue());
	}

	public NumericWrapper(JsclVector vector) {
		Numeric v[] = new Numeric[vector.n];
		for (int i = 0; i < vector.n; i++) v[i] = ((NumericWrapper) vector.element[i].numeric()).content();
		content = new NumericVector(v);
	}

	public NumericWrapper(Matrix matrix) {
		Numeric m[][] = new Numeric[matrix.n][matrix.p];
		for (int i = 0; i < matrix.n; i++) {
			for (int j = 0; j < matrix.p; j++) {
				m[i][j] = ((NumericWrapper) matrix.element[i][j].numeric()).content();
			}
		}
		content = new NumericMatrix(m);
	}

	public NumericWrapper(Constant constant) {
		final IConstant constantFromRegistry = ConstantsRegistry.getInstance().get(constant.getName());

		if (constantFromRegistry != null ) {
			if (constantFromRegistry.getName().equals(Constant.I_CONST.getName())) {
				content = Complex.ONE_I;
			} else {
				if (constantFromRegistry.getValue() != null) {
					final Double value = constantFromRegistry.getDoubleValue();
					if (value == null) {
						throw new ArithmeticException("Constant " + constant.getName() + " has invalid definition: " + constantFromRegistry.getValue());
					} else {
						content = JsclDouble.valueOf(value);
					}
				} else {
					throw new ArithmeticException();
				}
			}
		} else {
			throw new ArithmeticException();
		}
	}

	public NumericWrapper(Numeric numeric) {
		content = numeric;
	}

	public Numeric content() {
		return content;
	}

	public NumericWrapper add(NumericWrapper wrapper) {
		return new NumericWrapper(content.add(wrapper.content));
	}

	@NotNull
	public Generic add(@NotNull Generic that) {
		if (that instanceof NumericWrapper) {
			return add((NumericWrapper) that);
		} else {
			return add(valueOf(that));
		}
	}

	public NumericWrapper subtract(NumericWrapper wrapper) {
		return new NumericWrapper(content.subtract(wrapper.content));
	}

	@NotNull
	public Generic subtract(@NotNull Generic that) {
		if (that instanceof NumericWrapper) {
			return subtract((NumericWrapper) that);
		} else {
			return subtract(valueOf(that));
		}
	}

	public NumericWrapper multiply(NumericWrapper wrapper) {
		return new NumericWrapper(content.multiply(wrapper.content));
	}

	@NotNull
	public Generic multiply(@NotNull Generic that) {
		if (that instanceof NumericWrapper) {
			return multiply((NumericWrapper) that);
		} else {
			return multiply(valueOf(that));
		}
	}

	public NumericWrapper divide(NumericWrapper wrapper) throws ArithmeticException {
		return new NumericWrapper(content.divide(wrapper.content));
	}

	@NotNull
	public Generic divide(@NotNull Generic that) throws ArithmeticException {
		if (that instanceof NumericWrapper) {
			return divide((NumericWrapper) that);
		} else {
			return divide(valueOf(that));
		}
	}

	public Generic gcd(Generic generic) {
		return null;
	}

	public Generic gcd() {
		return null;
	}

	@NotNull
	public NumericWrapper abs() {
		return new NumericWrapper(content.abs());
	}

	@NotNull
	public NumericWrapper negate() {
		return new NumericWrapper(content.negate());
	}

	public int signum() {
		return content.signum();
	}

	public int degree() {
		return 0;
	}

	public Generic antiDerivative(Variable variable) throws NotIntegrableException {
		return null;
	}

	public Generic derivative(Variable variable) {
		return null;
	}

	public Generic substitute(Variable variable, Generic generic) {
		return null;
	}

	public Generic expand() {
		return null;
	}

	public Generic factorize() {
		return null;
	}

	public Generic elementary() {
		return null;
	}

	public Generic simplify() {
		return null;
	}

	public Generic numeric() {
		return this;
	}

	public NumericWrapper valueof(NumericWrapper wrapper) {
		return new NumericWrapper(content.valueOf(wrapper.content));
	}

	public Generic valueOf(Generic generic) {
		if (generic instanceof NumericWrapper) {
			return valueof((NumericWrapper) generic);
		} else if (generic instanceof JsclInteger) {
			return new NumericWrapper((JsclInteger) generic);
		} else {
			throw new ArithmeticException();
		}
	}

	public Generic[] sumValue() {
		return null;
	}

	public Generic[] productValue() throws NotProductException {
		return null;
	}

	public Power powerValue() throws NotPowerException {
		return null;
	}

	public Expression expressionValue() throws NotExpressionException {
		throw new NotExpressionException();
	}

	public JsclInteger integerValue() throws NotIntegerException {
		if (content instanceof JsclDouble) {
			double doubleValue = ((JsclDouble) content).doubleValue();
			if (Math.floor(doubleValue) == doubleValue) {
				return JsclInteger.valueOf((int) doubleValue);
			} else {
				throw new NotIntegerException();
			}
		} else {
			throw new NotIntegerException();
		}
	}

	@Override
	public boolean isInteger() {
		if (content instanceof JsclDouble) {
			double value = ((JsclDouble) content).doubleValue();
			return Math.floor(value) == value;
		}
		return false;
	}

	public Variable variableValue() throws NotVariableException {
		throw new NotVariableException();
	}

	public Variable[] variables() {
		return new Variable[0];
	}

	public boolean isPolynomial(Variable variable) {
		return true;
	}

	public boolean isConstant(Variable variable) {
		return true;
	}

	@NotNull
	public NumericWrapper sgn() {
		return new NumericWrapper(content.sgn());
	}

	@NotNull
	public NumericWrapper ln() {
		return new NumericWrapper(content.ln());
	}

	@NotNull
	public NumericWrapper lg() {
		return new NumericWrapper(content.lg());
	}

	@NotNull
	public NumericWrapper exp() {
		return new NumericWrapper(content.exp());
	}

	@NotNull
	public NumericWrapper inverse() {
		return new NumericWrapper(content.inverse());
	}

	@NotNull
	public NumericWrapper pow(int exponent) {
		return new NumericWrapper(content.pow(exponent));
	}

	public Generic pow(Generic generic) {
		return new NumericWrapper(content.pow(((NumericWrapper) generic).content));
	}

	@NotNull
	public NumericWrapper sqrt() {
		return new NumericWrapper(content.sqrt());
	}

	@NotNull
	public NumericWrapper nthrt(int n) {
		return new NumericWrapper(content.nthrt(n));
	}



	public static Generic root(int subscript, Generic parameter[]) {
		Numeric param[] = new Numeric[parameter.length];
		for (int i = 0; i < param.length; i++) param[i] = ((NumericWrapper) parameter[i]).content;
		return new NumericWrapper(Numeric.root(subscript, param));
	}

	public Generic conjugate() {
		return new NumericWrapper(content.conjugate());
	}

	@NotNull
	public NumericWrapper acos() {
		return new NumericWrapper(content.acos());
	}

	@NotNull
	public NumericWrapper asin() {
		return new NumericWrapper(content.asin());
	}

	@NotNull
	public NumericWrapper atan() {
		return new NumericWrapper(content.atan());
	}

	@NotNull
	public NumericWrapper acot() {
		return new NumericWrapper(content.acot());
	}

	@NotNull
	public NumericWrapper cos() {
		return new NumericWrapper(content.cos());
	}

	@NotNull
	public NumericWrapper sin() {
		return new NumericWrapper(content.sin());
	}

	@NotNull
	public NumericWrapper tan() {
		return new NumericWrapper(content.tan());
	}

	@NotNull
	public NumericWrapper cot() {
		return new NumericWrapper(content.cot());
	}

	@NotNull
	public NumericWrapper acosh() {
		return new NumericWrapper(content.acosh());
	}

	@NotNull
	public NumericWrapper asinh() {
		return new NumericWrapper(content.asinh());
	}

	@NotNull
	public NumericWrapper atanh() {
		return new NumericWrapper(content.atanh());
	}

	@NotNull
	public NumericWrapper acoth() {
		return new NumericWrapper(content.acoth());
	}

	@NotNull
	public NumericWrapper cosh() {
		return new NumericWrapper(content.cosh());
	}

	@NotNull
	public NumericWrapper sinh() {
		return new NumericWrapper(content.sinh());
	}

	@NotNull
	public NumericWrapper tanh() {
		return new NumericWrapper(content.tanh());
	}

	@NotNull
	public NumericWrapper coth() {
		return new NumericWrapper(content.coth());
	}

	public int compareTo(NumericWrapper wrapper) {
		return content.compareTo(wrapper.content);
	}

	public int compareTo(Generic generic) {
		if (generic instanceof NumericWrapper) {
			return compareTo((NumericWrapper) generic);
		} else {
			return compareTo(valueOf(generic));
		}
	}

	public String toString() {
		return content.toString();
	}

	public String toJava() {
		return "JsclDouble.valueOf(" + new Double(((JsclDouble) content).doubleValue()) + ")";
	}

	public void toMathML(MathML element, Object data) {
		int exponent = data instanceof Integer ? (Integer) data : 1;
		if (exponent == 1) bodyToMathML(element);
		else {
			MathML e1 = element.element("msup");
			bodyToMathML(e1);
			MathML e2 = element.element("mn");
			e2.appendChild(element.text(String.valueOf(exponent)));
			e1.appendChild(e2);
			element.appendChild(e1);
		}
	}

	void bodyToMathML(MathML element) {
		MathML e1 = element.element("mn");
		e1.appendChild(element.text(String.valueOf(new Double(((JsclDouble) content).doubleValue()))));
		element.appendChild(e1);
	}

	protected Generic newinstance() {
		return null;
	}


}

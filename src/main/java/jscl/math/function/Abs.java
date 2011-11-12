package jscl.math.function;

import jscl.math.*;
import jscl.mathml.MathML;

public class Abs extends Function {
	public Abs(Generic generic) {
		super("abs", new Generic[]{generic});
	}

	public Generic antiDerivative(int n) throws NotIntegrableException {
		return Constant.half.multiply(parameter[0]).multiply(new Abs(parameter[0]).evaluate());
	}

	public Generic derivative(int n) {
		return new Sgn(parameter[0]).evaluate();
	}

	public Generic evaluate() {
		if (parameter[0].signum() < 0) {
			return new Abs(parameter[0].negate()).evaluate();
		}
		try {
			return parameter[0].integerValue().abs();
		} catch (NotIntegerException e) {
		}
		return expressionValue();
	}

	public Generic evaluateElementary() {
		return new Sqrt(
				parameter[0].pow(2)
		).evaluateElementary();
	}

	public Generic evaluateSimplify() {
		if (parameter[0].signum() < 0) {
			return new Abs(parameter[0].negate()).evaluateSimplify();
		}
		try {
			return parameter[0].integerValue().abs();
		} catch (NotIntegerException e) {
		}
		try {
			Variable v = parameter[0].variableValue();
			if (v instanceof Abs) {
				Function f = (Function) v;
				return f.evaluateSimplify();
			} else if (v instanceof Sgn) {
				return JsclInteger.valueOf(1);
			}
		} catch (NotVariableException e) {
		}
		return expressionValue();
	}

	public Generic evaluateNumerically() {
		return parameter[0].abs();
	}

	public String toJava() {
		final StringBuilder result = new StringBuilder();

		result.append(parameter[0].toJava());
		result.append(".abs()");

		return result.toString();
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
		MathML e1 = element.element("mfenced");
		e1.setAttribute("open", "|");
		e1.setAttribute("close", "|");
		parameter[0].toMathML(e1, null);
		element.appendChild(e1);
	}

	public Variable newInstance() {
		return new Abs(null);
	}
}

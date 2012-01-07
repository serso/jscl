package jscl.math.function;

import jscl.math.*;
import jscl.math.JsclInteger;
import jscl.mathml.MathML;
import org.jetbrains.annotations.NotNull;

public class Fraction extends Algebraic {

	// fraction: n/d
	// where 	n = numerator,
	//			d = denominator
	public Fraction(Generic numerator, Generic denominator) {
		super("frac", new Generic[]{numerator, denominator});
	}

	@Override
	public int getMinParameters() {
		return 2;
	}

	public Root rootValue() {
		return new Root(new Generic[]{parameters[0].negate(), parameters[1]}, 0);
	}

	public Generic antiDerivative(@NotNull Variable variable) throws NotIntegrableException {
		if (parameters[0].isPolynomial(variable) && parameters[1].isPolynomial(variable)) {
			return AntiDerivative.compute(this, variable);
		} else throw new NotIntegrableException();
	}

	public Generic derivative(int n) {
		if (n == 0) {
			return new Inverse(parameters[1]).evaluate();
		} else {
			return parameters[0].multiply(new Inverse(parameters[1]).evaluate().pow(2).negate());
		}
	}

	public boolean integer() {
		try {
			parameters[0].integerValue().intValue();
			parameters[1].integerValue().intValue();
			return true;
		} catch (NotIntegerException e) {
		}
		return false;
	}

	public Generic evaluate() {
		if (parameters[0].compareTo(JsclInteger.valueOf(1)) == 0) {
			return new Inverse(parameters[1]).evaluate();
		}
		try {
			return parameters[0].divide(parameters[1]);
		} catch (NotDivisibleException e) {
		} catch (ArithmeticException e) {
		}

		return expressionValue();
	}

	public Generic selfElementary() {
		return evaluate();
	}

	public Generic selfSimplify() {
		if (parameters[0].signum() < 0) {
			return new Fraction(parameters[0].negate(), parameters[1]).selfSimplify().negate();
		}
		if (parameters[1].signum() < 0) {
			return new Fraction(parameters[0].negate(), parameters[1].negate()).selfSimplify();
		}
		return evaluate();
	}

	public Generic selfNumeric() {
		//if (parameter[0] instanceof NumericWrapper && parameter[1] instanceof NumericWrapper) {
		return ((NumericWrapper) parameters[0]).divide((NumericWrapper) parameters[1]);
		//} else {
		//		return (parameter[0]).divide(parameter[1]);
		//}
	}

	static Generic[] separateCoefficient(@NotNull Generic generic) {
		if (generic.signum() < 0) {
			Generic n[] = separateCoefficient(generic.negate());
			return new Generic[]{n[0], n[1], n[2].negate()};
		}
		try {
			Variable v = generic.variableValue();
			if (v instanceof Fraction) {
				Generic g[] = ((Fraction) v).getParameters();
				Generic a = g[0].expressionValue();
				Generic d = g[1].expressionValue();
				Generic na[] = a.gcdAndNormalize();
				Generic nd[] = d.gcdAndNormalize();
				return new Generic[]{na[0], nd[0], new Fraction(na[1], nd[1]).evaluate()};
			}
		} catch (NotVariableException e) {
			try {
				Generic a = generic.expressionValue();
				Generic n[] = a.gcdAndNormalize();
				return new Generic[]{n[0], JsclInteger.valueOf(1), n[1]};
			} catch (NotExpressionException e2) {
			}
		}
		return new Generic[]{JsclInteger.valueOf(1), JsclInteger.valueOf(1), generic};
	}

	public String toString() {
		StringBuilder result = new StringBuilder();
		try {
			parameters[0].powerValue();
			result.append(parameters[0]);
		} catch (NotPowerException e) {
			result.append(GenericVariable.valueOf(parameters[0]));
		}
		result.append("/");
		try {
			Variable v = parameters[1].variableValue();
			if (v instanceof Fraction) {
				result.append(GenericVariable.valueOf(parameters[1]));
			} else result.append(v);
		} catch (NotVariableException e) {
			try {
				parameters[1].abs().powerValue();
				result.append(parameters[1]);
			} catch (NotPowerException e2) {
				result.append(GenericVariable.valueOf(parameters[1]));
			}
		}
		return result.toString();
	}

	public String toJava() {
		StringBuilder result = new StringBuilder();
		result.append(parameters[0].toJava());
		result.append(".divide(");
		result.append(parameters[1].toJava());
		result.append(")");
		return result.toString();
	}

	void bodyToMathML(MathML element, boolean fenced) {
		if (fenced) {
			MathML e1 = element.element("mfenced");
			bodyToMathML(e1);
			element.appendChild(e1);
		} else {
			bodyToMathML(element);
		}
	}

	void bodyToMathML(MathML element) {
		MathML e1 = element.element("mfrac");
		parameters[0].toMathML(e1, null);
		parameters[1].toMathML(e1, null);
		element.appendChild(e1);
	}

	public Variable newInstance() {
		return new Fraction(null, null);
	}
}

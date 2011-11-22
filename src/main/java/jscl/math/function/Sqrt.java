package jscl.math.function;

import jscl.math.*;
import jscl.math.JsclInteger;
import jscl.mathml.MathML;
import org.jetbrains.annotations.NotNull;

public class Sqrt extends Algebraic {

	public Sqrt(Generic generic) {
		super("√", new Generic[]{generic});
	}

	public Root rootValue() {
		return new Root(new Generic[]{ parameters[0].negate(), JsclInteger.valueOf(0),JsclInteger.valueOf(1)}, 0);
	}

	public Generic antiDerivative(Variable variable) throws NotIntegrableException {
		Root r = rootValue();
		Generic g[] = r.getParameters();
		if (g[0].isPolynomial(variable)) {
			return AntiDerivative.compute(r, variable);
		} else {
			throw new NotIntegrableException();
		}
	}

	public Generic derivative(int n) {
		return Constant.half.multiply(new Inv( evaluate()).evaluate());
	}

	public boolean imaginary() {
		return parameters[0].compareTo(JsclInteger.valueOf(-1)) == 0;
	}

	public Generic evaluate() {
		Generic result;

		try {
			final JsclInteger p = parameters[0].integerValue();
			if (p.signum() < 0) {
				// result will be complex => evaluate
				result = expressionValue();
			} else {
				final Generic sqrt = p.sqrt();
				if (sqrt.pow(2).compareTo(p) == 0) {
					result = sqrt;
				} else {
					result = expressionValue();
				}
			}
		} catch (NotIntegerException e) {
			result = expressionValue();
		}

		return result;
	}

	public Generic evaluateElementary() {
		return evaluate();
	}

	public Generic evaluateSimplify() {
		try {
			final JsclInteger p = parameters[0].integerValue();
			if (p.signum() < 0) {
				return Constant.i.multiply(new Sqrt(p.negate()).evaluateSimplify());
			} else {
				final Generic sqrt = p.sqrt();
				if (sqrt.pow(2).compareTo(p) == 0) {
					return sqrt;
				}
			}

			return simplify0(p);

		} catch (NotIntegerException e) {

			try {
				return simplify0(parameters[0]);
			} catch (NotPowerException e1) {
				Generic n[] = Frac.separateCoefficient(parameters[0]);

				if (n[0].compareTo(JsclInteger.valueOf(1)) != 0 || n[1].compareTo(JsclInteger.valueOf(1)) != 0) {
					// n
					final Generic numerator = new Sqrt(n[0]).evaluateSimplify();
					// d
					final Generic denominator = new Sqrt(n[1]).evaluateSimplify();
					// fraction = n / d
					final Generic fraction = new Frac(numerator, denominator).evaluateSimplify();
					return new Sqrt(n[2]).evaluateSimplify().multiply(fraction);
				}
			}
		}
		return expressionValue();
	}

	private Generic simplify0(@NotNull Generic generic) {
		// let's try to present sqrt expression as products
		final Generic products[] = generic.factorize().productValue();

		Generic result = JsclInteger.valueOf(1);
		for (Generic product : products) {
			// and try sqrt for each product
			final Power power = product.powerValue();
			Generic q = power.value(true);
			int c = power.exponent();
			result = result.multiply(q.pow(c / 2).multiply(new Sqrt(q).expressionValue().pow(c % 2)));
		}
		return result;
	}

	public Generic evaluateNumerically() {
		return ((NumericWrapper) parameters[0]).sqrt();
	}

	public String toJava() {
		if (parameters[0].compareTo(JsclInteger.valueOf(-1)) == 0) {
			return "Complex.valueOf(0, 1)";
		} else {
			final StringBuilder result = new StringBuilder();
			result.append(parameters[0].toJava());
			result.append(".").append(name).append("()");
			return result.toString();
		}
	}

	void bodyToMathML(MathML element, boolean fenced) {
		if (parameters[0].compareTo(JsclInteger.valueOf(-1)) == 0) {
			MathML e1 = element.element("mi");
			e1.appendChild(element.text(/*"\u2148"*/"i"));
			element.appendChild(e1);
		} else {
			MathML e1 = element.element("msqrt");
			parameters[0].toMathML(e1, null);
			element.appendChild(e1);
		}
	}

	public Variable newInstance() {
		return new Sqrt(null);
	}
}

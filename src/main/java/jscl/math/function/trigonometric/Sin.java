package jscl.math.function.trigonometric;

import jscl.math.*;
import jscl.math.function.Constant;
import jscl.math.function.Exp;
import jscl.math.function.Trigonometric;

public class Sin extends Trigonometric {
	public Sin(Generic generic) {
		super("sin", new Generic[]{generic});
	}

	public Generic antiDerivative(int n) throws NotIntegrableException {
		return new Cos(parameters[0]).evaluate().negate();
	}

	public Generic derivative(int n) {
		return new Cos(parameters[0]).evaluate();
	}

	public Generic evaluate() {
		final Generic result = trySimplify();

		if (result != null) {
			return result;
		} else {
			return expressionValue();
		}
	}

	public Generic evaluateElementary() {
		return new Exp(
				Constant.i.multiply(parameters[0])
		).evaluateElementary().subtract(
				new Exp(
						Constant.i.multiply(parameters[0].negate())
				).evaluateElementary()
		).multiply(Constant.i.negate().multiply(Constant.half));
	}

	public Generic evaluateSimplify() {
		final Generic result = trySimplify();

		if (result != null) {
			return result;
		} else {

			try {
				Variable v = parameters[0].variableValue();
				if (v instanceof Asin) {
					Generic g[] = ((Asin) v).getParameters();
					return g[0];
				}
			} catch (NotVariableException e) {
			}
			return identity();
		}
	}

	private Generic trySimplify() {
		Generic result = null;

		if (parameters[0].signum() < 0) {
			result = new Sin(parameters[0].negate()).evaluate().negate();
		} else if (parameters[0].signum() == 0) {
			result = JsclInteger.valueOf(0);
		} else if (parameters[0].compareTo(Constant.pi) == 0) {
			result = JsclInteger.valueOf(0);
		}

		return result;
	}

	public Generic identity(Generic a, Generic b) {
		return new Cos(b).evaluateSimplify().multiply(
				new Sin(a).evaluateSimplify()
		).add(
				new Cos(a).evaluateSimplify().multiply(
						new Sin(b).evaluateSimplify()
				)
		);
	}

	public Generic evaluateNumerically() {
		return ((NumericWrapper) parameters[0]).sin();
	}

	public Variable newInstance() {
		return new Sin(null);
	}
}

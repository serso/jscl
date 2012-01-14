package jscl.math.function.trigonometric;

import jscl.math.*;
import jscl.math.function.Constants;
import jscl.math.function.Exp;
import jscl.math.function.Trigonometric;
import org.jetbrains.annotations.NotNull;

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

	public Generic selfElementary() {
		return new Exp(
				Constants.Generic.I.multiply(parameters[0])
		).selfElementary().subtract(
				new Exp(
						Constants.Generic.I.multiply(parameters[0].negate())
				).selfElementary()
		).multiply(Constants.Generic.I.negate().multiply(Constants.Generic.HALF));
	}

	public Generic selfSimplify() {
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
		} else if (parameters[0].compareTo(Constants.Generic.PI) == 0) {
			result = JsclInteger.valueOf(0);
		}

		return result;
	}

	public Generic identity(Generic a, Generic b) {
		return new Cos(b).selfSimplify().multiply(
				new Sin(a).selfSimplify()
		).add(
				new Cos(a).selfSimplify().multiply(
						new Sin(b).selfSimplify()
				)
		);
	}

	public Generic selfNumeric() {
		return ((NumericWrapper) parameters[0]).sin();
	}

	@NotNull
	public Variable newInstance() {
		return new Sin(null);
	}
}

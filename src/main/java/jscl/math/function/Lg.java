package jscl.math.function;

import jscl.math.*;
import jscl.math.JsclInteger;

public class Lg extends Function {

	public Lg(Generic generic) {
		super("lg", new Generic[]{generic});
	}

	public Generic antiDerivative(int n) throws NotIntegrableException {
		throw new NotIntegrableException();
	}

	public Generic derivative(int n) {
		return new Inv(parameters[0]).evaluate();
	}

	public Generic evaluate() {
		if (parameters[0].compareTo(JsclInteger.valueOf(1)) == 0) {
			return JsclInteger.valueOf(0);
		}
		return expressionValue();
	}

	public Generic selfElementary() {
		return evaluate();
	}

	public Generic selfSimplify() {
		// todo serso: check simplify (was just copied from Ln)
		try {
			JsclInteger en = parameters[0].integerValue();
			if (en.signum() < 0) {
				return Constants.Generic.I.multiply(Constants.Generic.PI).add(new Lg(en.negate()).selfSimplify());
			} else {
				Generic a = en.factorize();
				Generic p[] = a.productValue();
				Generic s = JsclInteger.valueOf(0);
				for (int i = 0; i < p.length; i++) {
					Power o = p[i].powerValue();
					s = s.add(JsclInteger.valueOf(o.exponent()).multiply(new Lg(o.value(true)).expressionValue()));
				}
				return s;
			}
		} catch (NotIntegerException e) {
		}
		try {
			Variable v = parameters[0].variableValue();
			if (v instanceof Sqrt) {
				Generic g[] = ((Sqrt) v).getParameters();
				return Constants.Generic.HALF.multiply(new Lg(g[0]).selfSimplify());
			}
		} catch (NotVariableException e) {
		}
		Generic n[] = Frac.separateCoefficient(parameters[0]);
		if (n[0].compareTo(JsclInteger.valueOf(1)) == 0 && n[1].compareTo(JsclInteger.valueOf(1)) == 0) ;
		else return new Lg(n[2]).selfSimplify().add(
				new Lg(n[0]).selfSimplify()
		).subtract(
				new Lg(n[1]).selfSimplify()
		);
		return expressionValue();
	}

	public Generic selfNumeric() {
		return ((NumericWrapper) parameters[0]).lg();
	}

	public Variable newInstance() {
		return new Lg(null);
	}
}

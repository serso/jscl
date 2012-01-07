package jscl.math.function;

import jscl.math.Generic;
import jscl.math.JsclInteger;
import jscl.math.NotDivisibleException;
import jscl.math.Variable;

public class Inverse extends Fraction {

	// inverse function: 1/g
	public Inverse(Generic generic) {
		super(JsclInteger.valueOf(1), generic);
	}

	public Generic evaluate() {
		try {
			return JsclInteger.valueOf(1).divide(parameter());
		} catch (NotDivisibleException e) {
		}
		return expressionValue();
	}

	public Generic parameter() {
		return parameters[1];
	}

	public Variable newInstance() {
		return new Inverse(null);
	}
}

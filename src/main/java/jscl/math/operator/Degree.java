package jscl.math.operator;

import jscl.math.Generic;
import jscl.math.NumericWrapper;
import jscl.math.Variable;
import jscl.math.function.Constant;
import jscl.math.numeric.JsclDouble;

/**
 * User: serso
 * Date: 10/31/11
 * Time: 10:58 PM
 */
public class Degree extends PostfixFunction {

	public static final String NAME = "°";

	public Degree(Generic expression) {
		super(NAME, new Generic[]{expression});
	}

	public Generic compute() {
		try {
			return numeric();
			// check if really need to catch arithmetic exception
		} catch (ArithmeticException e) {
		}

		return expressionValue();
	}

	@Override
	public Generic numeric() {
		// todo serso: check
		Generic parameter0 = parameter[0].numeric();
		Generic multiply = parameter0.multiply(Constant.pi.numeric());
		return multiply.divide(new NumericWrapper(JsclDouble.valueOf(180)));
	}

	@Override
	public Variable newInstance() {
		return new Degree(null);
	}

	public Degree(String name, Generic[] parameter) {
		super(name, parameter);
	}
}

package jscl.math.function;

import jscl.math.*;
import org.jetbrains.annotations.NotNull;

public class Inverse extends Fraction {

	// inverse function: 1/g
	public Inverse(Generic generic) {
		super(JsclInteger.valueOf(1), generic);
	}

	public Generic selfExpand() {
		try {
			Generic parameter = parameter();
			/*try {
				if (JsclInteger.ZERO.equals(parameter.integerValue())) {
					throw new ArithmeticException("Division by 0!");
				}
			} catch (NotIntegerException e) {
				// ok
			}*/

			return JsclInteger.ONE.divide(parameter);
		} catch (NotDivisibleException e) {
		}

		return expressionValue();
	}

	public Generic parameter() {
		return parameters[1];
	}

	@NotNull
	public Variable newInstance() {
		return new Inverse(null);
	}
}

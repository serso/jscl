package jscl.math.operator;

import jscl.math.*;
import jscl.math.function.Constant;
import jscl.math.numeric.JsclDouble;
import org.jetbrains.annotations.NotNull;

/**
 * User: serso
 * Date: 11/14/11
 * Time: 2:05 PM
 */
public class Percent extends PostfixFunction {

	public Percent(Generic content, Generic previousSumElement) {
		super("%", new Generic[]{content, previousSumElement});
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
		Generic percentValue = parameters[0].numeric();

		final Generic normalizedPercentage = percentValue.divide(JsclInteger.valueOf(100));
		if (parameters.length > 1 && parameters[1] != null) {
			Generic previousSumElement = parameters[1].numeric();

			return previousSumElement.multiply(normalizedPercentage);
		} else {
			throw new ArithmeticException("Second parameter must be set for % function!");
		}
	}

	@Override
	public Variable newInstance() {
		return new Percent(null, null);
	}
}

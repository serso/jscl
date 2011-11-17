package jscl.math.operator;

import jscl.math.Generic;
import jscl.math.JsclInteger;
import jscl.math.Variable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * User: serso
 * Date: 11/14/11
 * Time: 2:05 PM
 */
public class Percent extends PostfixFunction {

	public static final String NAME = "%";

	public Percent(Generic content, Generic previousSumElement) {
		super(NAME, new Generic[]{content, previousSumElement});
	}

	private Percent(Generic[] parameters) {
		super(NAME, createParameters(getParameter(parameters, 0), getParameter(parameters, 1)));
	}

	private static Generic[] createParameters(@Nullable Generic content, @Nullable Generic previousSumElement) {
		final Generic[] result;

		if (previousSumElement == null) {
			result = new Generic[]{content};
		} else {
			result = new Generic[]{content, previousSumElement};
		}

		return result;
	}

	@Override
	public int getMinimumNumberOfParameters() {
		return 1;
	}

	@Override
	public int getMaximumNumberOfParameters() {
		return 2;
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
	public Generic simplify() {
		throw new ArithmeticException("Simplify is not supported for percents yet!");
	}

	@Override
	public Generic numeric() {
		Generic percentValue = parameters[0].numeric();

		final Generic normalizedPercentage = percentValue.divide(JsclInteger.valueOf(100));
		if (parameters.length > 1 && parameters[1] != null) {
			Generic previousSumElement = parameters[1].numeric();

			return previousSumElement.multiply(normalizedPercentage);
		} else {
			return normalizedPercentage;
		}
	}

	@NotNull
	@Override
	public Operator newInstance(@NotNull Generic[] parameters) {
		return new Percent(parameters);
	}

	@Override
	public Variable newInstance() {
		return new Percent(null, null);
	}
}

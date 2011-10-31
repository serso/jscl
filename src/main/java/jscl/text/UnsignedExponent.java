package jscl.text;

import jscl.math.Generic;
import jscl.math.GenericVariable;
import jscl.math.operator.Factorial;
import jscl.text.MutableInt;
import org.jetbrains.annotations.NotNull;

/**
 * User: serso
 * Date: 10/27/11
 * Time: 2:45 PM
 */
class UnsignedExponent implements Parser<Generic> {

	public static final Parser<Generic> parser = new UnsignedExponent();

	private UnsignedExponent() {
	}

	public Generic parse(@NotNull String string, @NotNull MutableInt position) throws ParseException {
		Generic result = PrimaryExpressionParser.parser.parse(string, position);

		while (FactorialParser.parser.parse(string, position).isFactorial()) {
			result = new Factorial(GenericVariable.content(result, true)).expressionValue();
		}

		return result;
	}
}

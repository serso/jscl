package jscl.text;

import jscl.math.Generic;
import jscl.math.GenericVariable;
import jscl.math.operator.Factorial;
import org.apache.commons.lang.mutable.MutableInt;
import org.jetbrains.annotations.NotNull;

/**
 * User: serso
 * Date: 10/27/11
 * Time: 2:45 PM
 */
class UnsignedExponent implements Parser {
	public static final Parser parser = new UnsignedExponent();

	private UnsignedExponent() {
	}

	public Object parse(@NotNull String string, @NotNull MutableInt position) throws ParseException {
		final Generic a = PrimaryExpressionParser.parser.parse(string, position);
		boolean factorial = FactorialParser.parser.parse(string, position).isFactorial();
		return factorial ? new Factorial(GenericVariable.content(a, true)).expressionValue() : a;
	}
}

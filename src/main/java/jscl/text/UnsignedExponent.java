package jscl.text;

import jscl.math.Generic;
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

	public Generic parse(@NotNull String expression, @NotNull MutableInt position, int depth, Generic previousSumElement) throws ParseException {
		final Generic content = PrimaryExpressionParser.parser.parse(expression, position, depth, previousSumElement);
		return new PostfixFunctionsParser(content).parse(expression, position, depth, previousSumElement);
	}
}

package jscl.text;

import jscl.math.Generic;
import org.jetbrains.annotations.NotNull;

/**
 * User: serso
 * Date: 10/27/11
 * Time: 2:45 PM
 */
class Exponent implements Parser<Generic> {

	public static final Parser<Generic> parser = new Exponent();

	private Exponent() {
	}

	public Generic parse(@NotNull String expression, @NotNull MutableInt position, int depth) throws ParseException {
		int pos0 = position.intValue();

		boolean sign = MinusParser.parser.parse(expression, position, depth).isSign();

		final Generic result = ParserUtils.parseWithRollback(UnsignedExponent.parser, expression, position, depth, pos0);
		return sign ? result.negate() : result;
	}
}

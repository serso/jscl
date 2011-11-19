package jscl.text;

import jscl.math.Generic;
import org.jetbrains.annotations.NotNull;

/**
 * User: serso
 * Date: 10/27/11
 * Time: 2:45 PM
 */
class Factor implements Parser<Generic> {

	public static final Parser<Generic> parser = new Factor();

	private Factor() {
	}

	public Generic parse(@NotNull String expression, @NotNull MutableInt position, Generic previousSumElement) throws ParseException {

		boolean sign = MinusParser.parser.parse(expression, position, previousSumElement).isSign();

		final Generic result = (Generic) UnsignedFactor.parser.parse(expression, position, previousSumElement);

		return sign ? result.negate() : result;
	}
}

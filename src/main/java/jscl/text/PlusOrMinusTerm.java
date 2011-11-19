package jscl.text;

import jscl.math.Generic;
import org.jetbrains.annotations.NotNull;

/**
 * User: serso
 * Date: 10/27/11
 * Time: 2:44 PM
 */
class PlusOrMinusTerm implements Parser<Generic> {

	public static final Parser<Generic> parser = new PlusOrMinusTerm();

	private PlusOrMinusTerm() {
	}

	public Generic parse(@NotNull String expression, @NotNull MutableInt position, Generic previousSumElement) throws ParseException {
		int pos0 = position.intValue();

		ParserUtils.skipWhitespaces(expression, position);

		boolean sign;
		if (position.intValue() < expression.length() && (expression.charAt(position.intValue()) == '+' || expression.charAt(position.intValue()) == '-')) {
			sign = expression.charAt(position.intValue()) == '-';
			position.increment();
		} else {
			position.setValue(pos0);
			throw new ParseException();
		}

		final Generic result = ParserUtils.parseWithRollback(TermParser.parser, expression, position, pos0, previousSumElement);

		return sign ? result.negate() : result;
	}

}

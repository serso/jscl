package jscl.text;

import jscl.math.Generic;
import jscl.text.msg.Messages;
import org.jetbrains.annotations.NotNull;

/**
 * User: serso
 * Date: 10/27/11
 * Time: 2:45 PM
 */
class MultiplyOrDivideFactor implements Parser<Generic> {

	public static final Parser<Generic> multiply = new MultiplyOrDivideFactor(true);

	public static final Parser<Generic> divide = new MultiplyOrDivideFactor(false);

	boolean multiplication;

	private MultiplyOrDivideFactor(boolean multiplication) {
		this.multiplication = multiplication;
	}

	public Generic parse(@NotNull String expression, @NotNull MutableInt position, Generic previousSumElement) throws ParseException {
		int pos0 = position.intValue();

		ParserUtils.skipWhitespaces(expression, position);
		if (position.intValue() < expression.length() && expression.charAt(position.intValue()) == (multiplication ? '*' : '/')) {
			position.increment();
		} else {
			ParserUtils.throwParseException(expression, position, pos0, Messages.msg_10, '*', '/');
		}

		return ParserUtils.parseWithRollback(Factor.parser, expression, position, pos0, previousSumElement);
	}
}

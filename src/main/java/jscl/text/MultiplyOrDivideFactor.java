package jscl.text;

import jscl.math.Generic;
import org.jetbrains.annotations.NotNull;

/**
 * User: serso
 * Date: 10/27/11
 * Time: 2:45 PM
 */
class MultiplyOrDivideFactor implements Parser {
	public static final Parser multiply = new MultiplyOrDivideFactor(true);
	public static final Parser divide = new MultiplyOrDivideFactor(false);
	boolean option;

	private MultiplyOrDivideFactor(boolean option) {
		this.option = option;
	}

	public Object parse(@NotNull String expression, @NotNull MutableInt position, int depth, Generic previousSumElement) throws ParseException {
		int pos0 = position.intValue();
		Generic a;
		ParserUtils.skipWhitespaces(expression, position);
		if (position.intValue() < expression.length() && expression.charAt(position.intValue()) == (option ? '*' : '/')) {
			expression.charAt(position.intValue());
			position.increment();
		} else {
			position.setValue(pos0);
			throw new ParseException();
		}
		try {
			a = (Generic) Factor.parser.parse(expression, position, depth, previousSumElement);
		} catch (ParseException e) {
			position.setValue(pos0);
			throw e;
		}
		return a;
	}
}

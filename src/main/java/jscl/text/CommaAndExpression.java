package jscl.text;

import jscl.math.Generic;
import org.jetbrains.annotations.NotNull;

public class CommaAndExpression implements Parser<Generic> {

	public static final Parser<Generic> parser = new CommaAndExpression();

	private CommaAndExpression() {
	}

	public Generic parse(@NotNull String expression, @NotNull MutableInt position, int depth) throws ParseException {
		int pos0 = position.intValue();

		ParserUtils.skipWhitespaces(expression, position);
		if (position.intValue() < expression.length() && expression.charAt(position.intValue()) == ',') {
			position.increment();
		} else {
			position.setValue(pos0);
			throw new ParseException();
		}

		Generic result;
		try {
			result = ExpressionParser.parser.parse(expression, position, depth);
		} catch (ParseException e) {
			position.setValue(pos0);
			throw e;
		}

		return result;
	}
}

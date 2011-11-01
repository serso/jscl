package jscl.text;

import jscl.math.Generic;
import jscl.text.MutableInt;
import org.jetbrains.annotations.NotNull;

public class CommaAndExpression implements Parser<Generic> {

	public static final Parser<Generic> parser = new CommaAndExpression();

	private CommaAndExpression() {
	}

	public Generic parse(@NotNull String string, @NotNull MutableInt position) throws ParseException {
		int pos0 = position.intValue();

		ParserUtils.skipWhitespaces(string, position);
		if (position.intValue() < string.length() && string.charAt(position.intValue()) == ',') {
			position.increment();
		} else {
			position.setValue(pos0);
			throw new ParseException();
		}

		Generic result;
		try {
			result = ExpressionParser.parser.parse(string, position);
		} catch (ParseException e) {
			position.setValue(pos0);
			throw e;
		}

		return result;
	}
}

package jscl.text;

import jscl.math.Generic;
import org.jetbrains.annotations.NotNull;

public class Digits implements Parser<String> {

	public static final Parser<String> parser = new Digits();

	private Digits() {
	}

	// returns digit
	public String parse(@NotNull String expression, @NotNull MutableInt position, Generic previousSumElement) throws ParseException {
		int pos0 = position.intValue();

		final StringBuilder result = new StringBuilder();

		ParserUtils.skipWhitespaces(expression, position);

		if (position.intValue() < expression.length() && Character.isDigit(expression.charAt(position.intValue()))) {
			result.append(expression.charAt(position.intValue()));
			position.increment();
		} else {
			ParserUtils.throwParseException(expression, position, pos0, Messages.msg_9);
		}

		while (position.intValue() < expression.length() && Character.isDigit(expression.charAt(position.intValue()))) {
			result.append(expression.charAt(position.intValue()));
			position.increment();
		}

		return result.toString();
	}
}

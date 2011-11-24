package jscl.text;

import jscl.math.Generic;
import org.jetbrains.annotations.NotNull;

public class PrimeCharacters implements Parser<Integer> {
	public static final Parser<Integer> parser = new PrimeCharacters();

	private PrimeCharacters() {
	}

	public Integer parse(@NotNull String expression, @NotNull MutableInt position, Generic previousSumElement) throws ParseException {

		int pos0 = position.intValue();

		int result = 0;

		ParserUtils.skipWhitespaces(expression, position);

		if (position.intValue() < expression.length() && expression.charAt(position.intValue()) == '\'') {
			position.increment();
			result = 1;
		} else {
			ParserUtils.throwParseException(expression, position, pos0, Messages.MSG_12, '\'');
		}

		while (position.intValue() < expression.length() && expression.charAt(position.intValue()) == '\'') {
			position.increment();
			result++;
		}

		return result;
	}
}

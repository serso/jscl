package jscl.text;

import jscl.math.Generic;
import org.jetbrains.annotations.NotNull;

public class PrimeCharacters implements Parser<Integer> {
	public static final Parser<Integer> parser = new PrimeCharacters();

	private PrimeCharacters() {
	}

	public Integer parse(@NotNull String expression, @NotNull MutableInt position, int depth, Generic previousSumElement) throws ParseException {

		int pos0 = position.intValue();

		int result;

		ParserUtils.skipWhitespaces(expression, position);

		if (position.intValue() < expression.length() && expression.charAt(position.intValue()) == '\'') {
			position.increment();
			result = 1;
		} else {
			position.setValue(pos0);
			throw new ParseException();
		}

		while (position.intValue() < expression.length() && expression.charAt(position.intValue()) == '\'') {
			position.increment();
			result++;
		}

		return result;
	}
}

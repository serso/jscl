package jscl.text;

import org.jetbrains.annotations.NotNull;

public class IntegerParser implements Parser<Integer> {

	public static final Parser<Integer> parser = new IntegerParser();

	private IntegerParser() {
	}

	public Integer parse(@NotNull String expression, @NotNull MutableInt position, int depth) throws ParseException {
		int pos0 = position.intValue();

		int n;

		ParserUtils.skipWhitespaces(expression, position);
		if (position.intValue() < expression.length() && Character.isDigit(expression.charAt(position.intValue()))) {
			char c = expression.charAt(position.intValue());
			position.increment();
			n = c - '0';
		} else {
			position.setValue(pos0);
			throw new ParseException();
		}

		while (position.intValue() < expression.length() && Character.isDigit(expression.charAt(position.intValue()))) {
			char c = expression.charAt(position.intValue());
			position.increment();
			n = 10 * n + (c - '0');
		}

		return n;
	}
}

package jscl.text;

import org.jetbrains.annotations.NotNull;

public class IntegerParser implements Parser<Integer> {

	public static final Parser<Integer> parser = new IntegerParser();

	private IntegerParser() {
	}

	public Integer parse(@NotNull String string, @NotNull MutableInt position) throws ParseException {
		int pos0 = position.intValue();

		int n;

		ParserUtils.skipWhitespaces(string, position);
		if (position.intValue() < string.length() && Character.isDigit(string.charAt(position.intValue()))) {
			char c = string.charAt(position.intValue());
			position.increment();
			n = c - '0';
		} else {
			position.setValue(pos0);
			throw new ParseException();
		}

		while (position.intValue() < string.length() && Character.isDigit(string.charAt(position.intValue()))) {
			char c = string.charAt(position.intValue());
			position.increment();
			n = 10 * n + (c - '0');
		}

		return n;
	}
}

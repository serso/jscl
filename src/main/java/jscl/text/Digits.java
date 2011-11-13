package jscl.text;

import org.jetbrains.annotations.NotNull;

public class Digits implements Parser<String> {

	public static final Parser<String> parser = new Digits();

	private Digits() {
	}

	// returns digit
	public String parse(@NotNull String string, @NotNull MutableInt position, int depth) throws ParseException {
		int pos0 = position.intValue();

		final StringBuilder result = new StringBuilder();

		ParserUtils.skipWhitespaces(string, position);

		if (position.intValue() < string.length() && Character.isDigit(string.charAt(position.intValue()))) {
			result.append(string.charAt(position.intValue()));
			position.increment();
		} else {
			final ParseException e = new ParseException("First symbol of number must be digit!", position, string);
			position.setValue(pos0);
			throw e;
		}

		while (position.intValue() < string.length() && Character.isDigit(string.charAt(position.intValue()))) {
			result.append(string.charAt(position.intValue()));
			position.increment();
		}

		return result.toString();
	}
}

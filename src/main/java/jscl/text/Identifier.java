package jscl.text;

import org.jetbrains.annotations.NotNull;

public class Identifier implements Parser<String> {

	public static final Parser<String> parser = new Identifier();

	private Identifier() {
	}

	// returns variable/constant getName
	public String parse(@NotNull String string, @NotNull MutableInt position, int depth) throws ParseException {
		int pos0 = position.intValue();

		final StringBuilder result = new StringBuilder();

		ParserUtils.skipWhitespaces(string, position);

		if (position.intValue() < string.length() && isValidFirstCharacter(string.charAt(position.intValue()))) {
			result.append(string.charAt(position.intValue()));
			position.increment();
		} else {
			final ParseException e = new ParseException("First symbol of constant or variable must be letter!", position, string);
			position.setValue(pos0);
			throw e;
		}

		while (position.intValue() < string.length() && isValidNotFirstCharacter(string, position)) {
			result.append(string.charAt(position.intValue()));
			position.increment();
		}

		return result.toString();
	}

	private static boolean isValidFirstCharacter(char ch) {
		return Character.isLetter(ch) || ch == '√' || ch == '∞' || ch == 'π';
	}

	private static boolean isValidNotFirstCharacter(@NotNull String string, @NotNull MutableInt position) {
		final char ch = string.charAt(position.intValue());
		return Character.isLetter(ch) || Character.isDigit(ch) || ch == '_';
	}


}

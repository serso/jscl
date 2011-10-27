package jscl.text;

import jscl.text.MutableInt;
import org.jetbrains.annotations.NotNull;

public class Identifier implements Parser<String> {

	public static final Parser<String> parser = new Identifier();

	private Identifier() {
	}

	// returns variable/constant name
	public String parse(@NotNull String string, @NotNull MutableInt position) throws ParseException {
		int pos0 = position.intValue();

		final StringBuilder result = new StringBuilder();

		ParserUtils.skipWhitespaces(string, position);

		if (position.intValue() < string.length() && isValidFirstCharacter(string.charAt(position.intValue()))) {
			result.append(string.charAt(position.intValue()));
			position.increment();
		} else {
			position.setValue(pos0);
			throw new ParseException("First symbol of constant or variable must be letter!");
		}

		while (position.intValue() < string.length() && isValidNotFirstCharacter(string, position)) {
			result.append(string.charAt(position.intValue()));
			position.increment();
		}

		return result.toString();
	}

	private boolean isValidFirstCharacter(char ch) {
		return Character.isLetter(ch);
	}

	private boolean isValidNotFirstCharacter(@NotNull String string, @NotNull MutableInt position) {
		final char ch = string.charAt(position.intValue());
		return Character.isLetter(ch) || Character.isDigit(ch) || ch == '_';
	}


}

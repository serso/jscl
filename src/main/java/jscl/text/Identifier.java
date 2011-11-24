package jscl.text;

import jscl.math.Generic;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public class Identifier implements Parser<String> {

	public static final Parser<String> parser = new Identifier();

	private Identifier() {
	}

	// returns variable/constant getName
	@NotNull
	public String parse(@NotNull String expression, @NotNull MutableInt position, Generic previousSumElement) throws ParseException {
		int pos0 = position.intValue();

		final StringBuilder result = new StringBuilder();

		ParserUtils.skipWhitespaces(expression, position);

		if (position.intValue() < expression.length() && isValidFirstCharacter(expression.charAt(position.intValue()))) {
			result.append(expression.charAt(position.intValue()));
			position.increment();
		} else {
			ParserUtils.throwParseException(expression, position, pos0, Messages.MSG_5);
		}

		while (position.intValue() < expression.length() && isValidNotFirstCharacter(expression, position)) {
			result.append(expression.charAt(position.intValue()));
			position.increment();
		}

		return result.toString();
	}

	private final static List<Character> allowedCharacters = Arrays.asList('√', '∞', 'π', '∂', '∏', 'Σ', '∫');

	private static boolean isValidFirstCharacter(char ch) {
		return Character.isLetter(ch) || allowedCharacters.contains(ch);
	}

	private static boolean isValidNotFirstCharacter(@NotNull String string, @NotNull MutableInt position) {
		final char ch = string.charAt(position.intValue());
		return Character.isLetter(ch) || Character.isDigit(ch) || ch == '_';
	}


}

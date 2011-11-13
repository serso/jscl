package jscl.text;

import jscl.math.Generic;
import org.jetbrains.annotations.NotNull;

/**
 * User: serso
 * Date: 10/27/11
 * Time: 2:40 PM
 */
public class ParserUtils {

	public static void checkInterruption() {
		if (Thread.currentThread().isInterrupted()) {
			throw new ParseInterruptedException("Interrupted!");
		}
	}

	public static void skipWhitespaces(@NotNull String string, @NotNull MutableInt position) {
		while (position.intValue() < string.length() && Character.isWhitespace(string.charAt(position.intValue()))) {
			position.increment();
		}
	}

	public static void tryToParse(@NotNull String expression,
								  @NotNull MutableInt position,
								  int pos0,
								  char ch) throws ParseException {
		skipWhitespaces(expression, position);

		if (position.intValue() < expression.length()) {
			char actual = expression.charAt(position.intValue());
			if (actual == ch) {
				position.increment();
			} else {
				throwParseException(expression, position, pos0, "Expected character is: " + ch + ", actual: " + actual);
			}
		} else {
			throwParseException(expression, position, pos0, "Expected character is: " + ch + " but the end of expression was reached");
		}
	}

	private static void throwParseException(@NotNull String expression,
											@NotNull MutableInt position,
											int pos0,
											@NotNull String message) throws ParseException {
		final ParseException parseException = new ParseException(message, position, expression);
		position.setValue(pos0);
		throw parseException;
	}


	@NotNull
	static <T> T parseWithRollback(@NotNull Parser<T> parser,
								   @NotNull String expression,
								   @NotNull MutableInt position,
								   int depth,
								   int initialPosition) throws ParseException {
		T result;

		try {
			result = parser.parse(expression, position, depth);
		} catch (ParseException e) {
			position.setValue(initialPosition);
			throw e;
		}

		return result;
	}
}

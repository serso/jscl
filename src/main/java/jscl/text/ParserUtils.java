package jscl.text;

import jscl.math.Generic;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Array;

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
				throwParseException(expression, position, pos0, Messages.msg_12, ch);
			}
		} else {
			throwParseException(expression, position, pos0, Messages.msg_12, ch);
		}
	}

	public static void tryToParse(@NotNull String expression,
								  @NotNull MutableInt position,
								  int pos0,
								  @NotNull String s) throws ParseException {
		skipWhitespaces(expression, position);

		if (position.intValue() < expression.length()) {
			if (expression.startsWith(s, position.intValue())) {
				position.add(s.length());
			} else {
				throwParseException(expression, position, pos0, Messages.msg_11, s);
			}
		} else {
			throwParseException(expression, position, pos0, Messages.msg_11, s);
		}
	}

	public static void throwParseException(@NotNull String expression,
											@NotNull MutableInt position,
											int pos0,
											@NotNull String messageId,
											Object... parameters) throws ParseException {
		final ParseException parseException = new ParseException(messageId, position.intValue(), expression, parameters);
		position.setValue(pos0);
		throw parseException;
	}


	@NotNull
	static <T> T parseWithRollback(@NotNull Parser<T> parser,
								   @NotNull String expression,
								   @NotNull MutableInt position,
								   int initialPosition,
								   @Nullable final Generic previousSumParser) throws ParseException {
		T result;

		try {
			result = parser.parse(expression, position, previousSumParser);
		} catch (ParseException e) {
			position.setValue(initialPosition);
			throw e;
		}

		return result;
	}

	public static <T> T[] copyOf(@NotNull T[] array, int newLength) {
		return (T[]) copyOf(array, newLength, array.getClass());
	}

	public static <T> T[] copyOf(@NotNull T[] array) {
		return (T[]) copyOf(array, array.length, array.getClass());
	}

	public static <T, U> T[] copyOf(U[] array, int newLength, Class<? extends T[]> newType) {
		T[] copy = ((Object) newType == (Object) Object[].class)
				? (T[]) new Object[newLength]
				: (T[]) Array.newInstance(newType.getComponentType(), newLength);

		System.arraycopy(array, 0, copy, 0, Math.min(array.length, newLength));

		return copy;
	}
}

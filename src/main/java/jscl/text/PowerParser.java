package jscl.text;

import jscl.math.Generic;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * User: serso
 * Date: 10/27/11
 * Time: 2:45 PM
 */
class PowerParser implements Parser<Void> {

	public static final Parser<Void> parser = new PowerParser();

	private PowerParser() {
	}

	@Nullable
	public Void parse(@NotNull String expression, @NotNull MutableInt position, Generic previousSumElement) throws ParseException {
		int pos0 = position.intValue();

		ParserUtils.skipWhitespaces(expression, position);

		if (position.intValue() < expression.length() && expression.charAt(position.intValue()) == '^') {
			position.increment();
		} else {
			if (isDoubleStar(expression, position)) {
				position.increment();
				position.increment();
			} else {
				ParserUtils.throwParseException(expression, position, pos0, Messages.msg_10, '^', "**");
			}
		}

		return null;
	}

	private boolean isDoubleStar(@NotNull String string, @NotNull MutableInt position) {
		final int i = position.intValue();
		return i < string.length() && i + 1 < string.length() && string.charAt(i) == '*' && string.charAt(i + 1) == '*';
	}
}

package jscl.text;

import jscl.text.MutableInt;
import org.jetbrains.annotations.NotNull;

/**
 * User: serso
 * Date: 10/27/11
 * Time: 2:45 PM
 */
class PowerParser implements Parser {

	public static final Parser parser = new PowerParser();

	private PowerParser() {
	}

	public Object parse(@NotNull String string, @NotNull MutableInt position) throws ParseException {
		int pos0 = position.intValue();
		ParserUtils.skipWhitespaces(string, position);

		if (position.intValue() < string.length() && string.charAt(position.intValue()) == '^') {
			position.increment();
		} else {
			if (isDoubleStar(string, position)) {
				position.increment();
				position.increment();
			} else {
				position.setValue(pos0);
				throw new ParseException();
			}
		}
		return null;
	}

	private boolean isDoubleStar(@NotNull String string, @NotNull MutableInt position) {
		final int i = position.intValue();
		return i < string.length() && i + 1 < string.length() && string.charAt(i) == '*' && string.charAt(i + 1) == '*';
	}
}

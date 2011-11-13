package jscl.text;

import jscl.math.Generic;
import org.jetbrains.annotations.NotNull;

/**
 * User: serso
 * Date: 10/27/11
 * Time: 2:45 PM
 */
class PowerExponent implements Parser<Generic> {

	public static final Parser<Generic> parser = new PowerExponent();

	private PowerExponent() {
	}

	public Generic parse(@NotNull String expression, @NotNull MutableInt position, int depth) throws ParseException {
		int pos0 = position.intValue();

		try {
			PowerParser.parser.parse(expression, position, depth);
		} catch (ParseException e) {
			position.setValue(pos0);
			throw e;
		}

		Generic result;
		try {
			result = Exponent.parser.parse(expression, position, depth);
		} catch (ParseException e) {
			position.setValue(pos0);
			throw e;
		}

		return result;
	}
}

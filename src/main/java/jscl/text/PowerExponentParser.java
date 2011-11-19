package jscl.text;

import jscl.math.Generic;
import org.jetbrains.annotations.NotNull;

/**
 * User: serso
 * Date: 10/27/11
 * Time: 2:45 PM
 */
class PowerExponentParser implements Parser<Generic> {

	public static final Parser<Generic> parser = new PowerExponentParser();

	private PowerExponentParser() {
	}

	public Generic parse(@NotNull String expression, @NotNull MutableInt position, Generic previousSumElement) throws ParseException {
		int pos0 = position.intValue();

		try {
			PowerParser.parser.parse(expression, position, previousSumElement);
		} catch (ParseException e) {
			position.setValue(pos0);
			throw e;
		}

		Generic result;
		try {
			result = ExponentParser.parser.parse(expression, position, previousSumElement);
		} catch (ParseException e) {
			position.setValue(pos0);
			throw e;
		}

		return result;
	}
}

package jscl.text;

import jscl.math.Generic;
import org.jetbrains.annotations.NotNull;

/**
 * User: serso
 * Date: 10/27/11
 * Time: 2:45 PM
 */
class UnsignedExponent implements Parser<Generic> {

	public static final Parser<Generic> parser = new UnsignedExponent();

	private UnsignedExponent() {
	}

	public Generic parse(@NotNull String string, @NotNull MutableInt position) throws ParseException {
		return new PostfixFunctionsParser(PrimaryExpressionParser.parser.parse(string, position)).parse(string, position);
	}
}

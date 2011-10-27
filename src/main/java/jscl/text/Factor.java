package jscl.text;

import jscl.math.Generic;
import org.apache.commons.lang.mutable.MutableInt;
import org.jetbrains.annotations.NotNull;

/**
 * User: serso
 * Date: 10/27/11
 * Time: 2:45 PM
 */
class Factor implements Parser<Generic> {

	public static final Parser parser = new Factor();

	private Factor() {
	}

	public Generic parse(@NotNull String string, @NotNull MutableInt position) throws ParseException {

		boolean sign = MinusParser.parser.parse(string, position).isSign();

		final Generic result = (Generic) UnsignedFactor.parser.parse(string, position);

		return sign ? result.negate() : result;
	}
}

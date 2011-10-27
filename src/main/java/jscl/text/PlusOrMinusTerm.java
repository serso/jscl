package jscl.text;

import jscl.math.Generic;
import org.apache.commons.lang.mutable.MutableInt;
import org.jetbrains.annotations.NotNull;

/**
 * User: serso
 * Date: 10/27/11
 * Time: 2:44 PM
 */
class PlusOrMinusTerm implements Parser {
	public static final Parser parser = new PlusOrMinusTerm();

	private PlusOrMinusTerm() {
	}

	public Object parse(@NotNull String string, @NotNull MutableInt position) throws ParseException {
		int pos0 = position.intValue();
		boolean sign;
		Generic a;
		ParserUtils.skipWhitespaces(string, position);
		if (position.intValue() < string.length() && (string.charAt(position.intValue()) == '+' || string.charAt(position.intValue()) == '-')) {
			sign = string.charAt(position.intValue()) == '-';
			position.increment();
		} else {
			position.setValue(pos0);
			throw new ParseException();
		}
		try {
			a = (Generic) TermParser.parser.parse(string, position);
		} catch (ParseException e) {
			position.setValue(pos0);
			throw e;
		}
		return sign ? a.negate() : a;
	}
}

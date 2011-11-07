package jscl.text;

import jscl.math.Generic;
import org.jetbrains.annotations.NotNull;

public class Subscript implements Parser<Generic> {

	public static final Parser<Generic> parser = new Subscript();

	private Subscript() {
	}

	public Generic parse(@NotNull String string, @NotNull MutableInt position) throws ParseException {
		int pos0 = position.intValue();

		ParserUtils.tryToParse(string, position, pos0, '[');

		Generic a;
		try {
			a = ExpressionParser.parser.parse(string, position);
		} catch (ParseException e) {
			position.setValue(pos0);
			throw e;
		}

		ParserUtils.tryToParse(string, position, pos0, ']');

		return a;
	}

}

package jscl.text;

import jscl.math.Generic;
import org.jetbrains.annotations.NotNull;

public class Subscript implements Parser<Generic> {

	public static final Parser<Generic> parser = new Subscript();

	private Subscript() {
	}

	public Generic parse(@NotNull String expression, @NotNull MutableInt position, int depth) throws ParseException {
		int pos0 = position.intValue();

		ParserUtils.tryToParse(expression, position, pos0, '[');

		Generic a;
		try {
			a = ExpressionParser.parser.parse(expression, position, depth);
		} catch (ParseException e) {
			position.setValue(pos0);
			throw e;
		}

		ParserUtils.tryToParse(expression, position, pos0, ']');

		return a;
	}

}

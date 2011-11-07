package jscl.text;

import jscl.math.Generic;
import org.jetbrains.annotations.NotNull;

public class ExpressionParser implements Parser<Generic> {

	public static final Parser<Generic> parser = new ExpressionParser();

	private ExpressionParser() {
	}

	public Generic parse(@NotNull String string, @NotNull MutableInt position) throws ParseException {
		boolean sign = MinusParser.parser.parse(string, position).isSign();

		Generic result = (Generic) TermParser.parser.parse(string, position);

		if (sign) {
			result = result.negate();
		}

		while (true) {
			try {
				result = result.add((Generic) PlusOrMinusTerm.parser.parse(string, position));
			} catch (ParseException e) {
				break;
			}
		}

		return result;
	}
}


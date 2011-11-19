package jscl.text;

import jscl.math.Generic;
import org.jetbrains.annotations.NotNull;

public class ExpressionParser implements Parser<Generic> {

	public static final Parser<Generic> parser = new ExpressionParser();

	private ExpressionParser() {
	}

	public Generic parse(@NotNull String expression, @NotNull MutableInt position, Generic previousSumElement) throws ParseException {

		boolean sign = MinusParser.parser.parse(expression, position, previousSumElement).isSign();

		Generic result = TermParser.parser.parse(expression, position, previousSumElement);

		if (sign) {
			result = result.negate();
		}

		while (true) {
			try {
				result = result.add(PlusOrMinusTerm.parser.parse(expression, position, result));
			} catch (ParseException e) {
				break;
			}
		}

		return result;
	}
}


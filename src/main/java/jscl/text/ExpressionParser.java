package jscl.text;

import jscl.math.Generic;
import org.jetbrains.annotations.NotNull;

public class ExpressionParser implements Parser<Generic> {

	public static final Parser<Generic> parser = new ExpressionParser();

	private ExpressionParser() {
	}

	@NotNull
	private static final Integer MAX_DEPTH = 15;

	public Generic parse(@NotNull String expression, @NotNull MutableInt position, int depth, Generic previousSumElement) throws ParseException {
		depth = checkDepth(position, depth);

		boolean sign = MinusParser.parser.parse(expression, position, depth, previousSumElement).isSign();

		Generic result = TermParser.parser.parse(expression, position, depth, previousSumElement);

		if (sign) {
			result = result.negate();
		}

		while (true) {
			try {
				result = result.add(PlusOrMinusTerm.parser.parse(expression, position, depth, result));
			} catch (ParseException e) {
				break;
			}
		}

		return result;
	}

	private int checkDepth(@NotNull MutableInt position, int depth) throws ParseException {
		if (depth > MAX_DEPTH) {
			throw new ParseException(position, "Parsed expression is too compex for analyse!");
		}

		return depth + 1;
	}
}


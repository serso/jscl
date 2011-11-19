package jscl.text;

import jscl.math.Generic;
import org.jetbrains.annotations.NotNull;

public class CommaAndExpression implements Parser<Generic> {

	public static final Parser<Generic> parser = new CommaAndExpression();

	private CommaAndExpression() {
	}

	public Generic parse(@NotNull String expression, @NotNull MutableInt position, Generic previousSumElement) throws ParseException {
		int pos0 = position.intValue();

		ParserUtils.skipWhitespaces(expression, position);

		ParserUtils.tryToParse(expression, position, pos0, ',');

		return ParserUtils.parseWithRollback(ExpressionParser.parser, expression, position, pos0, previousSumElement);
	}
}

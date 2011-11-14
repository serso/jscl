package jscl.text;

import jscl.math.ExpressionVariable;
import jscl.math.Generic;
import org.jetbrains.annotations.NotNull;

public class BracketedExpression implements Parser<ExpressionVariable> {

	public static final Parser<ExpressionVariable> parser = new BracketedExpression();

	private BracketedExpression() {
	}

	public ExpressionVariable parse(@NotNull String expression, @NotNull MutableInt position, int depth, Generic previousSumElement) throws ParseException {
		int pos0 = position.intValue();

		ParserUtils.tryToParse(expression, position, pos0, '(');

		Generic result;
		try {
			result = ExpressionParser.parser.parse(expression, position, depth, previousSumElement);
		} catch (ParseException e) {
			position.setValue(pos0);
			throw e;
		}

		ParserUtils.tryToParse(expression, position, pos0, ')');

		return new ExpressionVariable(result);
	}
}

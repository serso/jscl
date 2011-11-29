package jscl.text;

import jscl.math.Generic;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CommaAndExpression implements Parser<Generic> {

	public static final Parser<Generic> parser = new CommaAndExpression();

	private CommaAndExpression() {
	}

	public Generic parse(@NotNull Parameters p, @Nullable Generic previousSumElement) throws ParseException {
		int pos0 = p.getPosition().intValue();

		ParserUtils.skipWhitespaces(p);

		ParserUtils.tryToParse(p, pos0, ',');

		return ParserUtils.parseWithRollback(ExpressionParser.parser, pos0, previousSumElement, p);
	}
}

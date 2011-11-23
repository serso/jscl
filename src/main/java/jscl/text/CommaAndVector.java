package jscl.text;

import jscl.math.Generic;
import jscl.math.JsclVector;
import org.jetbrains.annotations.NotNull;

public class CommaAndVector implements Parser<JsclVector> {

	public static final Parser<JsclVector> parser = new CommaAndVector();

	private CommaAndVector() {
	}

	@NotNull
	public JsclVector parse(@NotNull String expression, @NotNull MutableInt position, Generic previousSumElement) throws ParseException {
		int pos0 = position.intValue();

		ParserUtils.skipWhitespaces(expression, position);

		ParserUtils.tryToParse(expression , position, pos0, ',');

		return ParserUtils.parseWithRollback(VectorParser.parser, expression, position, pos0, previousSumElement);
	}
}

package jscl.text;

import java.util.ArrayList;
import java.util.List;

import jscl.math.Generic;
import jscl.math.JsclVector;
import jscl.util.ArrayUtils;
import org.jetbrains.annotations.NotNull;

public class VectorParser implements Parser<JsclVector> {

	public static final Parser<JsclVector> parser = new VectorParser();

	private VectorParser() {
	}

	public JsclVector parse(@NotNull String expression, @NotNull MutableInt position, Generic previousSumElement) throws ParseException {
		int pos0 = position.intValue();

		ParserUtils.skipWhitespaces(expression, position);

		ParserUtils.tryToParse(expression, position, pos0, '{');

		final List<Generic> result = new ArrayList<Generic>();
		try {
			result.add(ExpressionParser.parser.parse(expression, position, previousSumElement));
		} catch (ParseException e) {
			position.setValue(pos0);
			throw e;
		}

		while (true) {
			try {
				result.add(CommaAndExpression.parser.parse(expression, position, previousSumElement));
			} catch (ParseException e) {
				break;
			}
		}

		ParserUtils.skipWhitespaces(expression, position);

		ParserUtils.tryToParse(expression, position, pos0, '}');

		return new JsclVector(ArrayUtils.toArray(result, new Generic[result.size()]));
	}
}

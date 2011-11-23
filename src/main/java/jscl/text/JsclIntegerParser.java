package jscl.text;

import jscl.JsclMathEngine;
import jscl.NumeralBase;
import jscl.math.Generic;
import jscl.math.JsclInteger;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;

public class JsclIntegerParser implements Parser<Generic> {
	public static final Parser<Generic> parser = new JsclIntegerParser();

	private JsclIntegerParser() {
	}

	public Generic parse(@NotNull String expression, @NotNull MutableInt position, Generic previousSumElement) throws ParseException {
		int pos0 = position.intValue();

		final NumeralBase nb = NumeralBaseParser.parser.parse(expression, position, previousSumElement);

		final StringBuilder result = new StringBuilder();

		result.append(Digits.parser.parse(expression, position, previousSumElement));

		try {
			return nb.toJsclInteger(result.toString());
		} catch (NumberFormatException e) {
			throw new ParseException(e.getMessage(), position, expression);
		}
	}
}

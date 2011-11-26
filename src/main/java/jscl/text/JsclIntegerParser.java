package jscl.text;

import jscl.NumeralBase;
import jscl.math.Generic;
import jscl.text.msg.Messages;
import org.jetbrains.annotations.NotNull;

public class JsclIntegerParser implements Parser<Generic> {
	public static final Parser<Generic> parser = new JsclIntegerParser();

	private JsclIntegerParser() {
	}

	public Generic parse(@NotNull String expression, @NotNull MutableInt position, Generic previousSumElement) throws ParseException {
		int pos0 = position.intValue();

		final NumeralBase nb = NumeralBaseParser.parser.parse(expression, position, previousSumElement);

		final StringBuilder result = new StringBuilder();

		result.append(Digits.parser.parse(expression, position, previousSumElement));

		final String number = result.toString();
		try {
			return nb.toJsclInteger(number);
		} catch (NumberFormatException e) {
			throw new ParseException(Messages.msg_8, position.intValue(), expression, number);
		}
	}
}

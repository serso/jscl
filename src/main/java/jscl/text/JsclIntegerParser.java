package jscl.text;

import jscl.NumeralBase;
import jscl.math.Generic;
import jscl.text.msg.Messages;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class JsclIntegerParser implements Parser<Generic> {

	public static final Parser<Generic> parser = new JsclIntegerParser();

	private JsclIntegerParser() {
	}

	public Generic parse(@NotNull Parameters p, @Nullable Generic previousSumElement) throws ParseException {
		int pos0 = p.getPosition().intValue();

		final NumeralBase nb = NumeralBaseParser.parser.parse(p, previousSumElement);

		final StringBuilder result = new StringBuilder();

		result.append(new Digits(nb).parse(p, previousSumElement));

		final String number = result.toString();
		try {
			return nb.toJsclInteger(number);
		} catch (NumberFormatException e) {
			throw new ParseException(Messages.msg_8, p.getPosition().intValue(), p.getExpression(), number);
		}
	}
}

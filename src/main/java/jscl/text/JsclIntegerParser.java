package jscl.text;

import jscl.math.Generic;
import jscl.math.JsclInteger;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;

public class JsclIntegerParser implements Parser<Generic> {
	public static final Parser<Generic> parser = new JsclIntegerParser();

	private JsclIntegerParser() {
	}

	public Generic parse(@NotNull String string, @NotNull MutableInt position) throws ParseException {
		int pos0 = position.intValue();

		final StringBuilder sb = new StringBuilder();
		try {
			sb.append(Digits.parser.parse(string, position));
		} catch (ParseException e) {
			throw e;
		}

		return new JsclInteger(new BigInteger(sb.toString()));
	}
}
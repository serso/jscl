package jscl.text;

import jscl.math.Generic;
import jscl.math.JSCLInteger;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;

public class JSCLIntegerParser implements Parser<Generic> {
	public static final Parser<Generic> parser = new JSCLIntegerParser();

	private JSCLIntegerParser() {
	}

	public Generic parse(@NotNull String string, @NotNull MutableInt position) throws ParseException {
		int pos0 = position.intValue();

		final StringBuilder sb = new StringBuilder();
		try {
			sb.append(Digits.parser.parse(string, position));
		} catch (ParseException e) {
			throw e;
		}

		return new JSCLInteger(new BigInteger(sb.toString()));
	}
}

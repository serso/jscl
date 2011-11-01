package jscl.text;

import java.util.ArrayList;
import java.util.List;

import jscl.math.Generic;
import jscl.util.ArrayUtils;
import jscl.text.MutableInt;
import org.jetbrains.annotations.NotNull;

public class ParameterList implements Parser<Generic[]> {

	public static final Parser<Generic[]> parser = new ParameterList();

	private ParameterList() {
	}

	public Generic[] parse(@NotNull String string, @NotNull MutableInt position) throws ParseException {
		int pos0 = position.intValue();

		final List<Generic> result = new ArrayList<Generic>();

		ParserUtils.skipWhitespaces(string, position);
		if (position.intValue() < string.length() && string.charAt(position.intValue()) == '(') {
			position.increment();
		} else {
			position.setValue(pos0);
			throw new ParseException();
		}

		try {
			result.add(ExpressionParser.parser.parse(string, position));
		} catch (ParseException e) {
			position.setValue(pos0);
			throw e;
		}

		while (true) {
			try {
				result.add(CommaAndExpression.parser.parse(string, position));
			} catch (ParseException e) {
				break;
			}
		}

		ParserUtils.skipWhitespaces(string, position);
		if (position.intValue() < string.length() && string.charAt(position.intValue()) == ')') {
			position.increment();
		} else {
			position.setValue(pos0);
			throw new ParseException();
		}

		return (Generic[]) ArrayUtils.toArray(result, new Generic[result.size()]);
	}
}

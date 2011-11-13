package jscl.text;

import jscl.math.Generic;
import jscl.util.ArrayUtils;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ParameterList implements Parser<Generic[]> {

	public static final Parser<Generic[]> parser = new ParameterList();

	private ParameterList() {
	}

	public Generic[] parse(@NotNull String string, @NotNull MutableInt position, int depth) throws ParseException {
		int pos0 = position.intValue();

		final List<Generic> result = new ArrayList<Generic>();

		ParserUtils.tryToParse(string, position, pos0, '(');

		try {
			result.add(ExpressionParser.parser.parse(string, position, depth));
		} catch (ParseException e) {
			position.setValue(pos0);
			throw e;
		}

		while (true) {
			try {
				result.add(CommaAndExpression.parser.parse(string, position, depth));
			} catch (ParseException e) {
				break;
			}
		}

		ParserUtils.tryToParse(string, position, pos0, ')');


		return ArrayUtils.toArray(result, new Generic[result.size()]);
	}
}

package jscl.text;

import jscl.math.Generic;
import jscl.math.function.Function;
import jscl.math.function.Root;
import org.jetbrains.annotations.NotNull;

public class RootParser implements Parser<Function> {
	public static final Parser<Function> parser = new RootParser();

	private RootParser() {
	}

	public Function parse(@NotNull String expression, @NotNull MutableInt position, Generic previousSumElement) throws ParseException {
		int pos0 = position.intValue();

		final String name = Identifier.parser.parse(expression, position, previousSumElement);
		if (name.compareTo("root") != 0) {
			ParserUtils.throwParseException(expression, position, pos0, "root expected");
		}

		final Generic subscript = ParserUtils.parseWithRollback(Subscript.parser, expression, position, pos0, previousSumElement);
		final Generic parameters[] = ParserUtils.parseWithRollback(ParameterListParser.parser, expression, position, pos0, previousSumElement);

		return new Root(parameters, subscript);
	}
}

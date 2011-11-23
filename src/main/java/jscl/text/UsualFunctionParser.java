package jscl.text;

import jscl.math.Generic;
import jscl.math.function.Function;
import jscl.math.function.FunctionsRegistry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.solovyev.common.math.MathRegistry;

/**
 * User: serso
 * Date: 10/29/11
 * Time: 1:05 PM
 */
class UsualFunctionParser implements Parser<Function> {

	public static final Parser<Function> parser = new UsualFunctionParser();

	private MathRegistry<Function> functionsRegistry = FunctionsRegistry.getInstance();

	private UsualFunctionParser() {
	}

	public Function parse(@NotNull String expression, @NotNull MutableInt position, Generic previousSumElement) throws ParseException {
		int pos0 = position.intValue();

		final String name = Identifier.parser.parse(expression, position, previousSumElement);

		if (!valid(name)) {
			ParserUtils.throwParseException(expression, position, pos0, "Function name is not valid");
		}

		final Generic parameters[] = ParserUtils.parseWithRollback(ParameterListParser.parser, expression, position, pos0, previousSumElement);

		final Function result = functionsRegistry.get(name);
		if (result != null && result.getMinimumNumberOfParameters() <= parameters.length && result.getMaximumNumberOfParameters() >= parameters.length ) {
			result.setParameters(parameters);
		} else {
			ParserUtils.throwParseException(expression, position, pos0, "Number of parameters differs from expected");
		}

		return result;
	}

	static boolean valid(@Nullable String name) {
		return name != null && FunctionsRegistry.getInstance().getNames().contains(name);
	}
}

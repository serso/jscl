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

	public Function parse(@NotNull String expression, @NotNull MutableInt position, int depth, Generic previousSumElement) throws ParseException {
		int pos0 = position.intValue();

		final String name = Identifier.parser.parse(expression, position, depth, previousSumElement);

		if (!valid(name)) {
			position.setValue(pos0);
			throw new ParseException();
		}

		final Generic parameters[] = ParserUtils.parseWithRollback(ParameterListParser.parser, expression, position, depth, pos0, previousSumElement);

		final Function result = functionsRegistry.get(name);
		if (result != null && result.getMinimumNumberOfParameters() <= parameters.length && result.getMaximumNumberOfParameters() >= parameters.length ) {
			result.setParameters(parameters);
		} else {
			position.setValue(pos0);
			throw new ParseException();
		}

		return result;
	}

	static boolean valid(@Nullable String name) {
		return name != null && FunctionsRegistry.getInstance().getNames().contains(name);
	}
}

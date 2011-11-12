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

	public Function parse(@NotNull String string, @NotNull MutableInt position) throws ParseException {
		int pos0 = position.intValue();

		String name = Identifier.parser.parse(string, position);

		if (!valid(name)) {
			position.setValue(pos0);
			throw new ParseException();
		}

		Generic params[];
		try {
			params = (Generic[]) ParameterList.parser.parse(string, position);
		} catch (ParseException e) {
			position.setValue(pos0);
			throw e;
		}

		final Function result = functionsRegistry.get(name);
		if (result != null) {
			result.setParameters(params);
		}
		return result;
	}

	static boolean valid(@Nullable String name) {
		return name != null && FunctionsRegistry.getInstance().getNames().contains(name);
	}
}

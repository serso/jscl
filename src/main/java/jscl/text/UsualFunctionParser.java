package jscl.text;

import jscl.math.Generic;
import jscl.math.function.Function;
import jscl.math.function.FunctionsRegistry;
import org.jetbrains.annotations.NotNull;
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
			result.setParameter(params);
		}
		return result;
	}

	static boolean valid(String name) {
		for (int i = 0; i < na.length; i++) if (name.compareTo(na[i]) == 0) return true;
		return false;
	}

	private static String na[] = {"sin", "cos", "tan", "cot", "asin", "acos", "atan", "acot", "ln", "lg", "exp", "√", "cubic", "sinh", "cosh", "tanh", "coth", "asinh", "acosh", "atanh", "acoth", "abs", "sgn", "conjugate", "eq", "le", "ge", "ne", "lt", "gt", "ap"};
}

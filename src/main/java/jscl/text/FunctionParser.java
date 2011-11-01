package jscl.text;

import jscl.math.function.Function;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FunctionParser implements Parser<Function> {

	public static final Parser<Function> parser = new FunctionParser();

	private static final List<Parser<Function>> parsers = Arrays.asList(
			UsualFunctionParser.parser,
			RootParser.parser,
			ImplicitFunctionParser.parser);

	private FunctionParser() {
	}

	public Function parse(@NotNull String string, @NotNull MutableInt position) throws ParseException {
		return new MultiTryParser<Function>(new ArrayList<Parser<? extends Function>>(parsers)).parse(string, position);
	}
}


package jscl.text;

import jscl.math.Generic;
import jscl.math.Variable;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VariableParser implements Parser<Variable> {

	public static final Parser<Variable> parser = new VariableParser();

	private final static List<Parser<? extends Variable>> parsers = Arrays.asList(
			OperatorParser.parser,
			FunctionParser.parser,
			ConstantParser.parser);

	private final static MultiTryParser<Variable> internalParser = new MultiTryParser<Variable>(new ArrayList<Parser<? extends Variable>>(parsers));

	private VariableParser() {
	}

	public Variable parse(@NotNull String expression, @NotNull MutableInt position, int depth, Generic previousSumElement) throws ParseException {
		return internalParser.parse(expression, position, depth, previousSumElement);
	}
}

package jscl.text;

import jscl.math.DoubleVariable;
import jscl.math.Generic;
import jscl.math.Variable;
import org.jetbrains.annotations.NotNull;

public class DoubleVariableParser implements Parser<Variable> {

	public static final Parser<Variable> parser = new DoubleVariableParser();

	private DoubleVariableParser() {
	}

	@NotNull
	public Variable parse(@NotNull String expression, @NotNull MutableInt position, int depth, Generic previousSumElement) throws ParseException {
		return new DoubleVariable(DoubleParser.parser.parse(expression, position, depth, previousSumElement));
	}
}

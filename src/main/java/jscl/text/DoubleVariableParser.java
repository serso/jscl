package jscl.text;

import jscl.math.DoubleVariable;
import jscl.math.Variable;
import org.jetbrains.annotations.NotNull;

public class DoubleVariableParser implements Parser<Variable> {

	public static final Parser<Variable> parser = new DoubleVariableParser();

	private DoubleVariableParser() {
	}

	@NotNull
	public Variable parse(@NotNull String string, @NotNull MutableInt position, int depth) throws ParseException {
		return new DoubleVariable(DoubleParser.parser.parse(string, position, depth));
	}
}

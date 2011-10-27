package jscl.text;

import jscl.math.DoubleVariable;
import jscl.math.NumericWrapper;
import jscl.math.Variable;
import org.apache.commons.lang.mutable.MutableInt;
import org.jetbrains.annotations.NotNull;

public class DoubleVariableParser implements Parser<Variable> {

	public static final Parser<Variable> parser = new DoubleVariableParser();

	private DoubleVariableParser() {
	}

	@NotNull
	public Variable parse(@NotNull String string, @NotNull MutableInt position) throws ParseException {
		NumericWrapper result;

		try {
			result = (NumericWrapper) DoubleParser.parser.parse(string, position);
		} catch (ParseException e) {
			throw e;
		}

		return new DoubleVariable(result);
	}
}

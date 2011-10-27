package jscl.text;

import jscl.math.Generic;
import jscl.math.Variable;
import jscl.text.MutableInt;
import org.jetbrains.annotations.NotNull;

/**
* User: serso
* Date: 10/27/11
* Time: 3:21 PM
*/
class VariableConverter<T extends Variable> extends AbstractConverter<T, Generic> {

	VariableConverter(@NotNull Parser<T> variableParser) {
		super(variableParser);
	}

	@Override
	public Generic parse(@NotNull String string, @NotNull MutableInt position) throws ParseException {
		return this.parser.parse(string, position).expressionValue();
	}
}

package jscl.text;

import jscl.math.ExpressionVariable;
import jscl.math.Generic;
import jscl.math.Variable;
import jscl.text.MutableInt;
import org.jetbrains.annotations.NotNull;

public class BracketedExpression implements Parser<ExpressionVariable> {

	public static final Parser<ExpressionVariable> parser = new BracketedExpression();

	private BracketedExpression() {
	}

	public ExpressionVariable parse(@NotNull String string, @NotNull MutableInt position) throws ParseException {
		int pos0 = position.intValue();
		Generic a;
		ParserUtils.skipWhitespaces(string, position);
		if (position.intValue() < string.length() && string.charAt(position.intValue()) == '(') {
			string.charAt(position.intValue());
			position.increment();
		} else {
			position.setValue(pos0);
			throw new ParseException();
		}
		try {
			a = (Generic) ExpressionParser.parser.parse(string, position);
		} catch (ParseException e) {
			position.setValue(pos0);
			throw e;
		}
		ParserUtils.skipWhitespaces(string, position);
		if (position.intValue() < string.length() && string.charAt(position.intValue()) == ')') {
			string.charAt(position.intValue());
			position.increment();
		} else {
			position.setValue(pos0);
			throw new ParseException();
		}

		return new ExpressionVariable(a);
	}
}

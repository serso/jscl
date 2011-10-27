package jscl.text;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import jscl.math.Generic;
import jscl.math.GenericVariable;
import jscl.math.JSCLInteger;
import jscl.math.NotIntegerException;
import jscl.math.Variable;
import jscl.math.function.Frac;
import jscl.math.function.Inv;
import jscl.math.function.Pow;
import jscl.math.operator.Factorial;
import jscl.text.MutableInt;
import org.jetbrains.annotations.NotNull;

public class ExpressionParser implements Parser<Generic> {

	public static final Parser<Generic> parser = new ExpressionParser();

	private ExpressionParser() {
	}

	public Generic parse(@NotNull String string, @NotNull MutableInt position) throws ParseException {
		boolean sign = MinusParser.parser.parse(string, position).isSign();

		Generic a;
		try {
			a = (Generic) TermParser.parser.parse(string, position);
		} catch (ParseException e) {
			throw e;
		}

		if (sign) a = a.negate();

		while (true) {
			try {
				Generic a2 = (Generic) PlusOrMinusTerm.parser.parse(string, position);
				a = a.add(a2);
			} catch (ParseException e) {
				break;
			}
		}

		return a;
	}
}


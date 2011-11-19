package jscl.text;

import jscl.math.Generic;
import jscl.math.GenericVariable;
import jscl.math.JsclInteger;
import jscl.math.function.Frac;
import jscl.math.function.Inv;
import org.jetbrains.annotations.NotNull;

/**
 * User: serso
 * Date: 10/27/11
 * Time: 2:45 PM
 */
class TermParser implements Parser<Generic> {

	public static final Parser<Generic> parser = new TermParser();

	private TermParser() {
	}

	public Generic parse(@NotNull String expression, @NotNull MutableInt position, Generic previousSumElement) throws ParseException {
		Generic result = JsclInteger.valueOf(1);

		Generic s = (Generic) UnsignedFactor.parser.parse(expression, position, previousSumElement);

		while (true) {
			try {
				Generic b = MultiplyOrDivideFactor.multiply.parse(expression, position, null);
				result = result.multiply(s);
				s = b;
			} catch (ParseException e) {
				try {
					Generic b = MultiplyOrDivideFactor.divide.parse(expression, position, null);
					if (s.compareTo(JsclInteger.valueOf(1)) == 0)
						s = new Inv(GenericVariable.content(b, true)).expressionValue();
					else
						s = new Frac(GenericVariable.content(s, true), GenericVariable.content(b, true)).expressionValue();
				} catch (ParseException e2) {
					break;
				}
			}
		}

		result = result.multiply(s);

		return result;
	}
}

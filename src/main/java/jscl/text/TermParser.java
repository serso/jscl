package jscl.text;

import jscl.math.Generic;
import jscl.math.GenericVariable;
import jscl.math.JSCLInteger;
import jscl.math.function.Frac;
import jscl.math.function.Inv;
import org.apache.commons.lang.mutable.MutableInt;
import org.jetbrains.annotations.NotNull;

/**
 * User: serso
 * Date: 10/27/11
 * Time: 2:45 PM
 */
class TermParser implements Parser {
	public static final Parser parser = new TermParser();

	private TermParser() {
	}

	public Object parse(@NotNull String string, @NotNull MutableInt position) throws ParseException {
		Generic a = JSCLInteger.valueOf(1);
		Generic s = (Generic) UnsignedFactor.parser.parse(string, position);

		while (true) {
			try {
				Generic b = (Generic) MultiplyOrDivideFactor.multiply.parse(string, position);
				a = a.multiply(s);
				s = b;
			} catch (ParseException e) {
				try {
					Generic b = (Generic) MultiplyOrDivideFactor.divide.parse(string, position);
					if (s.compareTo(JSCLInteger.valueOf(1)) == 0)
						s = new Inv(GenericVariable.content(b, true)).expressionValue();
					else
						s = new Frac(GenericVariable.content(s, true), GenericVariable.content(b, true)).expressionValue();
				} catch (ParseException e2) {
					break;
				}
			}
		}
		a = a.multiply(s);
		return a;
	}
}

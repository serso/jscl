package jscl.text;

import jscl.math.Generic;
import jscl.math.GenericVariable;
import jscl.math.JSCLInteger;
import jscl.math.NotIntegerException;
import jscl.math.function.Pow;
import org.apache.commons.lang.mutable.MutableInt;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 * User: serso
 * Date: 10/27/11
 * Time: 2:45 PM
 */
class UnsignedFactor implements Parser {
	public static final Parser parser = new UnsignedFactor();

	private UnsignedFactor() {
	}

	public Object parse(@NotNull String string, @NotNull MutableInt position) throws ParseException {
		Generic a;
		List l = new ArrayList();
		try {
			a = (Generic) UnsignedExponent.parser.parse(string, position);
			l.add(a);
		} catch (ParseException e) {
			throw e;
		}
		while (true) {
			try {
				a = (Generic) PowerExponent.parser.parse(string, position);
				l.add(a);
			} catch (ParseException e) {
				break;
			}
		}
		ListIterator it = l.listIterator(l.size());
		a = (Generic) it.previous();
		while (it.hasPrevious()) {
			Generic b = (Generic) it.previous();
			try {
				int c = a.integerValue().intValue();
				if (c < 0) a = new Pow(GenericVariable.content(b, true), JSCLInteger.valueOf(c)).expressionValue();
				else a = b.pow(c);
			} catch (NotIntegerException e) {
				a = new Pow(GenericVariable.content(b, true), GenericVariable.content(a, true)).expressionValue();
			}
		}
		return a;
	}
}

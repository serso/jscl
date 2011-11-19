package jscl.text;

import jscl.math.Generic;
import jscl.math.NumericWrapper;
import jscl.math.numeric.JsclDouble;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DoubleParser implements Parser<NumericWrapper> {

	public static final Parser<NumericWrapper> parser = new DoubleParser();

	private static final List<Parser<Double>> parsers = Arrays.asList(
			Singularity.parser,
			FloatingPointLiteral.parser);

	private DoubleParser() {
	}

	@NotNull
	public NumericWrapper parse(@NotNull String expression, @NotNull MutableInt position, Generic previousSumElement) throws ParseException {
		final Parser<Double> multiTryParser = new MultiTryParser<Double>(new ArrayList<Parser<? extends Double>>(parsers));
		return new NumericWrapper(JsclDouble.valueOf(multiTryParser.parse(expression, position, previousSumElement)));
	}
}

class Singularity implements Parser<Double> {

	public static final Parser<Double> parser = new Singularity();

	private Singularity() {
	}

	@NotNull
	public Double parse(@NotNull String expression, @NotNull MutableInt position, Generic previousSumElement) throws ParseException {
		int pos0 = position.intValue();

		final double result;

		String s = Identifier.parser.parse(expression, position, previousSumElement);
		if (s.equals("NaN")) {
			result = Double.NaN;
		} else if (s.equals("Infinity") || s.equals("∞")) {
			result = Double.POSITIVE_INFINITY;
		} else {
			position.setValue(pos0);
			throw new ParseException();
		}

		return result;
	}
}

class FloatingPointLiteral implements Parser<Double> {

	public static final Parser<Double> parser = new FloatingPointLiteral();

	private FloatingPointLiteral() {
	}

	public Double parse(@NotNull String expression, @NotNull MutableInt position, Generic previousSumElement) throws ParseException {
		int pos0 = position.intValue();

		final StringBuilder result = new StringBuilder();

		boolean digits = false;
		boolean point = false;

		try {
			result.append(Digits.parser.parse(expression, position, previousSumElement));
			digits = true;
		} catch (ParseException e) {
		}

		try {
			DecimalPoint.parser.parse(expression, position, previousSumElement);
			result.append(".");
			point = true;
		} catch (ParseException e) {
			if (!digits) {
				position.setValue(pos0);
				throw e;
			}
		}
		try {
			result.append(Digits.parser.parse(expression, position, previousSumElement));
		} catch (ParseException e) {
			if (!digits) {
				position.setValue(pos0);
				throw e;
			}
		}
		try {
			String s = (String) ExponentPart.parser.parse(expression, position, previousSumElement);
			result.append(s);
		} catch (ParseException e) {
			if (!point) {
				position.setValue(pos0);
				throw e;
			}
		}

		return new Double(result.toString());
	}
}

class DecimalPoint implements Parser {
	public static final Parser parser = new DecimalPoint();

	private DecimalPoint() {
	}

	public Object parse(@NotNull String expression, @NotNull MutableInt position, Generic previousSumElement) throws ParseException {
		int pos0 = position.intValue();
		ParserUtils.skipWhitespaces(expression, position);
		if (position.intValue() < expression.length() && expression.charAt(position.intValue()) == '.') {
			expression.charAt(position.intValue());
			position.increment();
		} else {
			position.setValue(pos0);
			throw new ParseException();
		}
		return null;
	}
}

class ExponentPart implements Parser {
	public static final Parser parser = new ExponentPart();

	private ExponentPart() {
	}

	public Object parse(@NotNull String expression, @NotNull MutableInt position, Generic previousSumElement) throws ParseException {
		int pos0 = position.intValue();
		StringBuilder buffer = new StringBuilder();
		ParserUtils.skipWhitespaces(expression, position);
		if (position.intValue() < expression.length() && (expression.charAt(position.intValue()) == 'e' || expression.charAt(position.intValue()) == 'E')) {
			char c = expression.charAt(position.intValue());
			position.increment();
			buffer.append(c);
		} else {
			position.setValue(pos0);
			throw new ParseException();
		}
		try {
			String s = (String) SignedInteger.parser.parse(expression, position, previousSumElement);
			buffer.append(s);
		} catch (ParseException e) {
			position.setValue(pos0);
			throw e;
		}
		return buffer.toString();
	}
}

class SignedInteger implements Parser {

	public static final Parser parser = new SignedInteger();

	private SignedInteger() {
	}

	public Object parse(@NotNull String expression, @NotNull MutableInt position, Generic previousSumElement) throws ParseException {
		int pos0 = position.intValue();

		final StringBuilder result = new StringBuilder();

		ParserUtils.skipWhitespaces(expression, position);

		if (position.intValue() < expression.length() && (expression.charAt(position.intValue()) == '+' || expression.charAt(position.intValue()) == '-')) {
			char c = expression.charAt(position.intValue());
			position.increment();
			result.append(c);
		}

		try {
			result.append(IntegerParser.parser.parse(expression, position, previousSumElement).intValue());
		} catch (ParseException e) {
			position.setValue(pos0);
			throw e;
		}

		return result.toString();
	}
}

package jscl.text;

import jscl.NumeralBase;
import jscl.math.Generic;
import jscl.math.NumericWrapper;
import jscl.math.numeric.Real;
import jscl.text.msg.Messages;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
		return new NumericWrapper(Real.valueOf(multiTryParser.parse(expression, position, previousSumElement)));
	}
}

class Singularity implements Parser<Double> {

	public static final Parser<Double> parser = new Singularity();

	private Singularity() {
	}

	@NotNull
	public Double parse(@NotNull String expression, @NotNull MutableInt position, Generic previousSumElement) throws ParseException {
		int pos0 = position.intValue();

		double result = 0d;

		String s = Identifier.parser.parse(expression, position, previousSumElement);
		if (s.equals("NaN")) {
			result = Double.NaN;
		} else if (s.equals("Infinity") || s.equals("∞")) {
			result = Double.POSITIVE_INFINITY;
		} else {
			ParserUtils.throwParseException(expression, position, pos0, Messages.msg_10, "NaN", "∞");
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

		final NumeralBase nb = NumeralBaseParser.parser.parse(expression, position, previousSumElement);

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
			String s = ExponentPart.parser.parse(expression, position, previousSumElement);
			result.append(s);
		} catch (ParseException e) {
			if (!point) {
				position.setValue(pos0);
				throw e;
			}
		}

		final String doubleString = result.toString();
		try {
			return nb.toDouble(doubleString);
		} catch (NumberFormatException e) {
			throw new ParseException(Messages.msg_8, position.intValue(), expression, doubleString);
		}
	}
}

class DecimalPoint implements Parser<Void> {

	public static final Parser<Void> parser = new DecimalPoint();

	private DecimalPoint() {
	}

	@Nullable
	public Void parse(@NotNull String expression, @NotNull MutableInt position, Generic previousSumElement) throws ParseException {
		int pos0 = position.intValue();

		ParserUtils.skipWhitespaces(expression, position);

		ParserUtils.tryToParse(expression, position, pos0, '.');

		return null;
	}
}

class ExponentPart implements Parser<String> {

	public static final Parser<String> parser = new ExponentPart();

	private ExponentPart() {
	}

	@NotNull
	public String parse(@NotNull String expression, @NotNull MutableInt position, Generic previousSumElement) throws ParseException {
		int pos0 = position.intValue();

		final StringBuilder result = new StringBuilder();

		ParserUtils.skipWhitespaces(expression, position);

		if (position.intValue() < expression.length() && (expression.charAt(position.intValue()) == 'e' || expression.charAt(position.intValue()) == 'E')) {
			char c = expression.charAt(position.intValue());
			position.increment();
			result.append(c);
		} else {
			ParserUtils.throwParseException(expression, position, pos0, Messages.msg_10, 'e', 'E');
		}

		try {
			result.append(SignedInteger.parser.parse(expression, position, previousSumElement));
		} catch (ParseException e) {
			position.setValue(pos0);
			throw e;
		}

		return result.toString();
	}
}

class SignedInteger implements Parser<String> {

	public static final Parser<String> parser = new SignedInteger();

	private SignedInteger() {
	}

	@NotNull
	public String parse(@NotNull String expression, @NotNull MutableInt position, Generic previousSumElement) throws ParseException {
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

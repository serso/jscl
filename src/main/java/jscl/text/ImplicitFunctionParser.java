package jscl.text;

import jscl.math.Generic;
import jscl.math.function.Function;
import jscl.math.function.ImplicitFunction;
import jscl.util.ArrayUtils;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ImplicitFunctionParser implements Parser<Function> {
	public static final Parser<Function> parser = new ImplicitFunctionParser();

	private ImplicitFunctionParser() {
	}

	public Function parse(@NotNull String expression, @NotNull MutableInt position, int depth, Generic previousSumElement) throws ParseException {
		int pos0 = position.intValue();
		Generic a[];

		final String name = ParserUtils.parseWithRollback(CompoundIdentifier.parser, expression, position, depth, pos0, previousSumElement);


		final List<Generic> subscripts = new ArrayList<Generic>();
		while (true) {
			try {
				subscripts.add(Subscript.parser.parse(expression, position, depth, previousSumElement));
			} catch (ParseException e) {
				break;
			}
		}

		int b[];
		try {
			b = Derivation.parser.parse(expression, position, depth, previousSumElement);
		} catch (ParseException e) {
			b = new int[0];
		}
		try {
			a = ParameterListParser.parser.parse(expression, position, depth, previousSumElement);
		} catch (ParseException e) {
			position.setValue(pos0);
			throw e;
		}

		int derivations[] = new int[a.length];
		for (int i = 0; i < a.length && i < b.length; i++) {
			derivations[i] = b[i];
		}

		return new ImplicitFunction(name, a, derivations, ArrayUtils.toArray(subscripts, new Generic[subscripts.size()]));
	}
}

class Derivation implements Parser<int[]> {

	public static final Parser<int[]> parser = new Derivation();

	private Derivation() {
	}

	public int[] parse(@NotNull String expression, @NotNull MutableInt position, int depth, Generic previousSumElement) throws ParseException {

		int result[];
		try {
			result = new int[]{PrimeCharacters.parser.parse(expression, position, depth, previousSumElement)};
		} catch (ParseException e) {
			result = SuperscriptList.parser.parse(expression, position, depth, previousSumElement);
		}

		return result;
	}
}

class SuperscriptList implements Parser<int[]> {

	public static final Parser<int[]> parser = new SuperscriptList();

	private SuperscriptList() {
	}

	public int[] parse(@NotNull String expression, @NotNull MutableInt position, int depth, Generic previousSumElement) throws ParseException {
		int pos0 = position.intValue();

		ParserUtils.skipWhitespaces(expression, position);

		if (position.intValue() < expression.length() && expression.charAt(position.intValue()) == '{') {
			expression.charAt(position.intValue());
			position.increment();
		} else {
			position.setValue(pos0);
			throw new ParseException();
		}

		final List<Integer> result = new ArrayList<Integer>();
		try {
			result.add(IntegerParser.parser.parse(expression, position, depth, previousSumElement));
		} catch (ParseException e) {
			position.setValue(pos0);
			throw e;
		}

		while (true) {
			try {
				result.add(CommaAndInteger.parser.parse(expression, position, depth, previousSumElement));
			} catch (ParseException e) {
				break;
			}
		}

		ParserUtils.skipWhitespaces(expression, position);

		if (position.intValue() < expression.length() && expression.charAt(position.intValue()) == '}') {
			expression.charAt(position.intValue());
			position.increment();
		} else {
			position.setValue(pos0);
			throw new ParseException();
		}

		return ArrayUtils.toArray(result, new int[result.size()]);
	}
}

class CommaAndInteger implements Parser<Integer> {

	public static final Parser<Integer> parser = new CommaAndInteger();

	private CommaAndInteger() {
	}

	public Integer parse(@NotNull String expression, @NotNull MutableInt position, int depth, Generic previousSumElement) throws ParseException {
		int pos0 = position.intValue();

		ParserUtils.skipWhitespaces(expression, position);

		return ParserUtils.parseWithRollback(IntegerParser.parser, expression, position, depth, pos0, previousSumElement);
	}
}

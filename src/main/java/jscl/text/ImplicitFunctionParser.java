package jscl.text;

import jscl.math.Generic;
import jscl.math.function.Function;
import jscl.math.function.FunctionsRegistry;
import jscl.math.function.ImplicitFunction;
import jscl.math.operator.matrix.OperatorsRegistry;
import jscl.util.ArrayUtils;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ImplicitFunctionParser implements Parser<Function> {
	public static final Parser<Function> parser = new ImplicitFunctionParser();

	private ImplicitFunctionParser() {
	}

	public Function parse(@NotNull String expression, @NotNull MutableInt position, Generic previousSumElement) throws ParseException {
		int pos0 = position.intValue();
		Generic a[];

		final String name = ParserUtils.parseWithRollback(CompoundIdentifier.parser, expression, position, pos0, previousSumElement);
		if (FunctionsRegistry.getInstance().getNames().contains(name) || OperatorsRegistry.getInstance().getNames().contains(name)) {
			position.setValue(pos0);
			throw new ParseException(Messages.MSG_6, position.intValue(), expression, name);
		}

		final List<Generic> subscripts = new ArrayList<Generic>();
		while (true) {
			try {
				subscripts.add(Subscript.parser.parse(expression, position, previousSumElement));
			} catch (ParseException e) {
				break;
			}
		}

		int b[];
		try {
			b = Derivation.parser.parse(expression, position, previousSumElement);
		} catch (ParseException e) {
			b = new int[0];
		}
		try {
			a = ParameterListParser.parser.parse(expression, position, previousSumElement);
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

	public int[] parse(@NotNull String expression, @NotNull MutableInt position, Generic previousSumElement) throws ParseException {

		int result[];
		try {
			result = new int[]{PrimeCharacters.parser.parse(expression, position, previousSumElement)};
		} catch (ParseException e) {
			result = SuperscriptList.parser.parse(expression, position, previousSumElement);
		}

		return result;
	}
}

class SuperscriptList implements Parser<int[]> {

	public static final Parser<int[]> parser = new SuperscriptList();

	private SuperscriptList() {
	}

	public int[] parse(@NotNull String expression, @NotNull MutableInt position, Generic previousSumElement) throws ParseException {
		int pos0 = position.intValue();

		ParserUtils.tryToParse(expression, position, pos0, '{');

		final List<Integer> result = new ArrayList<Integer>();
		try {
			result.add(IntegerParser.parser.parse(expression, position, previousSumElement));
		} catch (ParseException e) {
			position.setValue(pos0);
			throw e;
		}

		while (true) {
			try {
				result.add(CommaAndInteger.parser.parse(expression, position, previousSumElement));
			} catch (ParseException e) {
				break;
			}
		}

		ParserUtils.tryToParse(expression, position, pos0, '}');

		ParserUtils.skipWhitespaces(expression, position);

		return ArrayUtils.toArray(result, new int[result.size()]);
	}
}

class CommaAndInteger implements Parser<Integer> {

	public static final Parser<Integer> parser = new CommaAndInteger();

	private CommaAndInteger() {
	}

	public Integer parse(@NotNull String expression, @NotNull MutableInt position, Generic previousSumElement) throws ParseException {
		int pos0 = position.intValue();

		ParserUtils.skipWhitespaces(expression, position);

		return ParserUtils.parseWithRollback(IntegerParser.parser, expression, position, pos0, previousSumElement);
	}
}

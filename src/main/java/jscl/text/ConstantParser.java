package jscl.text;

import jscl.math.Generic;
import jscl.math.function.Constant;
import jscl.util.ArrayUtils;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConstantParser implements Parser<Constant> {

	public static final Parser<Constant> parser = new ConstantParser();

	private ConstantParser() {
	}

	public Constant parse(@NotNull String string, @NotNull MutableInt position) throws ParseException {

		final String name = CompoundIdentifier.parser.parse(string, position);

		List<Generic> l = new ArrayList<Generic>();
		while (true) {
			try {
				l.add(Subscript.parser.parse(string, position));
			} catch (ParseException e) {
				break;
			}
		}

		Integer prime = 0;
		try {
			prime = Prime.parser.parse(string, position);
		} catch (ParseException e) {
		}

		return new Constant(name, prime, ArrayUtils.toArray(l, new Generic[l.size()]));
	}
}

class Prime implements Parser<Integer> {

	public static final Parser<Integer> parser = new Prime();

	private static final ArrayList<Parser<? extends Integer>> parsers = new ArrayList<Parser<? extends Integer>>(Arrays.asList(
			PrimeCharacters.parser,
			Superscript.parser));

	private static final Parser<Integer> internalParser = new MultiTryParser<Integer>(parsers);

	private Prime() {
	}

	public Integer parse(@NotNull String string,
						 @NotNull MutableInt position) throws ParseException {
		return internalParser.parse(string, position);
	}
}

class Superscript implements Parser<Integer> {
	public static final Parser<Integer> parser = new Superscript();

	private Superscript() {
	}

	public Integer parse(@NotNull String string, @NotNull MutableInt position) throws ParseException {
		int pos0 = position.intValue();


		ParserUtils.tryToParse(string, position, pos0, '{');

		int result;
		try {
			result = IntegerParser.parser.parse(string, position);
		} catch (ParseException e) {
			position.setValue(pos0);
			throw e;
		}

		ParserUtils.tryToParse(string, position, pos0, '}');

		return result;
	}
}

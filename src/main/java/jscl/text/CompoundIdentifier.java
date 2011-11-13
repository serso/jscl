package jscl.text;

import org.jetbrains.annotations.NotNull;

public class CompoundIdentifier implements Parser<String> {

	public static final Parser<String> parser = new CompoundIdentifier();

	private CompoundIdentifier() {
	}

	@NotNull
	public String parse(@NotNull String expression, @NotNull MutableInt position, int depth) throws ParseException {
		int pos0 = position.intValue();

		StringBuilder result = new StringBuilder();

		ParserUtils.skipWhitespaces(expression, position);
		try {
			String s = Identifier.parser.parse(expression, position, depth);
			result.append(s);
		} catch (ParseException e) {
			position.setValue(pos0);
			throw e;
		}

		while (true) {
			try {
				final String dotAndId = DotAndIdentifier.parser.parse(expression, position, depth);
				// NOTE: '.' must be appended after parsing
				result.append(".").append(dotAndId);
			} catch (ParseException e) {
				break;
			}
		}

		return result.toString();
	}
}

class DotAndIdentifier implements Parser<String> {

	public static final Parser<String> parser = new DotAndIdentifier();

	private DotAndIdentifier() {
	}

	public String parse(@NotNull String expression, @NotNull MutableInt position, int depth) throws ParseException {
		int pos0 = position.intValue();

		ParserUtils.tryToParse(expression, position, pos0, '.');

		String result;
		try {
			result = Identifier.parser.parse(expression, position, depth);
		} catch (ParseException e) {
			position.setValue(pos0);
			throw e;
		}

		return result;
	}
}

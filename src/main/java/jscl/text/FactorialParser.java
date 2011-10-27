package jscl.text;

import jscl.text.MutableInt;
import org.jetbrains.annotations.NotNull;

/**
 * User: serso
 * Date: 10/27/11
 * Time: 2:45 PM
 */
class FactorialParser implements Parser<FactorialParser.Result> {

	public static final Parser<Result> parser = new FactorialParser();

	private FactorialParser() {
	}

	@NotNull
	public Result parse(@NotNull String string, @NotNull MutableInt position) throws ParseException {
		int pos0 = position.intValue();

		final boolean factorial;

		ParserUtils.skipWhitespaces(string, position);

		if (position.intValue() < string.length() && string.charAt(position.intValue()) == '!') {
			position.increment();
			factorial = true;
		} else {
			position.setValue(pos0);
			factorial = false;
		}

		return new Result(factorial);
	}

	public static class Result {
		private final boolean factorial;

		public Result(boolean factorial) {
			this.factorial = factorial;
		}

		public boolean isFactorial() {
			return factorial;
		}
	}
}

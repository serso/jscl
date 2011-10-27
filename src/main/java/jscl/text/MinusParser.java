package jscl.text;

import jscl.text.MutableInt;
import org.jetbrains.annotations.NotNull;

/**
 * User: serso
 * Date: 10/27/11
 * Time: 2:44 PM
 */
class MinusParser implements Parser<MinusParser.Result> {

	public static final Parser<Result> parser = new MinusParser();

	private MinusParser() {
	}

	@NotNull
	public Result parse(@NotNull String string, @NotNull MutableInt position){
		final boolean result;

		int pos0 = position.intValue();

		ParserUtils.skipWhitespaces(string, position);

		if (position.intValue() < string.length() && string.charAt(position.intValue()) == '-') {
			result = true;
			position.increment();
		} else {
			result = false;
			position.setValue(pos0);
		}

		return new Result(result);
	}

	public static class Result {
		private final boolean sign;

		public Result(boolean sign) {
			this.sign = sign;
		}

		public boolean isSign() {
			return sign;
		}
	}
}

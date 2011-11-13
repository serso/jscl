package jscl.text;

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
	public Result parse(@NotNull String expression, @NotNull MutableInt position, int depth){
		final boolean result;

		int pos0 = position.intValue();

		ParserUtils.skipWhitespaces(expression, position);

		if (position.intValue() < expression.length() && expression.charAt(position.intValue()) == '-') {
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

package jscl.text;

import jscl.math.Generic;
import org.jetbrains.annotations.NotNull;

/**
 * User: serso
 * Date: 10/27/11
 * Time: 2:45 PM
 */
abstract class PostfixFunctionParser implements Parser<PostfixFunctionParser.Result> {

	@NotNull
	private final String postfixFunctionName;

	protected PostfixFunctionParser(@NotNull String postfixFunctionName) {
		this.postfixFunctionName = postfixFunctionName;
	}

	@NotNull
	public Result parse(@NotNull String string, @NotNull MutableInt position) throws ParseException {
		int pos0 = position.intValue();

		final boolean postfixFunction;

		ParserUtils.skipWhitespaces(string, position);

		if (position.intValue() < string.length() && string.startsWith(postfixFunctionName, position.intValue())) {
			position.add(postfixFunctionName.length());
			postfixFunction = true;
		} else {
			position.setValue(pos0);
			postfixFunction = false;
		}

		return new Result(postfixFunction);
	}

	@NotNull
	public abstract Generic newInstance(@NotNull Generic content);

	public static class Result {
		private final boolean postfixFunction;

		public Result(boolean postfixFunction) {
			this.postfixFunction = postfixFunction;
		}

		public boolean isPostfixFunction() {
			return postfixFunction;
		}
	}
}

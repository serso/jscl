package jscl.text;

import jscl.math.Generic;
import jscl.math.function.PostfixFunctionsRegistry;
import jscl.math.operator.Operator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * User: serso
 * Date: 10/27/11
 * Time: 2:45 PM
 */
public class PostfixFunctionParser implements Parser<PostfixFunctionParser.Result> {

	@NotNull
	private final String postfixFunctionName;

	protected PostfixFunctionParser(@NotNull String postfixFunctionName) {
		this.postfixFunctionName = postfixFunctionName;
	}

	@NotNull
	public Result parse(@NotNull String expression, @NotNull MutableInt position, int depth, Generic previousSumElement) throws ParseException {
		int pos0 = position.intValue();

		final boolean postfixFunction;

		ParserUtils.skipWhitespaces(expression, position);

		if (position.intValue() < expression.length() && expression.startsWith(postfixFunctionName, position.intValue())) {
			position.add(postfixFunctionName.length());
			postfixFunction = true;
		} else {
			position.setValue(pos0);
			postfixFunction = false;
		}

		return new Result(postfixFunctionName, postfixFunction);
	}

	public static class Result {

		@NotNull
		private final String postfixFunctionName;

		private final boolean postfixFunction;

		public Result(@NotNull String postfixFunctionName, boolean postfixFunction) {
			this.postfixFunctionName = postfixFunctionName;
			this.postfixFunction = postfixFunction;
		}

		public boolean isPostfixFunction() {
			return postfixFunction;
		}

		@NotNull
		public String getPostfixFunctionName() {
			return postfixFunctionName;
		}
	}
}

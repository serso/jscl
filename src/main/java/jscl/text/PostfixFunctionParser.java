package jscl.text;

import jscl.math.Generic;
import jscl.math.operator.Operator;
import org.jetbrains.annotations.NotNull;

/**
 * User: serso
 * Date: 10/27/11
 * Time: 2:45 PM
 */
public class PostfixFunctionParser implements Parser<PostfixFunctionParser.Result> {

	@NotNull
	private final Operator operator;

	protected PostfixFunctionParser(@NotNull Operator operator) {
		this.operator = operator;
	}

	@NotNull
	public Result parse(@NotNull String expression, @NotNull MutableInt position, int depth) throws ParseException {
		int pos0 = position.intValue();

		final boolean postfixFunction;

		ParserUtils.skipWhitespaces(expression, position);

		if (position.intValue() < expression.length() && expression.startsWith(operator.getName(), position.intValue())) {
			position.add(operator.getName().length());
			postfixFunction = true;
		} else {
			position.setValue(pos0);
			postfixFunction = false;
		}

		return new Result(postfixFunction);
	}

	@NotNull
	public Generic newInstance(@NotNull Generic content) {
		final Operator result = (Operator)operator.newInstance();
		result.setParameters(new Generic[]{content});
		return result.expressionValue();
	}

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

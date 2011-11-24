package jscl.text;

import jscl.math.Generic;
import jscl.math.function.PostfixFunctionsRegistry;
import jscl.math.operator.Operator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * User: serso
 * Date: 10/31/11
 * Time: 11:20 PM
 */
public class PostfixFunctionsParser implements Parser<Generic> {

	@NotNull
	private final Generic content;

	public PostfixFunctionsParser(@NotNull Generic content) {
		this.content = content;
	}

	public Generic parse(@NotNull String expression, @NotNull MutableInt position, Generic previousSumElement) throws ParseException {

		final List<String> postfixFunctionNames = PostfixFunctionsRegistry.getInstance().getNames();

		final List<PostfixFunctionParser> parsers = new ArrayList<PostfixFunctionParser>(postfixFunctionNames.size());
		for (String postfixFunctionName : postfixFunctionNames) {
			parsers.add(new PostfixFunctionParser(postfixFunctionName));
		}

		return parsePostfix(parsers, expression, position, content, previousSumElement);
	}

	private static Generic parsePostfix(@NotNull List<PostfixFunctionParser> parsers,
										@NotNull String expression,
										@NotNull MutableInt position,
										Generic content,
										@Nullable final Generic previousSumElement) throws ParseException {
		Generic result = content;

		for (PostfixFunctionParser parser : parsers) {
			final PostfixFunctionParser.Result postfixResult = parser.parse(expression, position, previousSumElement);
			if (postfixResult.isPostfixFunction()) {
				final Operator postfixFunction;

				if (previousSumElement == null) {
					postfixFunction = PostfixFunctionsRegistry.getInstance().get(postfixResult.getPostfixFunctionName(), new Generic[]{content});
				} else {
					postfixFunction = PostfixFunctionsRegistry.getInstance().get(postfixResult.getPostfixFunctionName(), new Generic[]{content, previousSumElement});
				}

				if (postfixFunction == null) {
					throw new ParseException(Messages.MSG_4, position.intValue(), expression, postfixResult.getPostfixFunctionName());
				}

				result = parsePostfix(parsers, expression, position, postfixFunction.expressionValue(), previousSumElement);
			}
		}

		return result;
	}
}

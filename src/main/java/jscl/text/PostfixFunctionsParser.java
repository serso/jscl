package jscl.text;

import jscl.math.Generic;
import jscl.math.function.PostfixFunctionsRegistry;
import jscl.math.operator.Operator;
import jscl.math.operator.TripleFactorial;
import jscl.text.msg.Messages;
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

	public Generic parse(@NotNull Parameters p, Generic previousSumElement) throws ParseException {

		final List<String> postfixFunctionNames = PostfixFunctionsRegistry.getInstance().getNames();

		final List<PostfixFunctionParser> parsers = new ArrayList<PostfixFunctionParser>(postfixFunctionNames.size());
		parsers.add(new PostfixFunctionParser(TripleFactorial.NAME));
		for (String postfixFunctionName : postfixFunctionNames) {
			parsers.add(new PostfixFunctionParser(postfixFunctionName));
		}

		return parsePostfix(parsers, content, previousSumElement, p);
	}

	private static Generic parsePostfix(@NotNull List<PostfixFunctionParser> parsers,
										Generic content,
										@Nullable final Generic previousSumElement,
										@NotNull final Parameters parseParameters) throws ParseException {
		Generic result = content;

		for (PostfixFunctionParser parser : parsers) {
			final PostfixFunctionParser.Result postfixResult = parser.parse(parseParameters, previousSumElement);
			if (postfixResult.isPostfixFunction()) {
				final Operator postfixFunction;

				if (previousSumElement == null) {
					postfixFunction = PostfixFunctionsRegistry.getInstance().get(postfixResult.getPostfixFunctionName(), new Generic[]{content});
				} else {
					postfixFunction = PostfixFunctionsRegistry.getInstance().get(postfixResult.getPostfixFunctionName(), new Generic[]{content, previousSumElement});
				}

				if (postfixFunction == null) {
					if (TripleFactorial.NAME.equals(postfixResult.getPostfixFunctionName())) {
						throw new ParseException(Messages.msg_18, parseParameters.getPosition().intValue(), parseParameters.getExpression());
					} else {
						throw new ParseException(Messages.msg_4, parseParameters.getPosition().intValue(), parseParameters.getExpression(), postfixResult.getPostfixFunctionName());
					}
				}

				result = parsePostfix(parsers, postfixFunction.expressionValue(), previousSumElement, parseParameters);
			}
		}

		return result;
	}
}

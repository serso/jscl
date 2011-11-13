package jscl.text;

import jscl.math.Generic;
import jscl.math.GenericVariable;
import jscl.math.function.PostfixFunctionsRegistry;
import jscl.math.operator.Operator;
import org.jetbrains.annotations.NotNull;

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

	public Generic parse(@NotNull String expression, @NotNull MutableInt position, int depth) throws ParseException {

		final List<PostfixFunctionParser> parsers = new ArrayList<PostfixFunctionParser>(PostfixFunctionsRegistry.getInstance().getEntities().size());
		for (Operator operator : PostfixFunctionsRegistry.getInstance().getEntities()) {
			parsers.add(new PostfixFunctionParser(operator));
		}

		return parsePostfix(parsers, expression, position, content, depth);
	}

	private static Generic parsePostfix(@NotNull List<PostfixFunctionParser> parsers,
										@NotNull String string,
										@NotNull MutableInt position,
										Generic content,
										int depth) throws ParseException {
		Generic result = content;

		for (PostfixFunctionParser postfixParser : parsers) {
			final PostfixFunctionParser.Result postfixResult = postfixParser.parse(string, position, depth);
			if (postfixResult.isPostfixFunction()) {
				result = parsePostfix(parsers, string, position, postfixParser.newInstance(GenericVariable.content(result, true)), depth);
			}
		}

		return result;
	}
}

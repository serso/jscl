package jscl.text;

import jscl.math.Generic;
import jscl.math.GenericVariable;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

/**
 * User: serso
 * Date: 10/31/11
 * Time: 11:20 PM
 */
public class PostfixFunctionsParser implements Parser<Generic> {

	@NotNull
	private final Generic content;

	private final static List<PostfixFunctionParser> postfixParsers = Arrays.asList(FactorialParser.parser, DegreeParser.parser);

	public PostfixFunctionsParser(@NotNull Generic content) {
		this.content = content;
	}

	public Generic parse(@NotNull String string, @NotNull MutableInt position) throws ParseException {
		return parsePostfix(string, position, content);
	}

	private static Generic parsePostfix(@NotNull String string, @NotNull MutableInt position, Generic content) throws ParseException {
		Generic result = content;

		for (PostfixFunctionParser postfixParser : postfixParsers) {
			final PostfixFunctionParser.Result postfixResult = postfixParser.parse(string, position);
			if (postfixResult.isPostfixFunction()) {
				result = parsePostfix(string, position, postfixParser.newInstance(GenericVariable.content(result, true)));
			}
		}

		return result;
	}
}

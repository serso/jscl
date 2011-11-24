package jscl.text;

import jscl.math.Generic;
import jscl.math.operator.Operator;
import jscl.math.operator.matrix.OperatorsRegistry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class OperatorParser implements Parser<Operator> {

	public static final Parser<Operator> parser = new OperatorParser();

	private OperatorParser() {
	}

	@NotNull
	public Operator parse(@NotNull String expression, @NotNull MutableInt position, Generic previousSumElement) throws ParseException {
		int pos0 = position.intValue();

		final String operatorName = Identifier.parser.parse(expression, position, previousSumElement);
		if (!valid(operatorName)) {
			ParserUtils.throwParseException(expression, position, pos0, Messages.MSG_3, operatorName);
		}

		final Generic parameters[] = ParserUtils.parseWithRollback(ParameterListParser.parser, expression, position, pos0, previousSumElement);

		final Operator result = OperatorsRegistry.getInstance().get(operatorName, parameters);
		if ( result == null ) {
			ParserUtils.throwParseException(expression, position, pos0, Messages.MSG_2, operatorName);
			assert false;
		}

		return result;
	}

	static boolean valid(@Nullable String name) {
		return name != null && OperatorsRegistry.getInstance().getNames().contains(name);
	}

}

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
	public Operator parse(@NotNull String expression, @NotNull MutableInt position, int depth, Generic previousSumElement) throws ParseException {
		int pos0 = position.intValue();

		final String operatorName = Identifier.parser.parse(expression, position, depth, previousSumElement);
		if (!valid(operatorName)) {
			position.setValue(pos0);
			throw new ParseException("There is no such operator name!", position, expression);
		}

		final Generic parameters[] = ParserUtils.parseWithRollback(ParameterListParser.parser, expression, position, depth, pos0, previousSumElement);

		final Operator result = OperatorsRegistry.getInstance().get(operatorName, parameters);
		if ( result == null ) {
			position.setValue(pos0);
			throw new ParseException("There is no such operator!", position, expression);
		}

		return result;
	}

	static boolean valid(@Nullable String name) {
		return name != null && OperatorsRegistry.getInstance().getNames().contains(name);
	}

}

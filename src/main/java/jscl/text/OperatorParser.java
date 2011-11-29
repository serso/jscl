package jscl.text;

import jscl.math.Generic;
import jscl.math.operator.Operator;
import jscl.math.operator.matrix.OperatorsRegistry;
import jscl.text.msg.Messages;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class OperatorParser implements Parser<Operator> {

	public static final Parser<Operator> parser = new OperatorParser();

	private OperatorParser() {
	}

	@NotNull
	public Operator parse(@NotNull Parameters p, Generic previousSumElement) throws ParseException {
		int pos0 = p.getPosition().intValue();

		final String operatorName = Identifier.parser.parse(p, previousSumElement);
		if (!valid(operatorName)) {
			ParserUtils.throwParseException(p, pos0, Messages.msg_3, operatorName);
		}

		final Generic parameters[] = ParserUtils.parseWithRollback(ParameterListParser.parser, pos0, previousSumElement, p);

		final Operator result = OperatorsRegistry.getInstance().get(operatorName, parameters);
		if ( result == null ) {
			ParserUtils.throwParseException(p, pos0, Messages.msg_2, operatorName);
			assert false;
		}

		return result;
	}

	static boolean valid(@Nullable String name) {
		return name != null && OperatorsRegistry.getInstance().getNames().contains(name);
	}

}

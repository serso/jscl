package jscl.text;

import jscl.math.Generic;
import jscl.util.ArrayUtils;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ParameterListParser implements Parser<Generic[]> {

	private final int minNumberOfParameters;

	public static final Parser<Generic[]> parser1 = new ParameterListParser();

	private ParameterListParser() {
		this.minNumberOfParameters = 1;
	}

	public ParameterListParser(int minNumberOfParameters) {
		this.minNumberOfParameters = minNumberOfParameters;
	}

	@NotNull
	public Generic[] parse(@NotNull Parameters p, Generic previousSumElement) throws ParseException {
		int pos0 = p.getPosition().intValue();

		final List<Generic> result = new ArrayList<Generic>();

		ParserUtils.tryToParse(p, pos0, '(');

		try {
			result.add(ExpressionParser.parser.parse(p, previousSumElement));
		} catch (ParseException e) {
			if (minNumberOfParameters > 0) {
				p.getPosition().setValue(pos0);
				throw e;
			}
		}

		while (true) {
			try {
				result.add(CommaAndExpression.parser.parse(p, previousSumElement));
			} catch (ParseException e) {
				break;
			}
		}

		ParserUtils.tryToParse(p, pos0, ')');


		return ArrayUtils.toArray(result, new Generic[result.size()]);
	}
}

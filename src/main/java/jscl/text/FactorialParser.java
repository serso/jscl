package jscl.text;

import jscl.math.Generic;
import jscl.math.operator.Factorial;
import org.jetbrains.annotations.NotNull;

/**
 * User: serso
 * Date: 10/31/11
 * Time: 11:25 PM
 */
public class FactorialParser extends PostfixFunctionParser {

	public static final PostfixFunctionParser parser = new FactorialParser();

	private FactorialParser() {
		super("!");
	}

	@NotNull
	@Override
	public Generic newInstance(@NotNull Generic content) {
		return new Factorial(content).expressionValue();
	}
}

package jscl.text;

import jscl.math.Generic;
import jscl.math.operator.Degree;
import org.jetbrains.annotations.NotNull;

/**
 * User: serso
 * Date: 10/31/11
 * Time: 11:27 PM
 */
public class DegreeParser extends PostfixFunctionParser {

	public static final PostfixFunctionParser parser = new DegreeParser();

	private DegreeParser() {
		super("°");
	}

	@NotNull
	@Override
	public Generic newInstance(@NotNull Generic content) {
		return new Degree(content).expressionValue();
	}
}

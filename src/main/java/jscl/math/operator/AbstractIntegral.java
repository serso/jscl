package jscl.math.operator;

import jscl.math.Generic;
import jscl.math.Variable;
import org.jetbrains.annotations.NotNull;

/**
 * User: serso
 * Date: 11/17/11
 * Time: 11:42 AM
 */
abstract class AbstractIntegral extends Operator {

	public static final String NAME = "integral";

	protected AbstractIntegral(Generic[] parameters) {
		super(NAME, parameters);
	}

	@NotNull
	@Override
	public Operator newInstance(@NotNull Generic[] parameters) {
		return parameters.length > 2 ? new Integral(parameters) : new IndefiniteIntegral(parameters);
	}
}

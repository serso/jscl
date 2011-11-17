package jscl.math.operator;

import jscl.math.Generic;
import jscl.math.Variable;
import org.jetbrains.annotations.NotNull;

/**
 * User: serso
 * Date: 11/17/11
 * Time: 11:42 AM
 */
public abstract class AbstractIntegral extends Operator {

	public static final String NAME = "∫";

	protected AbstractIntegral(Generic[] parameters) {
		super(NAME, parameters);
	}

	@NotNull
	@Override
	public Operator newInstance(@NotNull Generic[] parameters) {
		return parameters.length > 2 ? new Integral(parameters) : new IndefiniteIntegral(parameters);
	}

		@Override
	public int getMinimumNumberOfParameters() {
		return 2;
	}

	public static class RegistryInstance extends AbstractIntegral {

		public RegistryInstance() {
			super(new Generic[2]);
		}

		@Override
		public Generic compute() {
			throw new UnsupportedOperationException();
		}

		@Override
		public Variable newInstance() {
			return new RegistryInstance();
		}
	}
}

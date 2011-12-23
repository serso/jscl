package jscl.math.operator;

import jscl.math.Generic;
import jscl.math.JsclInteger;
import jscl.math.NotIntegerException;
import jscl.math.Variable;
import org.jetbrains.annotations.NotNull;

public class Modulo extends Operator {

	public static final String NAME = "mod";

	public Modulo(Generic first, Generic second) {
		super(NAME, new Generic[]{first, second});
	}

	private Modulo(Generic parameters[]) {
		super(NAME, parameters);
	}

	@Override
	public int getMinParameters() {
		return 2;
	}

	public Generic evaluate() {
		try {
			final JsclInteger first = parameters[0].integerValue();
			final JsclInteger second = parameters[1].integerValue();

			return first.mod(second);

		} catch (NotIntegerException e) {
		}
		return parameters[0].remainder(parameters[1]);
	}

	@NotNull
	@Override
	public Operator newInstance(@NotNull Generic[] parameters) {
		return new Modulo(parameters);
	}

	public Variable newInstance() {
		return new Modulo(null, null);
	}
}

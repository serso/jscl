package jscl.math.operator;

import jscl.math.Generic;
import jscl.math.Variable;
import org.jetbrains.annotations.NotNull;

public class Division extends Operator {

	public static final String NAME = "div";

	public Division(Generic expression1, Generic expression2) {
        super(NAME,new Generic[] {expression1,expression2});
    }

	private Division(Generic parameters[]) {
		super(NAME, parameters);
	}

	@Override
	public int getMinParameters() {
		return 2;
	}

	public Generic evaluate() {
        return parameters[0].divideAndRemainder(parameters[1])[0];
    }

	@NotNull
	@Override
	public Operator newInstance(@NotNull Generic[] parameters) {
		return new Division(parameters);
	}

	@NotNull
	public Variable newInstance() {
        return new Division(null,null);
    }
}

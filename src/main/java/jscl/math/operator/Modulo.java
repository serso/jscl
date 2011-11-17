package jscl.math.operator;

import jscl.math.Generic;
import jscl.math.JsclInteger;
import jscl.math.NotIntegerException;
import jscl.math.Variable;
import org.jetbrains.annotations.NotNull;

public class Modulo extends Operator {

	public static final String NAME = "mod";

	public Modulo(Generic expression1, Generic expression2) {
        super(NAME,new Generic[] {expression1,expression2});
    }

	private Modulo(Generic parameters[]) {
		super(NAME, parameters);
	}

	public Generic compute() {
        try {
            JsclInteger en= parameters[0].integerValue();
            JsclInteger en2= parameters[1].integerValue();
            return en.mod(en2);
        } catch (NotIntegerException e) {}
        return parameters[0].remainder(parameters[1]);
    }

	@NotNull
	@Override
	public Operator newInstance(@NotNull Generic[] parameters) {
		return new Modulo(parameters);
	}

	public Variable newInstance() {
        return new Modulo(null,null);
    }
}

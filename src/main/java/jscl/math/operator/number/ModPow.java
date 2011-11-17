package jscl.math.operator.number;

import jscl.math.Generic;
import jscl.math.JsclInteger;
import jscl.math.NotIntegerException;
import jscl.math.Variable;
import jscl.math.operator.Operator;
import org.jetbrains.annotations.NotNull;

public class ModPow extends Operator {

	public static final String NAME = "modpow";

	public ModPow(Generic integer, Generic exponent, Generic modulo) {
        super(NAME,new Generic[] {integer,exponent,modulo});
    }

	private ModPow(Generic parameters[]) {
		super(NAME, parameters);
	}

	public Generic compute() {
        try {
            JsclInteger en= parameters[0].integerValue();
            JsclInteger exponent= parameters[1].integerValue();
            JsclInteger modulo= parameters[2].integerValue();
            return en.modPow(exponent,modulo);
        } catch (NotIntegerException e) {}
        return expressionValue();
    }

	@NotNull
	@Override
	public Operator newInstance(@NotNull Generic[] parameters) {
		return new ModPow(parameters);
	}

	public Variable newInstance() {
        return new ModPow(null,null,null);
    }
}

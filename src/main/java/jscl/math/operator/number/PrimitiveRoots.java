package jscl.math.operator.number;

import jscl.math.*;
import jscl.math.JsclInteger;
import jscl.math.operator.Operator;
import org.jetbrains.annotations.NotNull;

public class PrimitiveRoots extends Operator {

	public static final String NAME = "primitiveroots";

	public PrimitiveRoots(Generic integer) {
        super(NAME,new Generic[] {integer});
    }

	private PrimitiveRoots(Generic parameters[]) {
		super(NAME, parameters);
	}

	@Override
	public int getMinimumNumberOfParameters() {
		return 1;
	}

	public Generic evaluate() {
        try {
            JsclInteger en= parameters[0].integerValue();
            Generic a[]=en.primitiveRoots();
            return new JsclVector(a.length>0?a:new Generic[] {JsclInteger.valueOf(0)});
        } catch (NotIntegerException e) {}
        return expressionValue();
    }

	@NotNull
	@Override
	public Operator newInstance(@NotNull Generic[] parameters) {
		return new PrimitiveRoots(parameters);
	}

	public Variable newInstance() {
        return new PrimitiveRoots((Generic)null);
    }
}

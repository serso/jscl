package jscl.math.operator.number;

import jscl.math.Generic;
import jscl.math.JsclInteger;
import jscl.math.NotIntegerException;
import jscl.math.Variable;
import jscl.math.operator.Operator;
import jscl.mathml.MathML;
import org.jetbrains.annotations.NotNull;

public class EulerPhi extends Operator {

	public static final String NAME = "eulerphi";

	public EulerPhi(Generic integer) {
        super(NAME,new Generic[] {integer});
    }

	private EulerPhi(Generic parameters[]) {
		super(NAME, parameters);
	}

	public Generic compute() {
        try {
            JsclInteger en= parameters[0].integerValue();
            return en.phi();
        } catch (NotIntegerException e) {}
        return expressionValue();
    }

    protected void nameToMathML(MathML element) {
        MathML e1=element.element("mi");
        e1.appendChild(element.text("\u03C6"));
        element.appendChild(e1);
    }

    public Variable newInstance() {
        return new EulerPhi((Generic)null);
    }

	@NotNull
	@Override
	public Operator newInstance(@NotNull Generic[] parameters) {
		return new EulerPhi(parameters);
	}
}

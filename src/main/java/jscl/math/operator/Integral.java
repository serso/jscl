package jscl.math.operator;

import jscl.math.Generic;
import jscl.math.NotIntegrableException;
import jscl.math.Variable;
import jscl.mathml.MathML;
import org.jetbrains.annotations.NotNull;

public class Integral extends AbstractIntegral {

	public Integral(Generic expression, Generic variable, Generic n1, Generic n2) {
        super(new Generic[] {expression,variable,n1,n2});
    }

	protected Integral(@NotNull Generic[] parameters) {
        super(parameters);
    }

	@Override
	public int getMinimumNumberOfParameters() {
		return 4;
	}

	public Generic compute() {
        Variable variable= parameters[1].variableValue();
        try {
            Generic a= parameters[0].antiDerivative(variable);
            return a.substitute(variable, parameters[3]).subtract(a.substitute(variable, parameters[2]));
        } catch (NotIntegrableException e) {}
        return expressionValue();
    }

    public void toMathML(MathML element, Object data) {
        int exponent=data instanceof Integer? (Integer) data :1;
        if(exponent==1) bodyToMathML(element);
        else {
            MathML e1=element.element("msup");
            MathML e2=element.element("mfenced");
            bodyToMathML(e2);
            e1.appendChild(e2);
            e2=element.element("mn");
            e2.appendChild(element.text(String.valueOf(exponent)));
            e1.appendChild(e2);
            element.appendChild(e1);
        }
    }

	void bodyToMathML(MathML element) {
        Variable v= parameters[1].variableValue();
        MathML e1=element.element("mrow");
        MathML e2=element.element("msubsup");
        MathML e3=element.element("mo");
        e3.appendChild(element.text("\u222B"));
        e2.appendChild(e3);
        parameters[2].toMathML(e2,null);
        parameters[3].toMathML(e2,null);
        e1.appendChild(e2);
        parameters[0].toMathML(e1,null);
        e2=element.element("mo");
        e2.appendChild(element.text(/*"\u2146"*/"d"));
        e1.appendChild(e2);
        v.toMathML(e1,null);
        element.appendChild(e1);
    }

    public Variable newInstance() {
        return new Integral(null,null,null,null);
    }
}

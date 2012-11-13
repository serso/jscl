package jscl.math.operator;

import jscl.math.Generic;
import jscl.math.NotIntegrableException;
import jscl.math.Variable;
import jscl.mathml.MathML;
import org.jetbrains.annotations.NotNull;

public class IndefiniteIntegral extends Operator {

    public static final String NAME = "∫";

    public IndefiniteIntegral(Generic expression, Generic variable) {
        super(NAME, new Generic[]{expression, variable});
    }

    protected IndefiniteIntegral(@NotNull Generic[] parameters) {
        super(NAME, parameters);
    }

    @Override
    public int getMinParameters() {
        return 2;
    }

    public Generic selfExpand() {
        Variable variable = parameters[1].variableValue();
        try {
            return parameters[0].antiDerivative(variable);
        } catch (NotIntegrableException e) {
        }
        return expressionValue();
    }

    @NotNull
    @Override
    protected String formatUndefinedParameter(int i) {
        switch (i) {
            case 0:
                return "f(x)";
            case 1:
                return "x";
            default:
                return super.formatUndefinedParameter(i);
        }
    }

    public void toMathML(MathML element, Object data) {
        int exponent = data instanceof Integer ? (Integer) data : 1;
        if (exponent == 1) bodyToMathML(element);
        else {
            MathML e1 = element.element("msup");
            MathML e2 = element.element("mfenced");
            bodyToMathML(e2);
            e1.appendChild(e2);
            e2 = element.element("mn");
            e2.appendChild(element.text(String.valueOf(exponent)));
            e1.appendChild(e2);
            element.appendChild(e1);
        }
    }

    @NotNull
    @Override
    public Operator newInstance(@NotNull Generic[] parameters) {
        return new IndefiniteIntegral(parameters);
    }

    @NotNull
    @Override
    public Variable newInstance() {
        return new IndefiniteIntegral(null, null);
    }

    void bodyToMathML(MathML element) {
        Variable v = parameters[1].variableValue();
        MathML e1 = element.element("mrow");
        MathML e2 = element.element("mo");
        e2.appendChild(element.text("\u222B"));
        e1.appendChild(e2);
        parameters[0].toMathML(e1, null);
        e2 = element.element("mo");
        e2.appendChild(element.text(/*"\u2146"*/"d"));
        e1.appendChild(e2);
        v.toMathML(e1, null);
        element.appendChild(e1);
    }
}

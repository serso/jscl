package jscl.math.function;

import jscl.math.*;
import jscl.math.JsclInteger;
import jscl.mathml.MathML;
import org.jetbrains.annotations.NotNull;

public class Cubic extends Algebraic {
    public Cubic(Generic generic) {
        super("cubic",new Generic[] {generic});
    }

    public Root rootValue() {
        return new Root(
            new Generic[] {
                parameters[0].negate(),
                JsclInteger.valueOf(0),
                JsclInteger.valueOf(0),
                JsclInteger.valueOf(1)
            },
            0
        );
    }

    public Generic antiDerivative(@NotNull Variable variable) throws NotIntegrableException {
        Root r=rootValue();
        Generic g[]=r.getParameters();
        if(g[0].isPolynomial(variable)) {
            return AntiDerivative.compute(r, variable);
        } else throw new NotIntegrableException();
    }

    public Generic derivative(int n) {
        return Constant.third.multiply(
            new Inv(
                evaluate().pow(2)
            ).evaluate()
        );
    }

    public Generic evaluate() {
        try {
            JsclInteger en= parameters[0].integerValue();
            if(en.signum()<0);
            else {
                Generic rt=en.nthrt(3);
                if(rt.pow(3).compareTo(en)==0) return rt;
            }
        } catch (NotIntegerException e) {}
        return expressionValue();
    }

    public Generic selfElementary() {
        return evaluate();
    }

    public Generic selfSimplify() {
        try {
            JsclInteger en= parameters[0].integerValue();
            if(en.signum()<0) return new Cubic(en.negate()).selfSimplify().negate();
            else {
                Generic rt=en.nthrt(3);
                if(rt.pow(3).compareTo(en)==0) return rt;
            }
        } catch (NotIntegerException e) {}
        return expressionValue();
    }

    public Generic selfNumeric() {
        return ((NumericWrapper) parameters[0]).nThRoot(3);
    }

    public String toJava() {
        StringBuffer buffer=new StringBuffer();
        buffer.append(parameters[0].toJava());
        buffer.append(".pow(");
        buffer.append(Constant.third.toJava());
        buffer.append(")");
        return buffer.toString();
    }

    void bodyToMathML(MathML element, boolean fenced) {
        MathML e1=element.element("mroot");
        parameters[0].toMathML(e1,null);
        JsclInteger.valueOf(3).toMathML(e1,null);
        element.appendChild(e1);
    }

    public Variable newInstance() {
        return new Cubic(null);
    }
}

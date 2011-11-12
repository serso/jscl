package jscl.math.function;

import jscl.math.*;
import jscl.math.JsclInteger;

public class Sgn extends Function {
    public Sgn(Generic generic) {
        super("sgn",new Generic[] {generic});
    }

    public Generic antiDerivative(int n) throws NotIntegrableException {
        return new Abs(parameters[0]).evaluate();
    }

    public Generic derivative(int n) {
        return JsclInteger.valueOf(0);
    }

    public Generic evaluate() {
        if(parameters[0].signum()<0) {
            return new Sgn(parameters[0].negate()).evaluate().negate();
        } else if(parameters[0].signum()==0) {
            return JsclInteger.valueOf(1);
        }
        try {
            return JsclInteger.valueOf(parameters[0].integerValue().signum());
        } catch (NotIntegerException e) {}
        return expressionValue();
    }

    public Generic evaluateElementary() {
        return new Frac(
            parameters[0],
            new Abs(parameters[0]).evaluateElementary()
        ).evaluateElementary();
    }

    public Generic evaluateSimplify() {
        if(parameters[0].signum()<0) {
            return new Sgn(parameters[0].negate()).evaluate().negate();
        } else if(parameters[0].signum()==0) {
            return JsclInteger.valueOf(1);
        }
        try {
            return JsclInteger.valueOf(parameters[0].integerValue().signum());
        } catch (NotIntegerException e) {}
        try {
            Variable v= parameters[0].variableValue();
            if(v instanceof Abs) {
                return JsclInteger.valueOf(1);
            } else if(v instanceof Sgn) {
                Function f=(Function)v;
                return f.evaluateSimplify();
            }
        } catch (NotVariableException e) {}
        return expressionValue();
    }

    public Generic evaluateNumerically() {
        return ((NumericWrapper) parameters[0]).sgn();
    }

    public String toJava() {
        StringBuffer buffer=new StringBuffer();
        buffer.append(parameters[0].toJava());
        buffer.append(".sgn()");
        return buffer.toString();
    }

    public Variable newInstance() {
        return new Sgn(null);
    }
}

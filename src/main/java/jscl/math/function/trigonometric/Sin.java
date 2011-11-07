package jscl.math.function.trigonometric;

import jscl.math.Generic;
import jscl.math.JsclInteger;
import jscl.math.NotIntegrableException;
import jscl.math.NotVariableException;
import jscl.math.NumericWrapper;
import jscl.math.Variable;
import jscl.math.function.Constant;
import jscl.math.function.Exp;
import jscl.math.function.Trigonometric;

public class Sin extends Trigonometric {
    public Sin(Generic generic) {
        super("sin",new Generic[] {generic});
    }

    public Generic antiderivative(int n) throws NotIntegrableException {
        return new Cos(parameter[0]).evaluate().negate();
    }

    public Generic derivative(int n) {
        return new Cos(parameter[0]).evaluate();
    }

    public Generic evaluate() {
        if(parameter[0].signum()<0) {
            return new Sin(parameter[0].negate()).evaluate().negate();
        } else if(parameter[0].signum()==0) {
            return JsclInteger.valueOf(0);
        } else if(parameter[0].compareTo(Constant.pi)==0) {
            return JsclInteger.valueOf(0);
        }
        return expressionValue();
    }

    public Generic evaluateElementary() {
        return new Exp(
            Constant.i.multiply(parameter[0])
        ).evaluateElementary().subtract(
            new Exp(
                Constant.i.multiply(parameter[0].negate())
            ).evaluateElementary()
        ).multiply(Constant.i.negate().multiply(Constant.half));
    }

    public Generic evaluateSimplify() {
        if(parameter[0].signum()<0) {
            return new Sin(parameter[0].negate()).evaluate().negate();
        } else if(parameter[0].signum()==0) {
            return JsclInteger.valueOf(0);
        } else if(parameter[0].compareTo(Constant.pi)==0) {
            return JsclInteger.valueOf(0);
        }
        try {
            Variable v=parameter[0].variableValue();
            if(v instanceof Asin) {
                Generic g[]=((Asin)v).parameters();
                return g[0];
            }
        } catch (NotVariableException e) {}
        return identity();
    }

    public Generic identity(Generic a, Generic b) {
        return new Cos(b).evaluateSimplify().multiply(
            new Sin(a).evaluateSimplify()
        ).add(
            new Cos(a).evaluateSimplify().multiply(
                new Sin(b).evaluateSimplify()
            )
        );
    }

    public Generic evaluateNumerically() {
        return ((NumericWrapper)parameter[0]).sin();
    }

    public Variable newInstance() {
        return new Sin(null);
    }
}

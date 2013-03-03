package jscl.math.function;

import jscl.math.*;
import jscl.math.JsclInteger;
import javax.annotation.Nonnull;

public class Lg extends Function {

    public Lg(Generic generic) {
        super("lg", new Generic[]{generic});
    }

    public Generic antiDerivative(int n) throws NotIntegrableException {
        // tmp = ln(x) - 1
        final Generic tmp = new Ln(parameters[0]).expressionValue().subtract(JsclInteger.ONE);

        // ln10 = ln (10)
        final Generic ln10 = new Ln(JsclInteger.valueOf(10L)).expressionValue();
        return new Fraction(parameters[0].multiply(tmp), ln10).expressionValue();
    }

    public Generic derivative(int n) {
        return new Inverse(parameters[0].multiply(new Ln(JsclInteger.valueOf(10L)).expressionValue())).expressionValue();
    }

    public Generic selfExpand() {
        if (parameters[0].compareTo(JsclInteger.valueOf(1)) == 0) {
            return JsclInteger.valueOf(0);
        }
        return expressionValue();
    }

    public Generic selfElementary() {
        return selfExpand();
    }

    public Generic selfSimplify() {
/*
        try {
            JsclInteger en = parameters[0].integerValue();
            if (en.signum() < 0) {
                return Constants.Generic.I_BY_PI.add(new Lg(en.negate()).selfSimplify());
            } else {
                Generic a = en.factorize();
                Generic p[] = a.productValue();
                Generic s = JsclInteger.valueOf(0);
                for (int i = 0; i < p.length; i++) {
                    Power o = p[i].powerValue();
                    s = s.add(JsclInteger.valueOf(o.exponent()).multiply(new Lg(o.value(true)).expressionValue()));
                }
                return s;
            }
        } catch (NotIntegerException e) {
        }
        try {
            Variable v = parameters[0].variableValue();
            if (v instanceof Sqrt) {
                Generic g[] = ((Sqrt) v).getParameters();
                return Constants.Generic.HALF.multiply(new Lg(g[0]).selfSimplify());
            }
        } catch (NotVariableException e) {
        }*/

        Generic coefficents[] = Fraction.separateCoefficient(parameters[0]);
        final Generic a = coefficents[0];
        final Generic b = coefficents[1];
        if (a.compareTo(JsclInteger.ONE) != 0 || b.compareTo(JsclInteger.ONE) != 0) {
            // lg ( a * c / b ) = lg ( c ) + lg( a ) - lg (b)
            final Generic c = coefficents[2];
            return new Lg(c).selfSimplify().add(new Lg(a).selfSimplify()).subtract(new Lg(b).selfSimplify());
        }
        return expressionValue();
    }

    public Generic selfNumeric() {
        return ((NumericWrapper) parameters[0]).lg();
    }

    @Nonnull
    public Variable newInstance() {
        return new Lg(null);
    }
}

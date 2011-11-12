package jscl.math.function;

import jscl.math.*;
import jscl.math.JsclInteger;
import jscl.mathml.MathML;

public class Conjugate extends Function {
    public Conjugate(Generic generic) {
        super("conjugate",new Generic[] {generic});
    }

    public Generic antiDerivative(int n) throws NotIntegrableException {
        return Constant.half.multiply(evaluate().pow(2));
    }

    public Generic derivative(int n) {
        return JsclInteger.valueOf(1);
    }

    public Generic evaluate() {
        try {
            return parameter[0].integerValue();
        } catch (NotIntegerException e) {}
        if(parameter[0] instanceof Matrix) {
            return ((Matrix)parameter[0]).conjugate();
        } else if(parameter[0] instanceof JsclVector) {
            return ((JsclVector)parameter[0]).conjugate();
        }
        return expressionValue();
    }

    public Generic evaluateElementary() {
        try {
            return parameter[0].integerValue();
        } catch (NotIntegerException e) {}
        return expressionValue();
    }

    public Generic evaluateSimplify() {
        try {
            return parameter[0].integerValue();
        } catch (NotIntegerException e) {}
        if(parameter[0].signum()<0) {
            return new Conjugate(parameter[0].negate()).evaluateSimplify().negate();
        } else if(parameter[0].compareTo(Constant.i)==0) {
            return Constant.i.negate();
        }
        try {
            Variable v=parameter[0].variableValue();
            if(v instanceof Conjugate) {
                Generic g[]=((Conjugate)v).parameters();
                return g[0];
            } else if(v instanceof Exp) {
                Generic g[]=((Exp)v).parameters();
                return new Exp(new Conjugate(g[0]).evaluateSimplify()).evaluateSimplify();
            } else if(v instanceof Lg) {
                Generic g[]=((Lg)v).parameters();
                return new Lg(new Conjugate(g[0]).evaluateSimplify()).evaluateSimplify();
            }
        } catch (NotVariableException e) {
            Generic a[]=parameter[0].sumValue();
            if(a.length>1) {
                Generic s= JsclInteger.valueOf(0);
                for(int i=0;i<a.length;i++) {
                    s=s.add(new Conjugate(a[i]).evaluateSimplify());
                }
                return s;
            } else {
                Generic p[]=a[0].productValue();
                Generic s= JsclInteger.valueOf(1);
                for(int i=0;i<p.length;i++) {
                    Power o=p[i].powerValue();
                    s=s.multiply(new Conjugate(o.value()).evaluateSimplify().pow(o.exponent()));
                }
                return s;
            }
        }
        Generic n[]=Frac.separateCoefficient(parameter[0]);
        if(n[0].compareTo(JsclInteger.valueOf(1))==0 && n[1].compareTo(JsclInteger.valueOf(1))==0);
        else return new Conjugate(n[2]).evaluateSimplify().multiply(
            new Frac(n[0],n[1]).evaluateSimplify()
        );
        return expressionValue();
    }

    public Generic evaluateNumerically() {
        return ((NumericWrapper)parameter[0]).conjugate();
    }

    public String toJava() {
        StringBuffer buffer=new StringBuffer();
        buffer.append(parameter[0].toJava());
        buffer.append(".conjugate()");
        return buffer.toString();
    }

    public void toMathML(MathML element, Object data) {
        int exponent=data instanceof Integer?((Integer)data).intValue():1;
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
        MathML e1=element.element("mover");
        parameter[0].toMathML(e1,null);
        MathML e2=element.element("mo");
        e2.appendChild(element.text("_"));
        e1.appendChild(e2);
        element.appendChild(e1);
    }

    public Variable newInstance() {
        return new Conjugate(null);
    }
}

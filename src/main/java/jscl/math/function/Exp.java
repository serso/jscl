package jscl.math.function;

import jscl.math.*;
import jscl.math.JsclInteger;
import jscl.math.polynomial.Polynomial;
import jscl.mathml.MathML;

public class Exp extends Function {
    public Exp(Generic generic) {
        super("exp",new Generic[] {generic});
    }

    public Generic antiDerivative(Variable variable) throws NotIntegrableException {
        Generic s= parameters[0];
        if(s.isPolynomial(variable)) {
            Polynomial p=Polynomial.factory(variable).valueof(s);
            if(p.degree()==1) {
                Generic a[]=p.elements();
                return new Inv(a[1]).evaluate().multiply(antiDerivative(0));
            } else throw new NotIntegrableException();
        } else throw new NotIntegrableException();
    }

    public Generic antiDerivative(int n) throws NotIntegrableException {
        return evaluate();
    }

    public Generic derivative(int n) {
        return evaluate();
    }

    public Generic evaluate() {
        if(parameters[0].signum()<0) {
            return new Inv(new Exp(parameters[0].negate()).evaluate()).evaluate();
        } else if(parameters[0].signum()==0) {
            return JsclInteger.valueOf(1);
        }
        return expressionValue();
    }

    public Generic evaluateElementary() {
        return evaluate();
    }

    public Generic evaluateSimplify() {

		if (parameters[0].signum() < 0) {
			return new Inv(new Exp(parameters[0].negate()).evaluateSimplify()).evaluateSimplify();
		} else if (parameters[0].signum() == 0) {
			return JsclInteger.valueOf(1);
		} else if (parameters[0].compareTo(Constant.i.multiply(Constant.pi)) == 0) {
			return JsclInteger.valueOf(-1);
		}

        try {
            Variable v= parameters[0].variableValue();
            if(v instanceof Lg) {
                Generic g[]=((Lg)v).getParameters();
                return g[0];
            }
        } catch (NotVariableException e) {
            Generic sumElements[]= parameters[0].sumValue();
			if (sumElements.length > 1) {
				Generic result = JsclInteger.valueOf(1);
				for (Generic sumElement : sumElements) {
					result = result.multiply(new Exp(sumElement).evaluateSimplify());
				}
				return result;
			}
        }

        Generic n[]=Frac.separateCoefficient(parameters[0]);
        if(n[0].compareTo(JsclInteger.valueOf(1))==0 && n[1].compareTo(JsclInteger.valueOf(1))==0);
        else return new Pow(
            new Exp(n[2]).evaluateSimplify(),
            new Frac(n[0],n[1]).evaluateSimplify()
        ).evaluateSimplify();
        return expressionValue();
    }

    public Generic evaluateNumerically() {
        return ((NumericWrapper) parameters[0]).exp();
    }

    public void toMathML(MathML element, Object data) {
        int exponent=data instanceof Integer?((Integer)data).intValue():1;
        if(exponent==1) bodyToMathML(element,false);
        else {
            MathML e1=element.element("msup");
            bodyToMathML(e1,true);
            MathML e2=element.element("mn");
            e2.appendChild(element.text(String.valueOf(exponent)));
            e1.appendChild(e2);
            element.appendChild(e1);
        }
    }

    void bodyToMathML(MathML element, boolean fenced) {
        if(fenced) {
            MathML e1=element.element("mfenced");
            bodyToMathML(e1);
            element.appendChild(e1);
        } else {
            bodyToMathML(element);
        }
    }

    void bodyToMathML(MathML element) {
        MathML e1=element.element("msup");
        MathML e2=element.element("mi");
        e2.appendChild(element.text(/*"\u2147"*/"e"));
        e1.appendChild(e2);
        parameters[0].toMathML(e1,null);
        element.appendChild(e1);
    }
    
    public Variable newInstance() {
        return new Exp(null);
    }
}

package jscl.math.function;

import jscl.math.*;
import jscl.math.JsclInteger;
import jscl.mathml.MathML;

public class Comparison extends Function {
    int operator;

    public Comparison(String name, Generic expression1, Generic expression2) {
        super(name,new Generic[] {expression1,expression2});
		for (int i = 0; i < easo.length; i++) if (name.compareTo(easo[i]) == 0) operator = i;
	}

    public Generic antiderivative(int n) throws NotIntegrableException {
        throw new NotIntegrableException();
    }

    public Generic derivative(int n) {
        return JsclInteger.valueOf(0);
    }

    public Generic evaluate() {
        try {
            return compare(parameter[0].integerValue(),parameter[1].integerValue());
        } catch (NotIntegerException e) {}
        return expressionValue();
    }

    public Generic evaluateElementary() {
        return expressionValue();
    }

    public Generic evaluateSimplify() {
        return expressionValue();
    }

    public Generic evaluateNumerically() {
        return compare((NumericWrapper)parameter[0],(NumericWrapper)parameter[1]);
    }

    JsclInteger compare(JsclInteger a1, JsclInteger a2) {
        return JsclInteger.valueOf(compare((Generic) a1, (Generic) a2) ? 1 : 0);
    }

    NumericWrapper compare(NumericWrapper a1, NumericWrapper a2) {
        return new NumericWrapper(JsclInteger.valueOf(compare((Generic) a1, (Generic) a2) ? 1 : 0));
    }

    boolean compare(Generic a1, Generic a2) {
        switch(operator) {
            case 0:
                return a1.compareTo(a2)==0;
            case 1:
                return a1.compareTo(a2)<=0;
            case 2:
                return a1.compareTo(a2)>=0;
            case 3:
                return a1.compareTo(a2)!=0;
            case 4:
                return a1.compareTo(a2)<0;
            case 5:
                return a1.compareTo(a2)>0;
            case 6:
                return a1.compareTo(a2)==0;
            default:
                return false;
        }
    }

    public String toJava() {
        StringBuffer buffer=new StringBuffer();
        buffer.append(parameter[0].toJava()).append(easj[operator]).append(parameter[1].toJava());
        return buffer.toString();
    }

    public void toMathML(MathML element, Object data) {
        parameter[0].toMathML(element,null);
        MathML e1=element.element("mo");
        e1.appendChild(element.text(easm[operator]));
        element.appendChild(e1);
        parameter[1].toMathML(element,null);
    }

    public Variable newInstance() {
        return new Comparison(name,null,null);
    }

    private static final String eass[]={"=","<=",">=","<>","<",">","~"};
    private static final String easj[]={"==","<=",">=","!=","<",">","=="};
    private static final String easm[]={"=","\u2264","\u2265","\u2260","<",">","\u2248"};
    private static final String easo[]={"eq","le","ge","ne","lt","gt","ap"};
}

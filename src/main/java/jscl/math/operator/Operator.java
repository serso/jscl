package jscl.math.operator;

import jscl.math.*;
import jscl.math.JsclInteger;
import jscl.mathml.MathML;
import jscl.util.ArrayComparator;

public abstract class Operator extends Variable {
    protected Generic parameter[];

    public Operator(String name, Generic parameter[]) {
        super(name);
        this.parameter=parameter;
    }

    public Generic[] parameters() {
        return parameter;
    }

	public void setParameter(Generic[] parameter) {
		this.parameter = parameter;
	}

	public abstract Generic compute();

    public Generic antiDerivative(Variable variable) throws NotIntegrableException {
        return null;
    }

    public Generic derivative(Variable variable) {
        if(isIdentity(variable)) return JsclInteger.valueOf(1);
        else return JsclInteger.valueOf(0);
    }

    public Generic substitute(Variable variable, Generic generic) {
        Operator v=(Operator) newInstance();
        for(int i=0;i<parameter.length;i++) {
            v.parameter[i]=parameter[i].substitute(variable,generic);
        }
        if(v.isIdentity(variable)) return generic;
        else return v.compute();
    }

    public Generic expand() {
        Operator v=(Operator) newInstance();
        for(int i=0;i<parameter.length;i++) {
            v.parameter[i]=parameter[i].expand();
        }
        return v.compute();
    }

    public Generic factorize() {
        Operator v=(Operator) newInstance();
        for(int i=0;i<parameter.length;i++) {
            v.parameter[i]=parameter[i].factorize();
        }
        return v.expressionValue();
    }

    public Generic elementary() {
        Operator v=(Operator) newInstance();
        for(int i=0;i<parameter.length;i++) {
            v.parameter[i]=parameter[i].elementary();
        }
        return v.expressionValue();
    }

    public Generic simplify() {
        Operator v=(Operator) newInstance();
        for(int i=0;i<parameter.length;i++) {
            v.parameter[i]=parameter[i].simplify();
        }
        return v.expressionValue();
    }

    public Generic numeric() {
        throw new ArithmeticException();
    }

    public boolean isConstant(Variable variable) {
        return !isIdentity(variable);
    }

	public int compareTo(Variable variable) {
		if (this == variable) {
			return 0;
		}

		int result = comparator.compare(this, variable);
		if (result < 0) {
			return -1;
		} else if (result > 0) {
			return 1;
		} else {
			result = name.compareTo(((Operator) variable).name);
			if (result < 0) {
				return -1;
			} else if (result > 0) {
				return 1;
			} else {
				return ArrayComparator.comparator.compare(parameter, ((Operator) variable).parameter);
			}
		}
	}

	protected static Variable[] variables(Generic generic) throws NotVariableException {
        Generic element[]=((JsclVector)generic).elements();
        Variable variable[]=new Variable[element.length];
        for(int i=0;i<element.length;i++) {
            variable[i]=element[i].variableValue();
        }
        return variable;
    }

    public String toString() {
        StringBuffer buffer=new StringBuffer();
        buffer.append(name);
        buffer.append("(");
        for(int i=0;i<parameter.length;i++) {
            buffer.append(parameter[i]).append(i<parameter.length-1?", ":"");
        }
        buffer.append(")");
        return buffer.toString();
    }

    public String toJava() {
        throw new ArithmeticException();
    }

    public void toMathML(MathML element, Object data) {
        MathML e1;
        int exponent=data instanceof Integer?((Integer)data).intValue():1;
        if(exponent==1) nameToMathML(element);
        else {
            e1=element.element("msup");
            nameToMathML(e1);
            MathML e2=element.element("mn");
            e2.appendChild(element.text(String.valueOf(exponent)));
            e1.appendChild(e2);
            element.appendChild(e1);
        }
        e1=element.element("mfenced");
        for(int i=0;i<parameter.length;i++) {
            parameter[i].toMathML(e1,null);
        }
        element.appendChild(e1);
    }
}

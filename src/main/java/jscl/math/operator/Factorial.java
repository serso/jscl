package jscl.math.operator;

import jscl.math.*;
import jscl.math.function.Frac;
import jscl.math.function.Pow;
import jscl.mathml.MathML;
import jscl.text.ParserUtils;
import jscl.util.ArrayComparator;

public class Factorial extends Operator {
    public Factorial(Generic expression) {
        super("",new Generic[] {expression});
    }

    public Generic compute() {
		try {
			return numeric();
		} catch (NotIntegerException e) {
		}

		return expressionValue();
    }



    public int compareTo(Variable variable) {
        if(this==variable) return 0;
        int c=comparator.compare(this,variable);
        if(c<0) return -1;
        else if(c>0) return 1;
        else {
            Factorial v=(Factorial)variable;
            return ArrayComparator.comparator.compare(parameter,v.parameter);
        }
    }

	@Override
	public Generic numeric() {
		Generic numeric = parameter[0].numeric();
		if (numeric.isInteger()) {
			int n = numeric.integerValue().intValue();
			if (n < 0) {
				//return expressionValue();
				throw new ArithmeticException("Cannot take factorial from negative integer!");
			}

			Generic a = JSCLInteger.valueOf(1);
			for (int i = 0; i < n; i++) {
				ParserUtils.checkInterruption();
				a = a.multiply(JSCLInteger.valueOf(i + 1));
			}
			if (a instanceof JSCLInteger) {
				return new NumericWrapper(((JSCLInteger) a));
			} else {
				throw new NotIntegerException();
			}
		} else {
			throw new NotIntegerException();
		}
	}

	public String toString() {
        StringBuffer buffer=new StringBuffer();
        try {
            JSCLInteger en=parameter[0].integerValue();
            buffer.append(en);
        } catch (NotIntegerException e) {
            try {
                Variable v=parameter[0].variableValue();
                if(v instanceof Frac || v instanceof Pow) {
                    buffer.append(GenericVariable.valueOf(parameter[0]));
                } else buffer.append(v);
            } catch (NotVariableException e2) {
                buffer.append(GenericVariable.valueOf(parameter[0]));
            }
        }
        buffer.append("!");
        return buffer.toString();
    }

    public void toMathML(MathML element, Object data) {
        int exponent=data instanceof Integer?((Integer)data).intValue():1;
        if(exponent==1) bodyToMathML(element);
        else {
            MathML e1=element.element("msup");
            bodyToMathML(e1);
            MathML e2=element.element("mn");
            e2.appendChild(element.text(String.valueOf(exponent)));
            e1.appendChild(e2);
            element.appendChild(e1);
        }
    }

    void bodyToMathML(MathML element) {
        MathML e1=element.element("mrow");
        try {
            JSCLInteger en=parameter[0].integerValue();
            en.toMathML(e1,null);
        } catch (NotIntegerException e) {
            try {
                Variable v=parameter[0].variableValue();
                if(v instanceof Pow) {
                    GenericVariable.valueOf(parameter[0]).toMathML(e1,null);
                } else v.toMathML(e1,null);
            } catch (NotVariableException e2) {
                GenericVariable.valueOf(parameter[0]).toMathML(e1,null);
            }
        }
        MathML e2=element.element("mo");
        e2.appendChild(element.text("!"));
        e1.appendChild(e2);
        element.appendChild(e1);
    }

    public Variable newInstance() {
        return new Factorial(null);
    }
}

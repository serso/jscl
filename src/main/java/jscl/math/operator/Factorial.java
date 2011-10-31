package jscl.math.operator;

import jscl.math.*;
import jscl.math.function.Frac;
import jscl.math.function.Pow;
import jscl.mathml.MathML;
import jscl.text.ParserUtils;

public class Factorial extends Operator {

	public Factorial(Generic expression) {
        super("!",new Generic[] {expression});
    }

    public Generic compute() {
		try {
			return numeric();
		} catch (NotIntegerException e) {
		}

		return expressionValue();
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
		final StringBuilder result = new StringBuilder();
		try {
			result.append(parameter[0].integerValue());
		} catch (NotIntegerException e) {
			try {
				final Variable v = parameter[0].variableValue();
				if (v instanceof Frac || v instanceof Pow) {
					result.append(GenericVariable.valueOf(parameter[0]));
				} else {
					result.append(v);
				}
			} catch (NotVariableException e2) {
				result.append(GenericVariable.valueOf(parameter[0]));
			}
		}
		result.append("!");
		return result.toString();
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

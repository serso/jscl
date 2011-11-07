package jscl.math.operator;

import jscl.math.*;
import jscl.math.function.Pow;
import jscl.mathml.MathML;
import jscl.text.ParserUtils;

public class Factorial extends PostfixFunction {

	public static final String NAME = "!";

	public Factorial(Generic expression) {
        super(NAME,new Generic[] {expression});
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

			Generic a = JsclInteger.valueOf(1);
			for (int i = 0; i < n; i++) {
				ParserUtils.checkInterruption();
				a = a.multiply(JsclInteger.valueOf(i + 1));
			}
			if (a instanceof JsclInteger) {
				return new NumericWrapper(((JsclInteger) a));
			} else {
				throw new NotIntegerException();
			}
		} else {
			throw new NotIntegerException();
		}
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
            JsclInteger en=parameter[0].integerValue();
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

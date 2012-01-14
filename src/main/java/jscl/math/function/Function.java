package jscl.math.function;

import jscl.math.Generic;
import jscl.math.JsclInteger;
import jscl.math.NotIntegrableException;
import jscl.math.Variable;
import jscl.math.operator.AbstractFunction;
import jscl.text.ParserUtils;
import org.jetbrains.annotations.NotNull;
import org.solovyev.common.math.MathEntity;

public abstract class Function extends AbstractFunction {

	protected Function(String name, Generic parameters[]) {
		super(name, parameters);
	}

	public int getMinParameters(){
		return 1;
	}

	@Override
	public void copy(@NotNull MathEntity that) {
		super.copy(that);
		if (that instanceof Function) {
			if (((Function) that).parameters != null) {
				this.parameters = ParserUtils.copyOf(((Function) that).parameters);
			} else {
				this.parameters = null;
			}
		}
	}

	public Generic antiDerivative(@NotNull Variable variable) throws NotIntegrableException {
		final int parameter = getParameterForAntiDerivation(variable);

		if (parameter < 0) {
			throw new NotIntegrableException();
		} else {
			return antiDerivative(parameter);
		}
	}

	protected int getParameterForAntiDerivation(@NotNull Variable variable) {
		int result = -1;

		for (int i = 0; i < parameters.length; i++) {
			if (result == -1 && parameters[i].isIdentity(variable)) {
				// found!
				result = i;
			} else if (!parameters[i].isConstant(variable)) {
				result = -1;
				break;
			}
		}

		return result;
	}

	public abstract Generic antiDerivative(int n) throws NotIntegrableException;

	@NotNull
	public Generic derivative(@NotNull Variable variable) {
		if (isIdentity(variable)) {
			return JsclInteger.valueOf(1);
		} else {
			Generic result = JsclInteger.valueOf(0);

			for (int i = 0; i < parameters.length; i++) {
				// chain rule: f(x) = g(h(x)) => f'(x) = g'(h(x)) * h'(x)
				// hd = h'(x)
				// gd = g'(x)
				final Generic hd = parameters[i].derivative(variable);
				final Generic gd = this.derivative(i);

				result = result.add(hd.multiply(gd));
			}

			return result;
		}
	}

	public abstract Generic derivative(int n);

	public Generic numeric() {
		final Function result = (Function) newInstance();

		for (int i = 0; i < parameters.length; i++) {
			result.parameters[i] = parameters[i].numeric();
		}

		return result.selfNumeric();
	}

	public abstract Generic selfNumeric();


	public boolean isConstant(Variable variable) {
		boolean result = !isIdentity(variable);

		if (result) {
			for (Generic parameter : parameters) {
				if ( !parameter.isConstant(variable) ) {
					result = false;
					break;
				}
			}
		}

		return result;
	}
}

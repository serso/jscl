package jscl.math.function;

import jscl.math.Generic;
import jscl.math.JsclInteger;
import jscl.math.NotIntegrableException;
import jscl.math.Variable;
import jscl.mathml.MathML;
import jscl.util.ArrayComparator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.solovyev.common.math.MathEntity;

import java.util.Arrays;

public abstract class Function extends Variable {

	private final static String variableNames = "xyzabcdefghijklmnopqrstuvw";

	protected Generic parameters[];

	public Function(String name, Generic parameters[]) {
		super(name);
		this.parameters = parameters;
	}

	public Generic[] getParameters() {
		return parameters;
	}

	public void setParameters(Generic[] parameters) {
		this.parameters = parameters;
	}

	@Override
	public void copy(@NotNull MathEntity that) {
		super.copy(that);
		if (that instanceof Function) {
			if (((Function) that).parameters != null) {
				this.parameters = Arrays.copyOf(((Function) that).parameters, ((Function) that).parameters.length);
			} else {
				this.parameters = null;
			}
		}
	}

	public abstract Generic evaluate();

	public abstract Generic evaluateElementary();

	public abstract Generic evaluateSimplify();

	public abstract Generic evaluateNumerically();

	public Generic antiDerivative(Variable variable) throws NotIntegrableException {
		int n = -1;
		for (int i = 0; i < parameters.length; i++) {
			if (n == -1 && parameters[i].isIdentity(variable)) n = i;
			else if (!parameters[i].isConstant(variable)) {
				n = -1;
				break;
			}
		}
		if (n < 0) throw new NotIntegrableException();
		else return antiDerivative(n);
	}

	public abstract Generic antiDerivative(int n) throws NotIntegrableException;

	public Generic derivative(Variable variable) {
		if (isIdentity(variable)) return JsclInteger.valueOf(1);
		else {
			Generic a = JsclInteger.valueOf(0);
			for (int i = 0; i < parameters.length; i++) {
				a = a.add(parameters[i].derivative(variable).multiply(derivative(i)));
			}
			return a;
		}
	}

	public abstract Generic derivative(int n);

	public Generic substitute(Variable variable, Generic generic) {
		Function v = (Function) newInstance();
		for (int i = 0; i < parameters.length; i++) {
			v.parameters[i] = parameters[i].substitute(variable, generic);
		}

		if (v.isIdentity(variable)) {
			return generic;
		} else {
			return v.evaluate();
		}
	}

	public Generic expand() {
		Function v = (Function) newInstance();
		for (int i = 0; i < parameters.length; i++) {
			v.parameters[i] = parameters[i].expand();
		}
		return v.evaluate();
	}

	public Generic factorize() {
		Function v = (Function) newInstance();
		for (int i = 0; i < parameters.length; i++) {
			v.parameters[i] = parameters[i].factorize();
		}
		return v.expressionValue();
	}

	public Generic elementary() {
		Function v = (Function) newInstance();
		for (int i = 0; i < parameters.length; i++) {
			v.parameters[i] = parameters[i].elementary();
		}
		return v.evaluateElementary();
	}

	public Generic simplify() {
		Function v = (Function) newInstance();
		for (int i = 0; i < parameters.length; i++) {
			v.parameters[i] = parameters[i].simplify();
		}
		return v.evaluateSimplify();
	}

	public Generic numeric() {
		Function v = (Function) newInstance();
		for (int i = 0; i < parameters.length; i++) {
			v.parameters[i] = parameters[i].numeric();
		}
		return v.evaluateNumerically();
	}

	public boolean isConstant(Variable variable) {
		boolean s = !isIdentity(variable);
		for (Generic parameter : parameters) {
			s = s && parameter.isConstant(variable);
		}
		return s;
	}

	public int compareTo(Variable variable) {
		if (this == variable) return 0;
		int c = comparator.compare(this, variable);
		if (c < 0) return -1;
		else if (c > 0) return 1;
		else {
			Function v = (Function) variable;
			c = name.compareTo(v.name);
			if (c < 0) return -1;
			else if (c > 0) return 1;
			else return ArrayComparator.comparator.compare(parameters, v.parameters);
		}
	}

	public String toString() {
		final StringBuilder result = new StringBuilder();
		result.append(name);
		result.append("(");
		for (int i = 0; i < parameters.length; i++) {
			result.append(substituteParameter(i)).append(i < parameters.length - 1 ? ", " : "");
		}
		result.append(")");
		return result.toString();
	}

	private String substituteParameter(int i) {
		Generic parameter = parameters[i];

		String result;
		if (parameter != null) {
			result = parameter.toString();
		} else {
			result = substituteUndefinedParameter(i);
		}

		return result;
	}

	@NotNull
	protected String substituteUndefinedParameter(int i) {
		return String.valueOf(variableNames.charAt(i - (i / variableNames.length()) * variableNames.length()));
	}

	public String toJava() {
		StringBuilder buffer = new StringBuilder();
		buffer.append(parameters[0].toJava());
		buffer.append(".").append(name).append("()");
		return buffer.toString();
	}

	public void toMathML(MathML element, Object data) {
		MathML e1;
		int exponent = data instanceof Integer ? (Integer) data : 1;
		if (exponent == 1) nameToMathML(element);
		else {
			e1 = element.element("msup");
			nameToMathML(e1);
			MathML e2 = element.element("mn");
			e2.appendChild(element.text(String.valueOf(exponent)));
			e1.appendChild(e2);
			element.appendChild(e1);
		}
		e1 = element.element("mfenced");
		for (Generic parameter : parameters) {
			parameter.toMathML(e1, null);
		}
		element.appendChild(e1);
	}

	@Nullable
	public static Generic getParameter(@Nullable Generic[] parameters, final int i) {
		return parameters == null ? null : (parameters.length > i ? parameters[i] : null);
	}
}

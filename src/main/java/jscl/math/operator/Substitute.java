package jscl.math.operator;

import jscl.math.Generic;
import jscl.math.GenericVariable;
import jscl.math.JsclVector;
import jscl.math.Variable;
import org.jetbrains.annotations.NotNull;

public class Substitute extends Operator {

	public static final String NAME = "subst";

	public Substitute(Generic expression, Generic variable, Generic value) {
		super(NAME, new Generic[]{expression, variable, value});
	}

	private Substitute(Generic parameters[]) {
		super(NAME, parameters);
	}

	@Override
	public int getMinimumNumberOfParameters() {
		return 3;
	}

	public Generic evaluate() {
		if (parameters[1] instanceof JsclVector && parameters[2] instanceof JsclVector) {
			Generic a = parameters[0];
			Variable variable[] = variables(parameters[1]);
			Generic s[] = ((JsclVector) parameters[2]).elements();
			for (int i = 0; i < variable.length; i++) a = a.substitute(variable[i], s[i]);
			return a;
		} else {
			Variable variable = parameters[1].variableValue();
			return parameters[0].substitute(variable, parameters[2]);
		}
	}

	public Operator transmute() {
		Generic p[] = new Generic[]{null, GenericVariable.content(parameters[1]), GenericVariable.content(parameters[2])};
		if (p[1] instanceof JsclVector && p[2] instanceof JsclVector) {
			return new Substitute(parameters[0], p[1], p[2]);
		}
		return this;
	}

	public Generic expand() {
		return evaluate();
	}

	@NotNull
	@Override
	public Operator newInstance(@NotNull Generic[] parameters) {
		return new Substitute(parameters).transmute();
	}

	public Variable newInstance() {
		return new Substitute(null, null, null);
	}
}

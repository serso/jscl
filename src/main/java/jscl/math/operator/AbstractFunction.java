package jscl.math.operator;

import jscl.math.Generic;
import jscl.math.Variable;
import jscl.math.function.Constant;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;

/**
 * User: serso
 * Date: 11/29/11
 * Time: 9:50 PM
 */
public abstract class AbstractFunction extends Variable {

	protected Generic parameters[];

	public AbstractFunction(@NotNull String name, Generic[] parameters) {
		super(name);
		this.parameters = parameters;
	}

	public Generic[] getParameters() {
		return parameters;
	}

	public void setParameters(Generic[] parameters) {
		this.parameters = parameters;
	}

	public abstract Generic evaluate();

	public abstract Generic evaluateElementary();

	public abstract Generic evaluateSimplify();


	public Generic expand() {
		final AbstractFunction function = (AbstractFunction) newInstance();
		for (int i = 0; i < parameters.length; i++) {
			function.parameters[i] = parameters[i].expand();
		}
		return function.evaluate();
	}

	public Generic elementary() {
		final AbstractFunction function = (AbstractFunction) newInstance();
		for (int i = 0; i < parameters.length; i++) {
			function.parameters[i] = parameters[i].elementary();
		}
		return function.evaluateElementary();
	}

	public Generic factorize() {
		final AbstractFunction function = (AbstractFunction) newInstance();
		for (int i = 0; i < parameters.length; i++) {
			function.parameters[i] = parameters[i].factorize();
		}
		return function.expressionValue();
	}

	public Generic simplify() {
		final AbstractFunction function = (AbstractFunction) newInstance();
		for (int i = 0; i < parameters.length; i++) {
			function.parameters[i] = parameters[i].simplify();
		}
		return function.evaluateSimplify();
	}


	@Nullable
	protected static Generic getParameter(@Nullable Generic[] parameters, final int i) {
		return parameters == null ? null : (parameters.length > i ? parameters[i] : null);
	}

	@NotNull
	@Override
	public Set<? extends Constant> getConstants() {
		final Set<Constant> result = new HashSet<Constant> ();

		for (Generic parameter : parameters) {
			result.addAll(parameter.getConstants());
		}

		return result;
	}
}

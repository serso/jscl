package jscl.math.function;

import jscl.CustomFunctionCalculationException;
import jscl.math.*;
import jscl.text.ParseException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.solovyev.common.definitions.IBuilder;

/**
 * User: serso
 * Date: 11/15/11
 * Time: 5:19 PM
 */
public class CustomFunction extends Function {

	@NotNull
	private Generic content;

	private String[] parameterNames;

	public static class Builder implements IBuilder<CustomFunction> {

		private final boolean system;

		@NotNull
		private String content;

		@NotNull
		private String[] parameterNames;

		@NotNull
		private String name;

		public Builder(@NotNull String name,
				@NotNull String[] parameterNames,
				@NotNull String content) {
			this.system = false;
			this.content = content;
			this.parameterNames = parameterNames;
			this.name = name;
		}

		Builder(boolean system,
				@NotNull String name,
				@NotNull String[] parameterNames,
				@NotNull String content) {
			this.system = system;
			this.content = content;
			this.parameterNames = parameterNames;
			this.name = name;
		}

		@NotNull
		@Override
		public CustomFunction create() {
			final CustomFunction customFunction = new CustomFunction(name, null, parameterNames, content);
			customFunction.setSystem(system);
			return customFunction;
		}
	}

	private CustomFunction(@NotNull String name,
						  @Nullable Generic parameters[],
						  @NotNull String parameterNames[],
						  @NotNull Generic content) {
		super(name, parameters);
		this.parameterNames = parameterNames;
		this.content = content;
	}

	private CustomFunction(@NotNull String name,
						  @Nullable Generic parameters[],
						  @NotNull String parameterNames[],
						  @NotNull String content) {
		super(name, parameters);
		this.parameterNames = parameterNames;
		try {
			this.content = Expression.valueOf(content);
		} catch (ParseException e) {
			throw new CustomFunctionCalculationException(this, e);
		}
	}

	@Override
	public int getMinimumNumberOfParameters() {
		return parameterNames == null ? 0 : parameterNames.length;
	}

	@Override
	public int getMaximumNumberOfParameters() {
		return parameterNames == null ? 0 : parameterNames.length;
	}

	@Override
	public Generic substitute(Variable variable, Generic generic) {
		return super.substitute(variable, generic);
	}

	@Override
	public Generic numeric() {
		return evaluate().numeric();
	}

	@Override
	public Generic expand() {
		return evaluate().expand();
	}

	@Override
	public Generic elementary() {
		return evaluate().elementary();
	}

	@Override
	public Generic factorize() {
		return evaluate().factorize();
	}

	@Override
	public Generic evaluate() {
		Generic localContent = content;
		for (int i = 0; i < parameterNames.length; i++) {
			localContent = localContent.substitute(new Constant(parameterNames[i]), parameters[i]);
		}

		return localContent;
	}

	@Override
	public Generic evaluateElementary() {
		throw new ArithmeticException();
	}

	@Override
	public Generic evaluateSimplify() {
		return expressionValue();
	}

	@Override
	public Generic evaluateNumerically() {
		throw new ArithmeticException();
	}

	@Override
	public Generic antiDerivative(int n) throws NotIntegrableException {
		throw new NotIntegrableException();
	}

	@Override
	public Generic derivative(int n) {
		throw new ArithmeticException();
	}

	@NotNull
	public String getContent() {
		return this.content.toString();
	}

	public String[] getParameterNames() {
		return parameterNames;
	}

	@Override
	public CustomFunction newInstance() {
		return new CustomFunction(name, new Generic[parameterNames.length], parameterNames, content);
	}
}

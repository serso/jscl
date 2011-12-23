package jscl.math.function;

import jscl.CustomFunctionCalculationException;
import jscl.math.*;
import jscl.text.ParseException;
import jscl.text.ParserUtils;
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

	@NotNull
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

		public Builder(@NotNull CustomFunction function) {
			this.system = function.isSystem();
			this.content = function.getContent();
			this.parameterNames = ParserUtils.copyOf(function.getParameterNames());
			this.name = function.getName();
		}
		
		public Builder() {
			this.system = false;
		}

		@NotNull
		public Builder setContent(@NotNull String content) {
			this.content = content;
			return this;
		}

		@NotNull
		public Builder setParameterNames(@NotNull String[] parameterNames) {
			this.parameterNames = parameterNames;
			return this;
		}
		
		@NotNull
		public Builder setName(@NotNull String name) {
			this.name = name;
			return this;
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
	public int getMinParameters() {
		return parameterNames == null ? 0 : parameterNames.length;
	}

	@Override
	public int getMaxParameters() {
		return parameterNames == null ? Integer.MAX_VALUE : parameterNames.length;
	}

	@Override
	public Generic substitute(@NotNull Variable variable, @NotNull Generic generic) {
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
	public Generic selfElementary() {
		throw new ArithmeticException();
	}

	@Override
	public Generic selfSimplify() {
		return expressionValue();
	}

	@Override
	public Generic selfNumeric() {
		throw new ArithmeticException();
	}

	@Override
	public Generic antiDerivative(@NotNull Variable variable) throws NotIntegrableException {
		if ( getParameterForAntiDerivation(variable) < 0 ) {
			throw new NotIntegrableException();
		} else {
			return this.content.antiDerivative(variable);
		}
	}

	@Override
	public Generic antiDerivative(int n) throws NotIntegrableException {
		throw new NotIntegrableException();
	}

	@NotNull
	@Override
	public Generic derivative(@NotNull Variable variable) {
		Generic result = JsclInteger.valueOf(0);

		for (int i = 0; i < parameters.length; i++) {
			// chain rule: f(x) = g(h(x)) => f'(x) = g'(h(x)) * h'(x)
			// hd = h'(x)
			// gd = g'(x)
			final Generic hd = parameters[i].derivative(variable);
			final Generic gd = this.content.derivative(variable);

			result = result.add(hd.multiply(gd));
		}

		return result;
	}

	@Override
	public Generic derivative(int n) {
		throw new ArithmeticException();
	}

	@NotNull
	public String getContent() {
		return this.content.toString();
	}

	@NotNull
	public String[] getParameterNames() {
		return parameterNames;
	}

	@Override
	public CustomFunction newInstance() {
		return new CustomFunction(name, new Generic[parameterNames.length], parameterNames, content);
	}
}

package jscl.math.function;

import jscl.math.*;
import jscl.text.ParseException;
import org.jetbrains.annotations.NotNull;
import org.solovyev.common.definitions.IBuilder;

import java.util.Map;

/**
 * User: serso
 * Date: 11/15/11
 * Time: 5:19 PM
 */
public class CustomFunction extends Function {

	private String content;

	private String[] parameterNames;

	public static class Builder implements IBuilder<CustomFunction> {

		private String content;

		private String[] parameterNames;

		private String name;

		public Builder(String content, String[] parameterNames, String name) {
			this.content = content;
			this.parameterNames = parameterNames;
			this.name = name;
		}

		@NotNull
		@Override
		public CustomFunction create() {
			return new CustomFunction(name, null, parameterNames, content);
		}
	}

	public CustomFunction(String name, Generic parameters[], String parameterNames[], String content) {
		super(name == null ? "" : name, parameters);
		this.parameterNames = parameterNames;
		this.content = content;
	}

	@Override
	public Generic evaluate() {
		throw new ArithmeticException();
	}

	@Override
	public Generic evaluateElementary() {
		return evaluate().elementary();
	}

	@Override
	public Generic evaluateSimplify() {
		return evaluate().simplify();
	}

	@Override
	public Generic evaluateNumerically() {
		return evaluate().numeric();
	}

	@Override
	public Generic antiDerivative(int n) throws NotIntegrableException {
		throw new NotIntegrableException();
	}

	@Override
	public Generic derivative(int n) {
		throw new ArithmeticException();
	}

	@Override
	public Variable newInstance() {
		return new CustomFunction(name, new Generic[parameterNames.length], parameterNames, content);
	}
}

package jscl.math.function;

import jscl.CustomFunctionCalculationException;
import jscl.JsclMathEngine;
import jscl.math.Expression;
import jscl.math.Generic;
import jscl.math.JsclInteger;
import jscl.math.NotIntegrableException;
import jscl.math.Variable;
import jscl.text.ParseException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.solovyev.common.JBuilder;
import org.solovyev.common.math.MathEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * User: serso
 * Date: 11/15/11
 * Time: 5:19 PM
 */
public class CustomFunction extends Function implements IFunction {

	/*
	**********************************************************************
	*
	*                           CONSTANTS
	*
	**********************************************************************
	*/

	private static final String LOCAL_VAR_POSTFIX = "_lv_09_03_1988_";

	private final static AtomicInteger counter = new AtomicInteger(0);

	/*
	**********************************************************************
	*
	*                           FIELDS
	*
	**********************************************************************
	*/

	@NotNull
	private final Integer localVarId;

	@NotNull
    private Expression content;

	@Nullable
	private String description;

    @NotNull
    private List<String> parameterNames = Collections.emptyList();

	/*
	**********************************************************************
	*
	*                           CONSTRUCTORS
	*
	**********************************************************************
	*/

    private CustomFunction(@NotNull String name,
                           @NotNull List<String> parameterNames,
                           @NotNull Expression content,
						   @Nullable String description) {
        super(name, new Generic[parameterNames.size()]);
        this.parameterNames = parameterNames;
        this.content = content;
        this.description = description;
		this.localVarId = counter.incrementAndGet();
    }

    private CustomFunction(@NotNull String name,
                           @NotNull List<String> parameterNames,
                           @NotNull String content,
						   @Nullable String description) {
        super(name, new Generic[parameterNames.size()]);
        this.parameterNames = parameterNames;
        try {
            this.content = Expression.valueOf(content);
        } catch (ParseException e) {
            throw new CustomFunctionCalculationException(this, e);
        }
		this.description = description;
		this.localVarId = counter.incrementAndGet();
	}

	/*
	**********************************************************************
	*
	*                           METHODS
	*
	**********************************************************************
	*/

    @Override
    public int getMinParameters() {
        return parameterNames == null ? 0 : parameterNames.size();
    }

    @Override
    public int getMaxParameters() {
        return parameterNames == null ? Integer.MAX_VALUE : parameterNames.size();
    }

    @Override
    public Generic substitute(@NotNull Variable variable, @NotNull Generic generic) {
        return super.substitute(variable, generic);
    }

    @Override
    public Generic numeric() {
        return selfExpand().numeric();
    }

    @Override
    public Generic expand() {
        return selfExpand().expand();
    }

    @Override
    public Generic elementary() {
        return selfExpand().elementary();
    }

    @Override
    public Generic factorize() {
        return selfExpand().factorize();
    }

    @Override
    public Generic selfExpand() {
        Generic localContent = content;

        try {
            for (String parameterName : parameterNames) {
                localContent = localContent.substitute(new Constant(parameterName), Expression.valueOf(new Constant(getParameterNameForConstant(parameterName))));
            }

            for (int i = 0; i < parameterNames.size(); i++) {
                localContent = localContent.substitute(new Constant(getParameterNameForConstant(parameterNames.get(i))), parameters[i]);
            }

        } finally {
            for (String parameterName : parameterNames) {
                localContent = localContent.substitute(new Constant(getParameterNameForConstant(parameterName)), Expression.valueOf(new Constant(parameterName)));
            }
        }

        return localContent;
    }

	@NotNull
	private String getParameterNameForConstant(@NotNull String parameterName) {
		return parameterName + LOCAL_VAR_POSTFIX + "_" + this.localVarId;
	}

	@Override
    public void copy(@NotNull MathEntity mathEntity) {
        super.copy(mathEntity);
        if (mathEntity instanceof CustomFunction) {
            final CustomFunction that = (CustomFunction) mathEntity;
            this.content = that.content;
            this.parameterNames = new ArrayList<String>(that.parameterNames);
            this.description = that.description;
        }
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
        if (getParameterForAntiDerivation(variable) < 0) {
            throw new NotIntegrableException(this);
        } else {
            return this.content.antiDerivative(variable);
        }
    }

    @Override
    public Generic antiDerivative(int n) throws NotIntegrableException {
        throw new NotIntegrableException(this);
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

	@Nullable
	@Override
	public String getDescription() {
		return this.description;
	}

	@Override
	@NotNull
    public List<String> getParameterNames() {
        return Collections.unmodifiableList(parameterNames);
    }

    @NotNull
    @Override
    protected String formatUndefinedParameter(int i) {
        if (i < this.parameterNames.size()) {
            return parameterNames.get(i);
        } else {
            return super.formatUndefinedParameter(i);
        }
    }

    @NotNull
    @Override
    public CustomFunction newInstance() {
        return new CustomFunction(name, parameterNames, content, description);
    }

	/*
	**********************************************************************
	*
	*                           STATIC
	*
	**********************************************************************
	*/

	public static class Builder implements JBuilder<CustomFunction> {

		private final boolean system;

		@NotNull
		private String content;

		@Nullable
		private String description;

		@NotNull
		private List<String> parameterNames;

		@NotNull
		private String name;

		@Nullable
		private Integer id;

		public Builder(@NotNull String name,
					   @NotNull List<String> parameterNames,
					   @NotNull String content) {
			this.system = false;
			this.content = content;
			this.parameterNames = parameterNames;
			this.name = name;
		}

		public Builder(@NotNull IFunction function) {
			this.system = function.isSystem();
			this.content = function.getContent();
			this.description = function.getDescription();
			this.parameterNames = new ArrayList<String>(function.getParameterNames());
			this.name = function.getName();
			if (function.isIdDefined()) {
				this.id = function.getId();
			}
		}

		public Builder() {
			this.system = false;
		}

		public void setDescription(@Nullable String description) {
			this.description = description;
		}

		@NotNull
		public Builder setContent(@NotNull String content) {
			this.content = content;
			return this;
		}

		@NotNull
		public Builder setParameterNames(@NotNull List<String> parameterNames) {
			this.parameterNames = parameterNames;
			return this;
		}

		@NotNull
		public Builder setName(@NotNull String name) {
			this.name = name;
			return this;
		}

		public Builder(boolean system,
					   @NotNull String name,
					   @NotNull List<String> parameterNames,
					   @NotNull String content) {
			this.system = system;
			this.content = content;
			this.parameterNames = parameterNames;
			this.name = name;
		}

		@NotNull
		@Override
		public CustomFunction create() {
			final CustomFunction customFunction = new CustomFunction(name, parameterNames, prepareContent(content), description);
			customFunction.setSystem(system);
			if (id != null) {
				customFunction.setId(id);
			}
			return customFunction;
		}

        @NotNull
        private static String prepareContent(@NotNull String content) {
            final StringBuilder result = new StringBuilder(content.length());

            final char groupingSeparator = JsclMathEngine.getInstance().getGroupingSeparator();

            for (int i = 0; i < content.length(); i++) {
                final char ch = content.charAt(i);
                switch (ch) {
                    case ' ':
                    case '\'':
                    case '\n':
                    case '\r':
                        // do nothing
                        break;
                    default:
                        // remove grouping separator
                        if (ch != groupingSeparator) {
                            result.append(ch);
                        }
                }
            }

            return result.toString();
        }
    }
}

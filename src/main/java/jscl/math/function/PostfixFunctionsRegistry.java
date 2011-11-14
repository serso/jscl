package jscl.math.function;

import jscl.math.operator.Degree;
import jscl.math.operator.Factorial;
import jscl.math.operator.Operator;
import jscl.math.operator.Percent;
import org.jetbrains.annotations.NotNull;
import org.solovyev.common.math.AbstractMathRegistry;

/**
 * User: serso
 * Date: 10/31/11
 * Time: 10:56 PM
 */
public class PostfixFunctionsRegistry extends AbstractMathRegistry<Operator> {

	private final static PostfixFunctionsRegistry instance = new PostfixFunctionsRegistry();

	static {
		instance.add(new Factorial(null));
		instance.add(new Degree(null));
		instance.add(new Percent(null, null));
	}

	@NotNull
	public static PostfixFunctionsRegistry getInstance() {
		return instance;
	}

	@Override
	public Operator get(@NotNull String name) {
		final Operator operator = super.get(name);
		return operator == null ? null : (Operator) operator.newInstance();
	}
}

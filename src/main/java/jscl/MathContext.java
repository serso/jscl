package jscl;

import jscl.math.function.Function;
import jscl.math.function.IConstant;
import jscl.math.operator.Operator;
import org.jetbrains.annotations.NotNull;
import org.solovyev.common.math.MathRegistry;

/**
 * User: serso
 * Date: 11/29/11
 * Time: 11:33 AM
 */
public interface MathContext {

	@NotNull
	MathRegistry<Function> getFunctionsRegistry();

	@NotNull
	MathRegistry<Operator> getOperatorsRegistry();

	@NotNull
	MathRegistry<IConstant> getConstantsRegistry();

	@NotNull
	MathRegistry<Operator> getPostfixFunctionsRegistry();

	@NotNull
	AngleUnit getDefaultAngleUnit();

	void setDefaultAngleUnit(@NotNull AngleUnit defaultAngleUnits);
}

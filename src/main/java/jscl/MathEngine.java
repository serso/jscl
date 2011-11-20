package jscl;

import jscl.math.function.Function;
import jscl.math.function.IConstant;
import jscl.math.operator.Operator;
import jscl.text.ParseException;
import org.jetbrains.annotations.NotNull;
import org.solovyev.common.math.MathRegistry;

/**
 * User: serso
 * Date: 11/1/11
 * Time: 12:00 PM
 */
public interface MathEngine {

	String evaluate(@NotNull String expression) throws ParseException;

	String simplify(@NotNull String expression) throws ParseException;

	String elementary(@NotNull String expression) throws ParseException;

	@NotNull
	MathRegistry<Function> getFunctionsRegistry();

	@NotNull
	MathRegistry<Operator> getOperatorsRegistry();

	@NotNull
	MathRegistry<IConstant> getConstantsRegistry();

	@NotNull
	MathRegistry<Operator> getPostfixFunctionsRegistry();

	@NotNull
	AngleUnits getDefaultAngleUnits();

	void setDefaultAngleUnits(@NotNull AngleUnits defaultAngleUnits);
}

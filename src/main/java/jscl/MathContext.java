package jscl;

import jscl.math.function.Function;
import jscl.math.function.IConstant;
import jscl.math.operator.Operator;
import org.jetbrains.annotations.NotNull;
import org.solovyev.common.math.MathRegistry;

import java.text.DecimalFormatSymbols;

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
	AngleUnit getAngleUnits();

	void setAngleUnits(@NotNull AngleUnit defaultAngleUnits);

	@NotNull
	NumeralBase getNumeralBase();


	// OUTPUT NUMBER FORMATTING
	// todo serso: maybe gather all formatting data in one object?

	void setNumeralBase(@NotNull NumeralBase numeralBase);

	void setDecimalGroupSymbols(@NotNull DecimalFormatSymbols decimalGroupSymbols);

	void setRoundResult(boolean roundResult);

	void setPrecision(int precision);

	void setUseGroupingSeparator(boolean useGroupingSeparator);

	void setGroupingSeparator(char groupingSeparator);

	@NotNull
	String format(@NotNull Double value) throws NumeralBaseException ;

	@NotNull
	String format(@NotNull Double value, @NotNull NumeralBase nb) throws NumeralBaseException;
}

package jscl;

import jscl.math.Expression;
import jscl.math.Generic;
import jscl.math.NotIntegerException;
import jscl.math.function.Constants;
import jscl.math.function.ConstantsRegistry;
import jscl.math.function.Function;
import jscl.math.function.FunctionsRegistry;
import jscl.math.function.IConstant;
import jscl.math.function.PostfixFunctionsRegistry;
import jscl.math.operator.Operator;
import jscl.math.operator.Percent;
import jscl.math.operator.Rand;
import jscl.math.operator.matrix.OperatorsRegistry;
import jscl.text.ParseException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.solovyev.common.JPredicate;
import org.solovyev.common.collections.Collections;
import org.solovyev.common.math.MathRegistry;
import org.solovyev.common.msg.MessageRegistry;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 * User: serso
 * Date: 11/1/11
 * Time: 12:00 PM
 */
public class JsclMathEngine implements MathEngine {

    /*
    **********************************************************************
    *
    *                           CONSTANTS
    *
    **********************************************************************
    */
    @NotNull
    private static JsclMathEngine instance = new JsclMathEngine();

    public static final String GROUPING_SEPARATOR_DEFAULT = " ";
    public static final int MAX_FRACTION_DIGITS = 20;


    /*
         **********************************************************************
         *
         *                           FIELDS
         *
         **********************************************************************
         */

    @NotNull
    private DecimalFormatSymbols decimalGroupSymbols = new DecimalFormatSymbols(Locale.getDefault());

    {
        decimalGroupSymbols.setDecimalSeparator('.');
        decimalGroupSymbols.setGroupingSeparator(GROUPING_SEPARATOR_DEFAULT.charAt(0));
    }

    private boolean roundResult = false;

    private boolean scienceNotation = false;

    private int precision = 5;

    private boolean useGroupingSeparator = false;

    @NotNull
    private AngleUnit angleUnits = AngleUnit.deg;

    @NotNull
    private NumeralBase numeralBase = NumeralBase.dec;

    @NotNull
    private ConstantsRegistry constantsRegistry;

	@NotNull
	private MessageRegistry messageRegistry = new FixedCapacityListMessageRegistry(10);

    /*
    **********************************************************************
    *
    *                           CONSTRUCTORS
    *
    **********************************************************************
    */

    private JsclMathEngine() {
        this.constantsRegistry = new ConstantsRegistry();
    }

    /*
    **********************************************************************
    *
    *                           METHODS
    *
    **********************************************************************
    */

    @NotNull
    public static JsclMathEngine getInstance() {
        return instance;
    }

    @NotNull
    @Override
    public String evaluate(@NotNull String expression) throws ParseException {
        return evaluateGeneric(expression).toString();
    }

    @NotNull
    @Override
    public String simplify(@NotNull String expression) throws ParseException {
        return simplifyGeneric(expression).toString();
    }

    @NotNull
    @Override
    public String elementary(@NotNull String expression) throws ParseException {
        return elementaryGeneric(expression).toString();
    }

    @NotNull
    @Override
    public Generic evaluateGeneric(@NotNull String expression) throws ParseException {
        if (expression.contains(Percent.NAME) || expression.contains(Rand.NAME)) {
            return Expression.valueOf(expression).numeric();
        } else {
            return Expression.valueOf(expression).expand().numeric();
        }
    }

    @NotNull
    @Override
    public Generic simplifyGeneric(@NotNull String expression) throws ParseException {
        if (expression.contains(Percent.NAME) || expression.contains(Rand.NAME)) {
            return Expression.valueOf(expression);
        } else {
            return Expression.valueOf(expression).expand().simplify();
        }
    }

    @NotNull
    @Override
    public Generic elementaryGeneric(@NotNull String expression) throws ParseException {
        return Expression.valueOf(expression).elementary();
    }

    @NotNull
    @Override
    public MathRegistry<Function> getFunctionsRegistry() {
        return FunctionsRegistry.getInstance();
    }

    @NotNull
    @Override
    public MathRegistry<Operator> getOperatorsRegistry() {
        return OperatorsRegistry.getInstance();
    }

    @NotNull
    @Override
    public MathRegistry<Operator> getPostfixFunctionsRegistry() {
        return PostfixFunctionsRegistry.getInstance();
    }

    @NotNull
    @Override
    public AngleUnit getAngleUnits() {
        return angleUnits;
    }

    @Override
    public void setAngleUnits(@NotNull AngleUnit angleUnits) {
        this.angleUnits = angleUnits;
    }

    @Override
    @NotNull
    public NumeralBase getNumeralBase() {
        return numeralBase;
    }

    @Override
    public void setNumeralBase(@NotNull NumeralBase numeralBase) {
        this.numeralBase = numeralBase;
    }

    @NotNull
    @Override
    public MathRegistry<IConstant> getConstantsRegistry() {
        return constantsRegistry;
    }

    @Override
    @NotNull
    public String format(@NotNull Double value) throws NumeralBaseException {
        return format(value, numeralBase);
    }

    @Override
    @NotNull
    public String format(@NotNull Double value, @NotNull NumeralBase nb) throws NumeralBaseException {
        if (value.isInfinite()) {
            // return predefined constant for infinity
            if (value >= 0) {
                return Constants.INF.getName();
            } else {
                return Constants.INF.expressionValue().negate().toString();
            }
        } else {
            if (value.isNaN()) {
                // return "NaN"
                return String.valueOf(value);
            } else {
                if (nb == NumeralBase.dec) {
                    // decimal numeral base => do specific formatting

                    // detect if current number is precisely equals to constant in constants' registry  (NOTE: ONLY FOR SYSTEM CONSTANTS)
                    final Double localValue = value;
                    IConstant constant = Collections.find(this.getConstantsRegistry().getSystemEntities(), new JPredicate<IConstant>() {
                        @Override
                        public boolean apply(@Nullable IConstant constant) {
                            if (constant != null) {
                                if (localValue.equals(constant.getDoubleValue())) {
                                    if (!constant.getName().equals(Constants.PI_INV.getName())) {
                                        if (!constant.getName().equals(Constants.PI.getName()) || JsclMathEngine.getInstance().getAngleUnits() == AngleUnit.rad) {
                                            return true;
                                        }
                                    }
                                }
                            }

                            return false;
                        }
                    });


                    if (constant == null) {
                        final IConstant piInv = this.getConstantsRegistry().get(Constants.PI_INV.getName());
                        if (piInv != null && value.equals(piInv.getDoubleValue())) {
                            constant = piInv;
                        }
                    }

                    if (constant == null) {
                        // prepare decimal format
                        final DecimalFormat df;

                        if (roundResult) {
                            value = new BigDecimal(value).setScale(precision, BigDecimal.ROUND_HALF_UP).doubleValue();
                        }

                        if (value != 0d && value != -0d) {
                            if (Math.abs(value) < Math.pow(10, -5) || scienceNotation) {
                                df = new DecimalFormat("##0.#####E0");
                            } else {
                                df = new DecimalFormat();
                            }
                        } else {
                            df = new DecimalFormat();
                        }

                        df.setDecimalFormatSymbols(decimalGroupSymbols);
                        df.setGroupingUsed(useGroupingSeparator);
                        df.setGroupingSize(nb.getGroupingSize());

                        if (!scienceNotation) {
                            // using default round logic => try roundResult variable
                            if (!roundResult) {
                                // set maximum fraction digits high enough to show all fraction digits in case of no rounding
                                df.setMaximumFractionDigits(MAX_FRACTION_DIGITS);
                            } else {
                                df.setMaximumFractionDigits(precision);
                            }
                        }

                        return df.format(value);

                    } else {
                        return constant.getName();
                    }
                } else {
                    return convert(value, nb);
                }
            }
        }
    }

    @Override
    @NotNull
    public String convert(@NotNull Double value, @NotNull NumeralBase to) {
        String ungroupedValue;
        try {
            // check if double can be converted to integer
            integerValue(value);

            ungroupedValue = to.toString(new BigDecimal(value).toBigInteger());
        } catch (NotIntegerException e) {
            ungroupedValue = to.toString(value, roundResult ? precision : MAX_FRACTION_DIGITS);
        }

        return addGroupingSeparators(to, ungroupedValue);
    }

	@Override
	@NotNull
	public MessageRegistry getMessageRegistry() {
		return messageRegistry;
	}

	@Override
	public void setMessageRegistry(@NotNull MessageRegistry messageRegistry) {
		this.messageRegistry = messageRegistry;
	}

	@Override
    @NotNull
    public String addGroupingSeparators(@NotNull NumeralBase nb, @NotNull String ungroupedDoubleValue) {
        if (useGroupingSeparator) {
            String groupingSeparator = nb == NumeralBase.dec ? String.valueOf(decimalGroupSymbols.getGroupingSeparator()) : " ";

            final int dotIndex = ungroupedDoubleValue.indexOf(".");

            String ungroupedValue;
            if (dotIndex >= 0) {
                ungroupedValue = ungroupedDoubleValue.substring(0, dotIndex);
            } else {
                ungroupedValue = ungroupedDoubleValue;
            }
            // inject group separator in the resulted string
            // NOTE: space symbol is always used!!!
            StringBuilder result = insertSeparators(nb, groupingSeparator, ungroupedValue, true);

            result = result.reverse();

            if (dotIndex >= 0) {
                result.append(insertSeparators(nb, groupingSeparator, ungroupedDoubleValue.substring(dotIndex), false));
            }

            return result.toString();
        } else {
            return ungroupedDoubleValue;
        }
    }

    @NotNull
    private StringBuilder insertSeparators(@NotNull NumeralBase nb,
                                           @NotNull String groupingSeparator,
                                           @NotNull String value,
                                           boolean reversed) {
        final StringBuilder result = new StringBuilder(value.length() + nb.getGroupingSize() * groupingSeparator.length());

        if (reversed) {
            for (int i = value.length() - 1; i >= 0; i--) {
                result.append(value.charAt(i));
                if (i != 0 && (value.length() - i) % nb.getGroupingSize() == 0) {
                    result.append(groupingSeparator);
                }
            }
        } else {
            for (int i = 0; i < value.length(); i++) {
                result.append(value.charAt(i));
                if (i != 0 && i != value.length() - 1 && i % nb.getGroupingSize() == 0) {
                    result.append(groupingSeparator);
                }
            }
        }

        return result;
    }

    private static int integerValue(final double value) throws NotIntegerException {
        if (Math.floor(value) == value) {
            return (int) value;
        } else {
            throw new NotIntegerException();
        }
    }

    @Override
    public void setDecimalGroupSymbols(@NotNull DecimalFormatSymbols decimalGroupSymbols) {
        this.decimalGroupSymbols = decimalGroupSymbols;
    }

    @Override
    public void setRoundResult(boolean roundResult) {
        this.roundResult = roundResult;
    }

    @Override
    public void setPrecision(int precision) {
        this.precision = precision;
    }

    @Override
    public void setUseGroupingSeparator(boolean useGroupingSeparator) {
        this.useGroupingSeparator = useGroupingSeparator;
    }

    @Override
    public void setGroupingSeparator(char groupingSeparator) {
        this.decimalGroupSymbols.setGroupingSeparator(groupingSeparator);
    }

    @Override
    public void setScienceNotation(boolean scienceNotation) {
        this.scienceNotation = scienceNotation;
    }

    public char getGroupingSeparator() {
        return this.decimalGroupSymbols.getGroupingSeparator();
    }
}

package jscl;

import jscl.math.Expression;
import jscl.math.Generic;
import jscl.math.NotIntegerException;
import jscl.math.function.*;
import jscl.math.operator.Operator;
import jscl.math.operator.Percent;
import jscl.math.operator.Rand;
import jscl.math.operator.matrix.OperatorsRegistry;
import jscl.text.ParseException;
import org.solovyev.common.JPredicate;
import org.solovyev.common.collections.Collections;
import org.solovyev.common.math.MathRegistry;
import org.solovyev.common.msg.MessageRegistry;
import org.solovyev.common.msg.Messages;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
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
    @Nonnull
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

    @Nonnull
    private DecimalFormatSymbols decimalGroupSymbols = new DecimalFormatSymbols(Locale.getDefault());

    {
        decimalGroupSymbols.setDecimalSeparator('.');
        decimalGroupSymbols.setGroupingSeparator(GROUPING_SEPARATOR_DEFAULT.charAt(0));
    }

    private boolean roundResult = false;

    private boolean scienceNotation = false;

    private int precision = 5;

    private boolean useGroupingSeparator = false;

    @Nonnull
    private AngleUnit angleUnits = AngleUnit.deg;

    @Nonnull
    private NumeralBase numeralBase = NumeralBase.dec;

    @Nonnull
    private ConstantsRegistry constantsRegistry;

	@Nonnull
	private MessageRegistry messageRegistry = Messages.synchronizedMessageRegistry(new FixedCapacityListMessageRegistry(10));

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

    @Nonnull
    public static JsclMathEngine getInstance() {
        return instance;
    }

    @Nonnull
    @Override
    public String evaluate(@Nonnull String expression) throws ParseException {
        return evaluateGeneric(expression).toString();
    }

    @Nonnull
    @Override
    public String simplify(@Nonnull String expression) throws ParseException {
        return simplifyGeneric(expression).toString();
    }

    @Nonnull
    @Override
    public String elementary(@Nonnull String expression) throws ParseException {
        return elementaryGeneric(expression).toString();
    }

    @Nonnull
    @Override
    public Generic evaluateGeneric(@Nonnull String expression) throws ParseException {
        if (expression.contains(Percent.NAME) || expression.contains(Rand.NAME)) {
            return Expression.valueOf(expression).numeric();
        } else {
            return Expression.valueOf(expression).expand().numeric();
        }
    }

    @Nonnull
    @Override
    public Generic simplifyGeneric(@Nonnull String expression) throws ParseException {
        if (expression.contains(Percent.NAME) || expression.contains(Rand.NAME)) {
            return Expression.valueOf(expression);
        } else {
            return Expression.valueOf(expression).expand().simplify();
        }
    }

    @Nonnull
    @Override
    public Generic elementaryGeneric(@Nonnull String expression) throws ParseException {
        return Expression.valueOf(expression).elementary();
    }

    @Nonnull
    @Override
    public MathRegistry<Function> getFunctionsRegistry() {
        return FunctionsRegistry.getInstance();
    }

    @Nonnull
    @Override
    public MathRegistry<Operator> getOperatorsRegistry() {
        return OperatorsRegistry.getInstance();
    }

    @Nonnull
    @Override
    public MathRegistry<Operator> getPostfixFunctionsRegistry() {
        return PostfixFunctionsRegistry.getInstance();
    }

    @Nonnull
    @Override
    public AngleUnit getAngleUnits() {
        return angleUnits;
    }

    @Override
    public void setAngleUnits(@Nonnull AngleUnit angleUnits) {
        this.angleUnits = angleUnits;
    }

    @Override
    @Nonnull
    public NumeralBase getNumeralBase() {
        return numeralBase;
    }

    @Override
    public void setNumeralBase(@Nonnull NumeralBase numeralBase) {
        this.numeralBase = numeralBase;
    }

    @Nonnull
    @Override
    public MathRegistry<IConstant> getConstantsRegistry() {
        return constantsRegistry;
    }

    @Override
    @Nonnull
    public String format(@Nonnull Double value) throws NumeralBaseException {
        return format(value, numeralBase);
    }

    @Override
    @Nonnull
    public String format(@Nonnull Double value, @Nonnull NumeralBase nb) throws NumeralBaseException {
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
    @Nonnull
    public String convert(@Nonnull Double value, @Nonnull NumeralBase to) {
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
	@Nonnull
	public MessageRegistry getMessageRegistry() {
		return messageRegistry;
	}

	@Override
	public void setMessageRegistry(@Nonnull MessageRegistry messageRegistry) {
		this.messageRegistry = messageRegistry;
	}

	@Override
    @Nonnull
    public String addGroupingSeparators(@Nonnull NumeralBase nb, @Nonnull String ungroupedDoubleValue) {
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

    @Nonnull
    private StringBuilder insertSeparators(@Nonnull NumeralBase nb,
                                           @Nonnull String groupingSeparator,
                                           @Nonnull String value,
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
    public void setDecimalGroupSymbols(@Nonnull DecimalFormatSymbols decimalGroupSymbols) {
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

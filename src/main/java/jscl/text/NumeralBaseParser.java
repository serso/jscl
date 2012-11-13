package jscl.text;

import jscl.NumeralBase;
import jscl.math.Generic;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * User: serso
 * Date: 11/23/11
 * Time: 2:59 PM
 */
public class NumeralBaseParser implements Parser<NumeralBase> {

    public static final Parser<NumeralBase> parser = new NumeralBaseParser();

    private NumeralBaseParser() {
    }

    @Override
    public NumeralBase parse(@NotNull Parameters p, @Nullable Generic previousSumElement) {
        int pos0 = p.getPosition().intValue();

        NumeralBase result = p.getMathContext().getNumeralBase();

        ParserUtils.skipWhitespaces(p);

        for (NumeralBase numeralBase : NumeralBase.values()) {
            try {
                final String jsclPrefix = numeralBase.getJsclPrefix();
                ParserUtils.tryToParse(p, pos0, jsclPrefix);
                result = numeralBase;
                break;
            } catch (ParseException e) {
            }
        }


        return result;
    }
}

package jscl.text;

import jscl.math.Generic;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * User: serso
 * Date: 10/27/11
 * Time: 2:45 PM
 */
class ExponentParser implements Parser<Generic> {

    public static final Parser<Generic> parser = new ExponentParser();

    private ExponentParser() {
    }

    public Generic parse(@NotNull Parameters p, @Nullable Generic previousSumElement) throws ParseException {
        int pos0 = p.getPosition().intValue();

        boolean sign = MinusParser.parser.parse(p, previousSumElement).isSign();

        final Generic result = ParserUtils.parseWithRollback(UnsignedExponent.parser, pos0, previousSumElement, p);
        return sign ? result.negate() : result;
    }
}

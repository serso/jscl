package jscl.text;

import jscl.math.Generic;
import jscl.text.msg.Messages;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PrimeCharacters implements Parser<Integer> {
    public static final Parser<Integer> parser = new PrimeCharacters();

    private PrimeCharacters() {
    }

    public Integer parse(@NotNull Parameters p, @Nullable Generic previousSumElement) throws ParseException {

        int pos0 = p.getPosition().intValue();

        int result = 0;

        ParserUtils.skipWhitespaces(p);

        if (p.getPosition().intValue() < p.getExpression().length() && p.getExpression().charAt(p.getPosition().intValue()) == '\'') {
            p.getPosition().increment();
            result = 1;
        } else {
            ParserUtils.throwParseException(p, pos0, Messages.msg_12, '\'');
        }

        while (p.getPosition().intValue() < p.getExpression().length() && p.getExpression().charAt(p.getPosition().intValue()) == '\'') {
            p.getPosition().increment();
            result++;
        }

        return result;
    }
}

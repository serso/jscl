package jscl.text;

import jscl.NumeralBase;
import jscl.math.Generic;
import jscl.text.msg.Messages;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Digits implements Parser<String> {

    @NotNull
    private final NumeralBase nb;

    public Digits(@NotNull NumeralBase nb) {
        this.nb = nb;
    }

    // returns digit
    public String parse(@NotNull Parameters p, @Nullable Generic previousSumElement) throws ParseException {
        int pos0 = p.getPosition().intValue();

        final StringBuilder result = new StringBuilder();

        ParserUtils.skipWhitespaces(p);

        if (p.getPosition().intValue() < p.getExpression().length() && nb.getAcceptableCharacters().contains(p.getExpression().charAt(p.getPosition().intValue()))) {
            result.append(p.getExpression().charAt(p.getPosition().intValue()));
            p.getPosition().increment();
        } else {
            ParserUtils.throwParseException(p, pos0, Messages.msg_9);
        }

        while (p.getPosition().intValue() < p.getExpression().length() && nb.getAcceptableCharacters().contains(p.getExpression().charAt(p.getPosition().intValue()))) {
            result.append(p.getExpression().charAt(p.getPosition().intValue()));
            p.getPosition().increment();
        }

        return result.toString();
    }
}

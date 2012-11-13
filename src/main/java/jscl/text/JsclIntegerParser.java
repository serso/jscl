package jscl.text;

import jscl.NumeralBase;
import jscl.math.Generic;
import jscl.math.JsclInteger;
import jscl.text.msg.Messages;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class JsclIntegerParser implements Parser<JsclInteger> {

    public static final Parser<JsclInteger> parser = new JsclIntegerParser();

    private JsclIntegerParser() {
    }

    public JsclInteger parse(@NotNull Parameters p, @Nullable Generic previousSumElement) throws ParseException {
        int pos0 = p.getPosition().intValue();

        final NumeralBase nb = NumeralBaseParser.parser.parse(p, previousSumElement);

        final StringBuilder result = new StringBuilder();

        try {
            result.append(new Digits(nb).parse(p, previousSumElement));
        } catch (ParseException e) {
            p.getPosition().setValue(pos0);
            throw e;
        }

        final String number = result.toString();
        try {
            return nb.toJsclInteger(number);
        } catch (NumberFormatException e) {
            throw new ParseException(Messages.msg_8, p.getPosition().intValue(), p.getExpression(), number);
        }
    }
}

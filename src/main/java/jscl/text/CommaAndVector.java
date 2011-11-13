package jscl.text;

import jscl.math.JsclVector;
import org.jetbrains.annotations.NotNull;

public class CommaAndVector implements Parser {
    public static final Parser parser=new CommaAndVector();

    private CommaAndVector() {}

    public Object parse(@NotNull String expression, @NotNull MutableInt position, int depth) throws ParseException {
        int pos0= position.intValue();
        JsclVector v;
        ParserUtils.skipWhitespaces(expression, position);
        if(position.intValue()< expression.length() && expression.charAt(position.intValue())==',') {
            expression.charAt(position.intValue());
			position.increment();
        } else {
            position.setValue(pos0);
            throw new ParseException();
        }
        try {
            v=(JsclVector)VectorParser.parser.parse(expression, position, depth);
        } catch (ParseException e) {
            position.setValue(pos0);
            throw e;
        }
        return v;
    }
}

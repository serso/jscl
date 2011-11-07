package jscl.text;

import jscl.math.JsclVector;
import org.jetbrains.annotations.NotNull;

public class CommaAndVector implements Parser {
    public static final Parser parser=new CommaAndVector();

    private CommaAndVector() {}

    public Object parse(@NotNull String string, @NotNull MutableInt position) throws ParseException {
        int pos0= position.intValue();
        JsclVector v;
        ParserUtils.skipWhitespaces(string, position);
        if(position.intValue()< string.length() && string.charAt(position.intValue())==',') {
            string.charAt(position.intValue());
			position.increment();
        } else {
            position.setValue(pos0);
            throw new ParseException();
        }
        try {
            v=(JsclVector)VectorParser.parser.parse(string, position);
        } catch (ParseException e) {
            position.setValue(pos0);
            throw e;
        }
        return v;
    }
}

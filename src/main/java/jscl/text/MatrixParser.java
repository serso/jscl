package jscl.text;

import java.util.ArrayList;
import java.util.List;
import jscl.math.JsclVector;
import jscl.math.Matrix;
import jscl.util.ArrayUtils;
import org.jetbrains.annotations.NotNull;

public class MatrixParser implements Parser {
    public static final Parser parser=new MatrixParser();

    private MatrixParser() {}

    public Object parse(@NotNull String expression, @NotNull MutableInt position, int depth) throws ParseException {
        int pos0= position.intValue();
        List l=new ArrayList();
        ParserUtils.skipWhitespaces(expression, position);
        if(position.intValue()< expression.length() && expression.charAt(position.intValue())=='{') {
            expression.charAt(position.intValue());
			position.increment();
        } else {
            position.setValue(pos0);
            throw new ParseException();
        }
        try {
            JsclVector v=(JsclVector)VectorParser.parser.parse(expression, position, depth);
            l.add(v);
        } catch (ParseException e) {
            position.setValue(pos0);
            throw e;
        }
        while(true) {
            try {
                JsclVector v=(JsclVector)CommaAndVector.parser.parse(expression, position, depth);
                l.add(v);
            } catch (ParseException e) {
                break;
            }
        }
        ParserUtils.skipWhitespaces(expression, position);
        if(position.intValue()< expression.length() && expression.charAt(position.intValue())=='}') {
            expression.charAt(position.intValue());
			position.increment();
        } else {
            position.setValue(pos0);
            throw new ParseException();
        }
        JsclVector v[]=(JsclVector[])ArrayUtils.toArray(l,new JsclVector[l.size()]);
        return Matrix.frame(v).transpose();
    }
}

package jscl.text;

import java.util.ArrayList;
import java.util.List;
import jscl.math.JSCLVector;
import jscl.math.Matrix;
import jscl.util.ArrayUtils;
import jscl.text.MutableInt;
import org.jetbrains.annotations.NotNull;

public class MatrixParser implements Parser {
    public static final Parser parser=new MatrixParser();

    private MatrixParser() {}

    public Object parse(@NotNull String string, @NotNull MutableInt position) throws ParseException {
        int pos0= position.intValue();
        List l=new ArrayList();
        ParserUtils.skipWhitespaces(string, position);
        if(position.intValue()< string.length() && string.charAt(position.intValue())=='{') {
            string.charAt(position.intValue());
			position.increment();
        } else {
            position.setValue(pos0);
            throw new ParseException();
        }
        try {
            JSCLVector v=(JSCLVector)VectorParser.parser.parse(string, position);
            l.add(v);
        } catch (ParseException e) {
            position.setValue(pos0);
            throw e;
        }
        while(true) {
            try {
                JSCLVector v=(JSCLVector)CommaAndVector.parser.parse(string, position);
                l.add(v);
            } catch (ParseException e) {
                break;
            }
        }
        ParserUtils.skipWhitespaces(string, position);
        if(position.intValue()< string.length() && string.charAt(position.intValue())=='}') {
            string.charAt(position.intValue());
			position.increment();
        } else {
            position.setValue(pos0);
            throw new ParseException();
        }
        JSCLVector v[]=(JSCLVector[])ArrayUtils.toArray(l,new JSCLVector[l.size()]);
        return Matrix.frame(v).transpose();
    }
}

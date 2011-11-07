package jscl.text;

import jscl.math.JsclVector;
import jscl.math.Variable;
import jscl.math.VectorVariable;
import org.jetbrains.annotations.NotNull;

public class VectorVariableParser implements Parser<Variable> {
    public static final Parser<Variable> parser=new VectorVariableParser();

    private VectorVariableParser() {}

    public Variable parse(@NotNull String string, @NotNull MutableInt position) throws ParseException {
        JsclVector v;
        try {
            v=(JsclVector)VectorParser.parser.parse(string, position);
        } catch (ParseException e) {
            throw e;
        }
        return new VectorVariable(v);
    }
}

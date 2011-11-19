package jscl.text;

import jscl.math.Generic;
import jscl.math.JsclVector;
import jscl.math.Variable;
import jscl.math.VectorVariable;
import org.jetbrains.annotations.NotNull;

public class VectorVariableParser implements Parser<Variable> {
    public static final Parser<Variable> parser=new VectorVariableParser();

    private VectorVariableParser() {}

    public Variable parse(@NotNull String expression, @NotNull MutableInt position, Generic previousSumElement) throws ParseException {
        JsclVector v;
        try {
            v=(JsclVector)VectorParser.parser.parse(expression, position, previousSumElement);
        } catch (ParseException e) {
            throw e;
        }
        return new VectorVariable(v);
    }
}

package jscl.text;

import jscl.math.Generic;
import jscl.math.Matrix;
import jscl.math.MatrixVariable;
import jscl.math.Variable;
import org.jetbrains.annotations.NotNull;

class MatrixVariableParser implements Parser<Variable> {
    public static final Parser<Variable> parser=new MatrixVariableParser();

    private MatrixVariableParser() {}

    public Variable parse(@NotNull String expression, @NotNull MutableInt position, int depth, Generic previousSumElement) throws ParseException {
        Matrix m;
        try {
            m=(Matrix)MatrixParser.parser.parse(expression, position, depth, previousSumElement);
        } catch (ParseException e) {
            throw e;
        }
        return new MatrixVariable(m);
    }
}

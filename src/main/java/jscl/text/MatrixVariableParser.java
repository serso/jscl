package jscl.text;

import jscl.math.Matrix;
import jscl.math.MatrixVariable;
import jscl.math.Variable;
import jscl.text.MutableInt;
import org.jetbrains.annotations.NotNull;

class MatrixVariableParser implements Parser<Variable> {
    public static final Parser<Variable> parser=new MatrixVariableParser();

    private MatrixVariableParser() {}

    public Variable parse(@NotNull String string, @NotNull MutableInt position) throws ParseException {
        Matrix m;
        try {
            m=(Matrix)MatrixParser.parser.parse(string, position);
        } catch (ParseException e) {
            throw e;
        }
        return new MatrixVariable(m);
    }
}

package jscl.math;

public class MatrixVariable extends GenericVariable {
        public MatrixVariable(Generic generic) {
                super(generic);
        }

        public Variable newInstance() {
                return new MatrixVariable(null);
        }
}

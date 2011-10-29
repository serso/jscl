package jscl.math;

public class VectorVariable extends GenericVariable {
        public VectorVariable(Generic generic) {
                super(generic);
        }

        public Variable newInstance() {
                return new VectorVariable(null);
        }
}

package jscl.math;

import jscl.math.function.Conjugate;
import jscl.math.function.Constant;
import jscl.math.function.Frac;
import jscl.math.function.trigonometric.Cos;
import jscl.math.function.trigonometric.Sin;
import jscl.mathml.MathML;
import jscl.util.ArrayComparator;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

public class Matrix extends Generic {
    protected final Generic elements[][];
    protected final int n,p;

    public Matrix(Generic elements[][]) {
        this.elements = elements;
        n= elements.length;
        p= elements.length>0? elements[0].length:0;
    }

    public Generic[][] elements() {
        return elements;
    }

    public Matrix add(Matrix matrix) {
        Matrix m=(Matrix)newinstance();
        for(int i=0;i<n;i++) {
            for(int j=0;j<p;j++) {
                m.elements[i][j]= elements[i][j].add(matrix.elements[i][j]);
            }
        }
        return m;
    }

    @NotNull
	public Generic add(@NotNull Generic that) {
        if(that instanceof Matrix) {
            return add((Matrix) that);
        } else {
            return add(valueOf(that));
        }
    }

    public Matrix subtract(Matrix matrix) {
        Matrix m=(Matrix)newinstance();
        for(int i=0;i<n;i++) {
            for(int j=0;j<p;j++) {
                m.elements[i][j]= elements[i][j].subtract(matrix.elements[i][j]);
            }
        }
        return m;
    }

    @NotNull
	public Generic subtract(@NotNull Generic that) {
        if(that instanceof Matrix) {
            return subtract((Matrix) that);
        } else {
            return subtract(valueOf(that));
        }
    }

    public static boolean isMatrixProduct(@NotNull Generic a, @NotNull Generic b) {
        return (a instanceof Matrix && b instanceof Matrix) ||
				(a instanceof Matrix && b instanceof JsclVector) ||
					(a instanceof JsclVector && b instanceof Matrix);
    }

    public Matrix multiply(Matrix matrix) {
        if(p!=matrix.n) throw new ArithmeticException();
        Matrix m=(Matrix)newinstance(new Generic[n][matrix.p]);
        for(int i=0;i<n;i++) {
            for(int j=0;j<matrix.p;j++) {
                m.elements[i][j]= JsclInteger.valueOf(0);
                for(int k=0;k<p;k++) {
                    m.elements[i][j]=m.elements[i][j].add(elements[i][k].multiply(matrix.elements[k][j]));
                }
            }
        }
        return m;
    }

    @NotNull
	public Generic multiply(@NotNull Generic that) {
        if(that instanceof Matrix) {
            return multiply((Matrix) that);
        } else if(that instanceof JsclVector) {
            JsclVector v=(JsclVector)((JsclVector) that).newInstance(new Generic[n]);
            JsclVector v2=(JsclVector) that;
            if(p!=v2.n) throw new ArithmeticException();
            for(int i=0;i<n;i++) {
                v.elements[i]= JsclInteger.valueOf(0);
                for(int k=0;k<p;k++) {
                    v.elements[i]=v.elements[i].add(elements[i][k].multiply(v2.elements[k]));
                }
            }
            return v;
        } else {
            Matrix m=(Matrix)newinstance();
            for(int i=0;i<n;i++) {
                for(int j=0;j<p;j++) {
                    m.elements[i][j]= elements[i][j].multiply(that);
                }
            }
            return m;
        }
    }

    @NotNull
	public Generic divide(@NotNull Generic that) throws ArithmeticException {
        if(that instanceof Matrix) {
            return multiply(((Matrix) that).inverse());
        } else if(that instanceof JsclVector) {
            throw new ArithmeticException();
        } else {
            Matrix m=(Matrix)newinstance();
            for(int i=0;i<n;i++) {
                for(int j=0;j<p;j++) {
                    try {
                        m.elements[i][j]= elements[i][j].divide(that);
                    } catch (NotDivisibleException e) {
                        m.elements[i][j]=new Frac(elements[i][j], that).evaluate();
                    }
                }
            }
            return m;
        }
    }

    public Generic gcd(Generic generic) {
        return null;
    }

    public Generic gcd() {
        return null;
    }

    public Generic negate() {
        Matrix m=(Matrix)newinstance();
        for(int i=0;i<n;i++) {
            for(int j=0;j<p;j++) {
                m.elements[i][j]= elements[i][j].negate();
            }
        }
        return m;
    }

    public int signum() {
        for(int i=0;i<n;i++) {
            for(int j=0;j<p;j++) {
                int c= elements[i][j].signum();
                if(c<0) return -1;
                else if(c>0) return 1;
            }
        }
        return 0;
    }

    public int degree() {
        return 0;
    }

    public Generic antiDerivative(Variable variable) throws NotIntegrableException {
        Matrix m=(Matrix)newinstance();
        for(int i=0;i<n;i++) {
            for(int j=0;j<p;j++) {
                m.elements[i][j]= elements[i][j].antiDerivative(variable);
            }
        }
        return m;
    }

    public Generic derivative(Variable variable) {
        Matrix m=(Matrix)newinstance();
        for(int i=0;i<n;i++) {
            for(int j=0;j<p;j++) {
                m.elements[i][j]= elements[i][j].derivative(variable);
            }
        }
        return m;
    }

    public Generic substitute(Variable variable, Generic generic) {
        Matrix m=(Matrix)newinstance();
        for(int i=0;i<n;i++) {
            for(int j=0;j<p;j++) {
                m.elements[i][j]= elements[i][j].substitute(variable,generic);
            }
        }
        return m;
    }

    public Generic expand() {
        Matrix m=(Matrix)newinstance();
        for(int i=0;i<n;i++) {
            for(int j=0;j<p;j++) {
                m.elements[i][j]= elements[i][j].expand();
            }
        }
        return m;
    }

    public Generic factorize() {
        Matrix m=(Matrix)newinstance();
        for(int i=0;i<n;i++) {
            for(int j=0;j<p;j++) {
                m.elements[i][j]= elements[i][j].factorize();
            }
        }
        return m;
    }

    public Generic elementary() {
        Matrix m=(Matrix)newinstance();
        for(int i=0;i<n;i++) {
            for(int j=0;j<p;j++) {
                m.elements[i][j]= elements[i][j].elementary();
            }
        }
        return m;
    }

    public Generic simplify() {
        Matrix m=(Matrix)newinstance();
        for(int i=0;i<n;i++) {
            for(int j=0;j<p;j++) {
                m.elements[i][j]= elements[i][j].simplify();
            }
        }
        return m;
    }

    public Generic numeric() {
        return new NumericWrapper(this);
    }

    public Generic valueOf(Generic generic) {
        if(generic instanceof Matrix || generic instanceof JsclVector) {
            throw new ArithmeticException();
        } else {
            Matrix m=(Matrix)identity(n,p).multiply(generic);
                        return newinstance(m.elements);
        }
    }

    public Generic[] sumValue() {
        return new Generic[] {this};
    }

    public Generic[] productValue() throws NotProductException {
        return new Generic[] {this};
    }

    public Power powerValue() throws NotPowerException {
        return new Power(this,1);
    }

    public Expression expressionValue() throws NotExpressionException {
        throw new NotExpressionException();
    }

    public JsclInteger integerValue() throws NotIntegerException {
        throw new NotIntegerException();
    }

	@Override
	public boolean isInteger() {
		return false;
	}

	public Variable variableValue() throws NotVariableException {
        throw new NotVariableException();
    }

    public Variable[] variables() {
        return null;
    }

    public boolean isPolynomial(Variable variable) {
        return false;
    }

    public boolean isConstant(Variable variable) {
        return false;
    }

    public Generic[] vectors() {
        JsclVector v[]=new JsclVector[n];
        for(int i=0;i<n;i++) {
            v[i]=new JsclVector(new Generic[p]);
            for(int j=0;j<p;j++) {
                v[i].elements[j]= elements[i][j];
            }
        }
        return v;
    }

    public Generic tensorProduct(Matrix matrix) {
        Matrix m=(Matrix)newinstance(new Generic[n*matrix.n][p*matrix.p]);
        for(int i=0;i<n;i++) {
            for(int j=0;j<p;j++) {
                for(int k=0;k<matrix.n;k++) {
                    for(int l=0;l<matrix.p;l++) {
                        m.elements[i*matrix.n+k][j*matrix.p+l]= elements[i][j].multiply(matrix.elements[k][l]);
                    }
                }
            }
        }
        return m;
    }

    public Matrix transpose() {
        Matrix m=(Matrix)newinstance(new Generic[p][n]);
        for(int i=0;i<n;i++) {
            for(int j=0;j<p;j++) {
                m.elements[j][i]= elements[i][j];
            }
        }
        return m;
    }

    public Generic trace() {
        Generic s= JsclInteger.valueOf(0);
        for(int i=0;i<n;i++) {
            s=s.add(elements[i][i]);
        }
        return s;
    }

    public Generic inverse() {
        Matrix m=(Matrix)newinstance();
        for(int i=0;i<n;i++) {
            for(int j=0;j<n;j++) {
                m.elements[i][j]=inverseElement(i,j);
            }
        }
        return m.transpose().divide(determinant());
    }

    Generic inverseElement(int k, int l) {
        Matrix m=(Matrix)newinstance();
        for(int i=0;i<n;i++) {
            for(int j=0;j<n;j++) {
                m.elements[i][j]=i==k? JsclInteger.valueOf(j == l ? 1 : 0): elements[i][j];
            }
        }
        return m.determinant();
    }

    public Generic determinant() {
        if(n>1) {
            Generic a= JsclInteger.valueOf(0);
            for(int i=0;i<n;i++) {
                if(elements[i][0].signum()==0);
                else {
                    Matrix m=(Matrix)newinstance(new Generic[n-1][n-1]);
                    for(int j=0;j<n-1;j++) {
                        for(int k=0;k<n-1;k++) m.elements[j][k]= elements[j<i?j:j+1][k+1];
                    }
                    if(i%2==0) a=a.add(elements[i][0].multiply(m.determinant()));
                    else a=a.subtract(elements[i][0].multiply(m.determinant()));
                }
            }
            return a;
        } else if(n>0) return elements[0][0];
        else return JsclInteger.valueOf(0);
    }

    public Generic conjugate() {
        Matrix m=(Matrix)newinstance();
        for(int i=0;i<n;i++) {
            for(int j=0;j<p;j++) {
                m.elements[i][j]=new Conjugate(elements[i][j]).evaluate();
            }
        }
        return m;
    }

    public int compareTo(Matrix matrix) {
        return ArrayComparator.comparator.compare(vectors(),matrix.vectors());
    }

    public int compareTo(Generic generic) {
        if(generic instanceof Matrix) {
            return compareTo((Matrix)generic);
        } else {
            return compareTo(valueOf(generic));
        }
    }

    public static Matrix identity(int dimension) {
            return identity(dimension,dimension);
    }

    public static Matrix identity(int n, int p) {
        Matrix m=new Matrix(new Generic[n][p]);
        for(int i=0;i<n;i++) {
            for(int j=0;j<p;j++) {
                if(i==j) {
                    m.elements[i][j]= JsclInteger.valueOf(1);
                } else {
                    m.elements[i][j]= JsclInteger.valueOf(0);
                }
            }
        }
        return m;
    }

    public static Matrix frame(JsclVector vector[]) {
        Matrix m=new Matrix(new Generic[vector.length>0?vector[0].n:0][vector.length]);
        for(int i=0;i<m.n;i++) {
            for(int j=0;j<m.p;j++) {
                m.elements[i][j]=vector[j].elements[i];
            }
        }
        return m;
    }

    public static Matrix rotation(int dimension, int plane, Generic angle) {
        return rotation(dimension,plane,2,angle);
    }

    public static Matrix rotation(int dimension, int axis1, int axis2, Generic angle) {
        Matrix m=new Matrix(new Generic[dimension][dimension]);
        for(int i=0;i<m.n;i++) {
            for(int j=0;j<m.p;j++) {
                if(i==axis1 && j==axis1) {
                    m.elements[i][j]=new Cos(angle).evaluate();
                } else if(i==axis1 && j==axis2) {
                    m.elements[i][j]=new Sin(angle).evaluate().negate();
                } else if(i==axis2 && j==axis1) {
                    m.elements[i][j]=new Sin(angle).evaluate();
                } else if(i==axis2 && j==axis2) {
                    m.elements[i][j]=new Cos(angle).evaluate();
                } else if(i==j) {
                    m.elements[i][j]= JsclInteger.valueOf(1);
                } else {
                    m.elements[i][j]= JsclInteger.valueOf(0);
                }
            }
        }
        return m;
    }

    public String toString() {
        StringBuffer buffer=new StringBuffer();
        buffer.append("{");
        for(int i=0;i<n;i++) {
            buffer.append("{");
            for(int j=0;j<p;j++) {
                buffer.append(elements[i][j]).append(j<p-1?", ":"");
            }
            buffer.append("}").append(i<n-1?",\n":"");
        }
        buffer.append("}");
        return buffer.toString();
    }

    public String toJava() {
        StringBuffer buffer=new StringBuffer();
        buffer.append("new Matrix(new Numeric[][] {");
        for(int i=0;i<n;i++) {
            buffer.append("{");
            for(int j=0;j<p;j++) {
                buffer.append(elements[i][j].toJava()).append(j<p-1?", ":"");
            }
            buffer.append("}").append(i<n-1?", ":"");
        }
        buffer.append("})");
        return buffer.toString();
    }

    public void toMathML(MathML element, Object data) {
        int exponent=data instanceof Integer?((Integer)data).intValue():1;
        if(exponent==1) bodyToMathML(element);
        else {
            MathML e1=element.element("msup");
            bodyToMathML(e1);
            MathML e2=element.element("mn");
            e2.appendChild(element.text(String.valueOf(exponent)));
            e1.appendChild(e2);
            element.appendChild(e1);
        }
    }

	@NotNull
	@Override
	public Set<? extends Constant> getConstants() {
		final Set<Constant> result = new HashSet<Constant>();

		for (Generic[] element : elements) {
			for (Generic generic : element) {
				result.addAll(generic.getConstants());
			}
		}

		return result;
	}

	protected void bodyToMathML(MathML e0) {
        MathML e1=e0.element("mfenced");
        MathML e2=e0.element("mtable");
        for(int i=0;i<n;i++) {
            MathML e3=e0.element("mtr");
            for(int j=0;j<p;j++) {
                MathML e4=e0.element("mtd");
                elements[i][j].toMathML(e4,null);
                e3.appendChild(e4);
            }
            e2.appendChild(e3);
        }
        e1.appendChild(e2);
        e0.appendChild(e1);
    }

    protected Generic newinstance() {
        return newinstance(new Generic[n][p]);
    }

    protected Generic newinstance(Generic element[][]) {
        return new Matrix(element);
    }
}
